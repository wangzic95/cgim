package com.wzc.im.controller;

import com.alibaba.fastjson.JSON;
import com.wzc.im.common.utils.Imt;
import com.wzc.im.entity.ImGroup;
import com.wzc.im.entity.ImUser;
import com.wzc.im.service.ImGroupService;
import com.wzc.im.service.ImUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @author WANGZIC
 */
@Api(tags = "群组接口",description = "群组操作接口")
@RestController
public class ImGroupController {

	@Autowired
	private ImUserService userService;

	@Autowired
	private ImGroupService groupService;


	/**
	 * 获取群组信息
	 * @param id 组id
	 * @return
	 */
	@RequestMapping(value="/getGroup")
	public String getGroup(Integer id){
		return JSON.toJSONStringWithDateFormat(groupService.selectByPrimaryKey(id),"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 我的群组
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value="/myGroups")
	public String myGroups(Integer id){
		return JSON.toJSONStringWithDateFormat(groupService.selectContainsMeGroups(id),"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 新增群组
	 * @param group 群组信息
	 * @return
	 */
    @RequestMapping(value="/addGroup")
    public String addGroup(ImGroup group){
    	group.setCreatetime(new Date());
    	int gid = groupService.insertUseGeneratedKeys(group);
    	if(gid!=0){
			for (String uid:group.getMembers().split(",")) {
				ImUser user = new ImUser();
				user.setId(Integer.valueOf(uid));
				user.setGroups(Imt.appendStr(user.getGroups(),gid));
				userService.updateByPrimaryKeySelective(user);
			}
    		return String.valueOf(gid);
    	}else{
    		return null;
    	}
    }

	/**
	 * 退出群组
	 * @param uid 用户id
	 * @param gid 目标组id
	 * @return
	 */
    @RequestMapping(value="/quitGroup")
	@Transactional(rollbackFor = Exception.class)
    public String quitGroup(Integer uid , Integer gid){
		ImGroup group = groupService.selectByPrimaryKey(gid);
    	group.setMembers(Imt.delStr(group.getMembers(),uid));
    	ImUser user = userService.selectByPrimaryKey(uid);
    	user.setGroups(Imt.delStr(user.getGroups(),gid));
    	return String.valueOf(groupService.updateByPrimaryKey(group)>0 && userService.updateByPrimaryKey(user)>0);
    }

	/**
	 * 删除群组
	 * @param uid 用户id
	 * @param gid 目标组id
	 * @return
	 */
    @RequestMapping(value="/deleteGroup")
	@Transactional(rollbackFor = Exception.class)
    public String deleteGroup(Integer uid , Integer gid){
    	ImUser user = userService.selectByPrimaryKey(uid);
		user.setGroups(Imt.delStr(user.getGroups(),gid));
    	return  String.valueOf(groupService.deleteByPrimaryKey(gid)>0 && userService.updateByPrimaryKey(user)>0);
    }

	/**
	 * 加入群组
	 * @param uid 用户id
	 * @param gid 目标组id
	 * @return
	 */
    @RequestMapping(value="/joinGroup")
	@Transactional(rollbackFor = Exception.class)
    public String joinGroup(String uid, Integer gid){
        ImGroup group = groupService.selectByPrimaryKey(gid);
        group.setMembers(Imt.appendStr(group.getMembers(),uid));
		ImUser user = new ImUser();
		user.setId(Integer.valueOf(uid));
		user.setGroups(Imt.appendStr(user.getGroups(),gid));
        return String.valueOf(groupService.updateByPrimaryKey(group)>0 && userService.updateByPrimaryKeySelective(user)>0);
    }

}
