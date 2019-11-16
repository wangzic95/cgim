package com.wzc.im.controller;

import com.alibaba.fastjson.JSON;
import com.wzc.im.common.utils.Imt;
import com.wzc.im.entity.ImUser;
import com.wzc.im.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author WANGZIC
 */
@Api(tags = "好友接口",description = "好友操作接口")
@RestController
public class ImFriendsController {

	@Autowired
	private ImUserService userService;

	/**
	 * 获取好友列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getMyFriends")
	public String getMyFriends(Integer id){
		try {
			return JSON.toJSONStringWithDateFormat(userService.selectFriends(id),"yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 添加好友
	 * @param uid
	 * @param tid
	 * @return
	 */
    @RequestMapping(value="/addfriend")
	@Transactional(rollbackFor = Exception.class)
    public String addfriend(Integer uid, Integer tid){
		ImUser user = userService.selectByPrimaryKey(uid);
		user.setFriends(Imt.appendStr(user.getFriends(),tid));
		ImUser target = userService.selectByPrimaryKey(tid);
		target.setFriends(Imt.appendStr(target.getFriends(),uid));
    	return String.valueOf(userService.updateByPrimaryKey(user)>0 && userService.updateByPrimaryKey(target)>0);
    }

	/**
	 * 删除好友
	 * @param uid
	 * @param tid
	 * @return
	 */
    @RequestMapping(value="/deletefriend")
	@Transactional(rollbackFor = Exception.class)
	public String deletefriend(Integer uid, Integer tid){
		ImUser user = userService.selectByPrimaryKey(uid);
		user.setFriends(Imt.delStr(user.getFriends(),tid));
		ImUser target = userService.selectByPrimaryKey(tid);
		target.setFriends(Imt.delStr(target.getFriends(),uid));
    	return String.valueOf(userService.updateByPrimaryKey(user)>0 && userService.updateByPrimaryKey(target)>0);
    }
}
