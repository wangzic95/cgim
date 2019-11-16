let bs4pop = {};

(function(pop){
    //创建弹出框
    pop.dialog = function(opts){
        opts = $.extend( true, {
            id: '',//'#xxx'，对话框ID，
            title: '',
            content: '', //可以是 string、element，$object
            className: '', //自定义样式
            width: 500,
            height: '',
            target: 'body',//在什么dom内创建dialog
            closeBtn: true, //是否有关闭按钮
            hideRemove: true,//关闭时移除dom
            escape: true, //Esc 退出
            autoFocus: true,//初始化时自动获得焦点
            show: true,//是否在一开始时就显示对话框
            backdrop: true,//模态背景 true: 显示模态，false: 没有模态，'static': 显示模态，单击模态不关闭对话框
            btns: [], //footer按钮 [{label: 'Button',	className: 'btn-primary',onClick(cb){}}]
            drag: true,//是否启用拖拽
            onShowStart(){},
            onShowEnd(){},
            onHideStart(){},
            onHideEnd(){},
            onClose(){},
            onDragStart(){},
            onDragEnd(){},
            onDrag(){}
        }, opts);
        //得到 $el
        let $el = opts.id !== '' ? $('#'+opts.id) : undefined;

        if(!$el || !$el.length){
            $el = $(`
				<div class="modal fade" tabindex="-1" role="dialog" data-backdrop="${opts.backdrop}">
					<div class="modal-dialog ">
						<div class="modal-content">
							<div class="modal-body"></div>
						</div>
					</div>
				</div>
			`);
        }

        //得到 $body
        let $body = $el.find('.modal-body');

        //创建 header
        if(opts.closeBtn || opts.title){

            let $header = $('<div class="modal-header"></div>');

            if(opts.title){
                $header.append(`<h5 class="modal-title"> ${opts.title} </h5>`);
            }

            if(opts.closeBtn){
                $header.append(`
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				`);
            }
            $body.before($header);
        }

        //创建 footer
        if(opts.btns.length){
            let $footer = $('<div class="modal-footer"></div>');
            opts.btns.forEach(btn => {
                btn = $.extend(true, {
                    label: 'Button',
                    className: 'btn-primary',
                    onClick(cb){},
                }, btn);
                let $btn = $('<button type="button" class="btn '+btn.className+' pl-5 pr-5">'+btn.label+'</button>');
                $btn.on('click', evt => {
                    //提供手动关闭对话框的方法，以便于对话框延迟关闭
                    evt.hide = ()=>{
                        $el.modal('hide');
                    };
                    //如果返回不是false就自动隐藏dialog
                    if(btn.onClick(evt) !== false){
                        $el.modal('hide');
                    }
                });
                $footer.append($btn);
            });
            $body.after($footer);
        }

        //创建内容
        if(typeof opts.content === 'string'){
            $body.html(opts.content);
        }else if(typeof opts.content === 'object'){
            $body.empty();
            $(opts.content).contents().appendTo($body);//移动dom到 modal-body下
        }

        //设置属性
        opts.id && $el.attr('id', opts.id);
        opts.className && $el.addClass(opts.className);
        opts.width && $el.find('.modal-dialog').width(opts.width).css('max-width', opts.width);
        opts.height && $el.find('.modal-dialog').height(opts.height);

        //绑定事件
        opts.hideRemove && $el.on('hidden.bs.modal',  function(){
            $el.modal('dispose').remove();//移除dom
        });
        $el.on('show.bs.modal', opts.onShowStart);
        $el.on('shown.bs.modal', opts.onShowEnd);
        $el.on('hide.bs.modal', opts.onHideStart);
        $el.on('hidden.bs.modal', opts.onHideEnd);
        opts.closeBtn && $el.find('.close').on('click', function(){
            return opts.onClose();
        });

        //拖拽
        // if(opts.drag){
        //     $el.attr('data-drag', 'drag');
        //     // $el.find(".modal-dialog").css("position","absolute");
        //     // $el.find(".modal-dialog").css("left","50%");
        //     // $el.find(".modal-dialog").css("margin-left",Number(opts.width)/-2);
        //     drag({
        //         el: $el.find('.modal-content'),
        //         handle: $el.find('.modal-header'),
        //         onDragStart(){
        //             $el.attr('data-drag', 'draged');
        //             opts.onDragStart();
        //         },
        //         onDragEnd(){
        //             opts.onDragEnd();
        //         },
        //         onDraging(){
        //             opts.onDrag();
        //         }
        //     });
        // }

        $(opts.target).append($el);

        $el.modal({
            backdrop: opts.backdrop, //boolean or the string 'static'.Includes a modal-backdrop element. Alternatively, specify static for a backdrop which doesn't close the modal on click.
            keyboard: opts.escape, //Closes the modal when escape key is pressed
            focus: opts.autoFocus, //Puts the focus on the modal when initialized.
            show: opts.show //Shows the modal when initialized.
        });

        let result = {
            $el: $el,
            show(){
                $el.modal('show');
            },
            hide(){
                $el.modal('hide');
            },
            toggle(){
                $el.modal('toggle');
            },
            destory(){
                $el.modal('dispose');
            }
        };

        return result;

    };
    //移除全部弹出框
    pop.removeAll = function(){
        $('[role="dialog"],.modal-backdrop').remove();
    };
    //拖拽
    function drag(opts){

        opts = $.extend(true, {
            el: '',
            handle: '',
            onDragStart(){},
            onDraging(){},
            onDragEnd(){}

        }, opts);

        opts.el = $(opts.el);
        opts.handle = $(opts.handle);
        let $root = $(document);
        let isFirstDrag = true;

        $(opts.handle).on('touchstart mousedown', startEvt=>{

            startEvt.preventDefault();

            let pointEvt = startEvt;
            if(startEvt.type === 'touchstart'){
                pointEvt = startEvt.touches[0];
            }

            let startData = {
                pageX: pointEvt.pageX,
                pageY: pointEvt.pageY,
                targetPageX: opts.el.offset().left, //当前dom的位置信息
                targetPageY: opts.el.offset().top,
            };

            let move = moveEvt => {

                let pointEvt = moveEvt;
                if(moveEvt.type === 'touchmove'){
                    pointEvt = moveEvt.touches[0];
                }

                let moveData = {
                    pageX: pointEvt.pageX, //对于整个页面来说，包括了被卷去的body部分的长度
                    pageY: pointEvt.pageY,
                    moveX: pointEvt.pageX - startData.pageX,
                    moveY: pointEvt.pageY - startData.pageY,
                };

                if(isFirstDrag){
                    opts.onDragStart(startData);
                    isFirstDrag = false;
                }else{
                    opts.onDraging();
                }

                opts.el.css({
                    left: startData.targetPageX + moveData.moveX,
                    top: startData.targetPageY + moveData.moveY,
                });

            };

            let up = () =>{
                $root.off('touchmove mousemove', move);
                $root.off('touchend mouseup', up);
                opts.onDragEnd();
            };

            $root.on('touchmove mousemove', move).on('touchend mouseup', up);

        });

    }
})(bs4pop);

(function(pop){
    //对话框
    pop.alert = function(content, cb, opts){

        let dialogOpts = $.extend(true, {
            title: '对话框',
            content: content,
            hideRemove: true,
            width: 500,
            btns: [
                {
                    label: '确定',
                    onClick(){
                        if(cb){
                            return cb();
                        }
                    }
                }
            ]
        }, opts);

        return pop.dialog(dialogOpts);

    };
    //确认框
    pop.confirm = function(content, cb, opts){

        let dialogOpts = $.extend(true, {
            title: '选择框',
            content: content,
            hideRemove: true,
            btns: [
                {
                    label: '确定',
                    onClick(){
                        if(cb){
                            return cb(true);
                        }
                    }
                },
                {
                    label: '取消',
                    className: 'btn-light',
                    onClick(){
                        if(cb){
                            return cb(false);
                        }
                    }
                }
            ]
        }, opts);

        return this.dialog(dialogOpts);

    };
    //输入框
    pop.prompt = function(content, value, cb, opts,placeholder){
        let $content = $(`
			<div>
				<p>${content}</p>
				<input type="text" class="form-control" value="${value || ''}" placeholder="${placeholder || '' }"/>
			</div>
		`);

        let $input = $content.find('input');

        let dialogOpts = $.extend(true, {
            title: '输入框',
            content: $content,
            hideRemove: true,
            width: 500,
            btns: [
                {
                    label: '确定',
                    onClick(){
                        if(cb){
                            return cb(true, $input.val());
                        }
                    }
                },
                {
                    label: '取消',
                    className: 'btn-light',
                    onClick(){
                        if(cb){
                            return cb(false, $input.val());
                        }
                    }
                }
            ]
        }, opts);

        return pop.dialog(dialogOpts);

    };
    //消息框 暂时废弃，有空再改造
    // pop.notice = function(content, opts){
    //     opts = $.extend( true, {
    //         type: 'primary', //primary, secondary, success, danger, warning, info, light, dark
    //         position: 'topcenter', //topleft, topcenter, topright, bottomleft, bottomcenter, bottonright, center,
    //         appendType: 'append', //append, prepend
    //         closeBtn: false,
    //         autoClose: 2000,
    //         className: '',
    //
    //     }, opts);
    //
    //     // 得到容器 $container
    //     let $container = $('#alert-container-'+ opts.position);
    //     if(!$container.length){
    //         $container = $('<div id="alert-container-' + opts.position + '" class="alert-container shadow"></div>');
    //         $('body').append($container);
    //     }
    //
    //     // alert $el
    //     let $el = $(`
	// 		<div class="alert fade alert-${opts.type}" role="alert">${content}</div>
	// 	`);
    //
    //     // 关闭按钮
    //     if(opts.closeBtn){
    //         $el.append(`
    //             <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    //                 <span aria-hidden="true">&times;</span>
    //             </button>
    //         `).addClass('alert-dismissible');
    //     }
    //
    //     //定时关闭
    //     if(opts.autoClose){
    //         let t = setTimeout(() => {
    //             $el.alert('close');
    //         }, opts.autoClose);
    //
    //     }
    //     opts.appendType === 'append' ? $container.append($el) : $container.prepend($el);
    //     setTimeout(() => {
    //         $el.addClass('show');
    //     }, 50);
    //     return;
    // };
    //消息框
    pop.toast = function(content, opts){
        opts = $.extend( true, {
            content:"操作成功",
            type:"success",
            delay:1500,
            title:"成功",
            autoClose:true,
            animation:true,
            closeBtn: true,
            date:"刚刚"
        }, opts);
        opts.content = content;
        const tits = {"info":"信息","warning":"警告","danger":"危险","success":"成功","primary":"提示"};
        let $toast = $(`
            <div class="toast" role="alert" aria-live="assertive" aria-atomic="true" 
                  data-animation="${opts.animation}" data-autohide="${opts.autoClose}" data-delay="${opts.delay}">
                <div class="toast-header">
                    <span class="p-2 mr-2 bg-${opts.type} rounded "></span>
                    <strong class="mr-auto">${opts.title || tits[opts.type]}</strong>
                    <small>${opts.date}</small>
                </div>
                <div class="toast-body"></div>
            </div>
        `);
        //创建内容
        const $toastBody = $toast.find(".toast-body");
        if(typeof opts.content === 'string'){
            $toastBody.html(opts.content);
        }else if(typeof opts.content === 'object'){
            $toastBody.empty();
            $(opts.content).contents().appendTo($toastBody);
        }
        // 关闭按钮
        const $toastHead = $toast.find(".toast-header");
        if(opts.closeBtn){
            $toastHead.append(`
                <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            `);
            $toast.on("click",function () {$(this).remove();});
        }
        //生成toast
        let $toastBox = $(".bs-toast-box");
        if($toastBox.length == 0){
            $toastBox = $(`
                <div class="bs-toast-box" style="position: fixed;top: 65px;right: 0;z-index: 950918;width:365px;"></div>
            `);
        }
        $("body").append($toastBox.append($toast));
        $toast.toast('show');
        opts.autoClose && setTimeout(function () { $toast.length>0 && $toast.remove(); },opts.delay);
    }
})(bs4pop);

if( typeof module === "object" && typeof module.exports === "object" ){
    module.exports = bs4pop
}