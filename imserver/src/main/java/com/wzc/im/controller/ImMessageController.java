package com.wzc.im.controller;

import com.alibaba.fastjson.JSON;
import com.wzc.im.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WANGZIC
 */
@Api(tags = "消息接口",description = "消息操作接口")
@RestController
public class ImMessageController {

	@Autowired
	private ImMessageService messageService;

	/**
	 * 获取用户离线消息
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value="/getOfflineMessages")
	public String getOfflineMessages(Integer id){
		return JSON.toJSONStringWithDateFormat(messageService.selectOfflineLogs(id),"yyyy-MM-dd HH:mm:ss");
	}
}
