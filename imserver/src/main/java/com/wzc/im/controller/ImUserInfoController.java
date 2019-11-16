package com.wzc.im.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.wzc.im.common.utils.ReturnT;
import com.wzc.im.entity.ImGroup;
import com.wzc.im.entity.ImUser;
import com.wzc.im.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author WANGZIC
 */
@Api(tags = "用户接口",description = "用户信息操作接口")
@Slf4j
@RestController
public class ImUserInfoController {

	@Autowired
	private ImUserService userService;

	@Autowired
	private ImGroupService groupService;


	@PostMapping(value="/login")
	public ReturnT login(ImUser user){
		List<ImUser> list = userService.select(user);
		if(list.isEmpty()){
			return ReturnT.FAIL.setMsg("账号或密码错误！");
		}else {
			return new ReturnT<>(list.get(0));
		}
	}

	@PostMapping(value="/regist")
	public ReturnT regist(ImUser user){
		ImUser user1 = new ImUser();
		user1.setCellphone(user.getCellphone());
		if(userService.select(user1).isEmpty()){
			if(userService.insertUseGeneratedKeys(user)>0){
				return new ReturnT<>(user);
			}
		}else {
			return ReturnT.FAIL.setMsg("该手机号已被注册，请直接登录或更换手机号！");
		}
		return ReturnT.FAIL.setMsg("注册失败！");
	}

	@RequestMapping(value="/getUser")
	public String getUser(Integer id){
		return JSON.toJSONStringWithDateFormat(userService.selectByPrimaryKey(id),"yyyy-MM-dd HH:mm:ss");
	}

	@RequestMapping(value="/getUserByU")
	public String getUserByU(ImUser user){
		return JSON.toJSONStringWithDateFormat(userService.select(user),"yyyy-MM-dd HH:mm:ss");
	}

	@GetMapping(value="/getuimg")
	public void getuimg(HttpServletResponse response, HttpServletRequest request){
		Integer uid = Integer.valueOf(request.getParameter("uid"));
		String imgpath ="upload/default.jpg";
		String path="";
		ImUser user = userService.selectByPrimaryKey(uid);
		if(user==null){
			ImGroup group = groupService.selectByPrimaryKey(uid);
			if(group!=null){
				imgpath = group.getGimg();
			}
		}else{
			imgpath = user.getUimg();
		}
		if(imgpath!=null&&imgpath!=""){
			path= request.getSession().getServletContext().getRealPath("/")+imgpath;
		}else{
			path= request.getSession().getServletContext().getRealPath("/")+"upload/default.jpg";
		}
		InputStream inputStream=null;
		try {
			inputStream =new FileInputStream(path);
		} catch (FileNotFoundException e) {
			log.error("文件未找到！",e.getMessage());
		}
		try {
			if(inputStream == null){
				inputStream= this.getClass().getResourceAsStream("/static/img/default.jpg");
			}
			byte[] bytes = IOUtils.toByteArray(inputStream);
			response.getOutputStream().write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}

	@RequestMapping(value="/getAllUsers")
	public String getAllUsers(){
		return JSON.toJSONStringWithDateFormat(userService.selectAll(),"yyyy-MM-dd HH:mm:ss");
	}

	@RequestMapping(value="/updateUserInfo")
	public String updateUserInfo(ImUser user, String mbirthday){
		if(StringUtils.isNotEmpty(mbirthday)){
			user.setBirthday(DateUtil.parse(mbirthday));
		}
		return userService.updateByPrimaryKeySelective(user)+"";
	}

    @RequestMapping(value="/uploadfile")
	public String uploadfile(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		String filepath = request.getSession().getServletContext().getRealPath("/")+"upload/";
		if(!new File(filepath).exists()){ new File(filepath).mkdirs(); }
    	Map<String, Object> map = new HashMap<>();
    	map.put("result", "faile");
    	//将请求转换成
        MultipartHttpServletRequest mRequest=(MultipartHttpServletRequest)request;
		//获取上传的文件列表
        Iterator<String> fns=mRequest.getFileNames();
        while(fns.hasNext()){
            String s =fns.next();
            MultipartFile mFile = mRequest.getFile(s);
            if(!mFile.isEmpty()){
            	String filename = System.currentTimeMillis()+"_"+mFile.getOriginalFilename();
            	mFile.transferTo(new File(filepath+filename));
            	map.put("result", "OK");
            	map.put("path", "upload/"+filename);
            }
        }
        return JSON.toJSONString(map);
	}

}
