package com.wzc.im.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.wzc.im.common.utils.Imt;
import com.wzc.im.entity.FunComment;
import com.wzc.im.entity.FunDynamic;
import com.wzc.im.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @author WANGZIC
 */
@Api(tags = "朋友圈接口",description = "朋友圈操作接口")
@RestController
public class ImMomentsController {

	@Autowired
	private FunDynamicService dynamicService;

	@Autowired
	private FunCommentService commentService;

	/**
	 * 发表动态
	 * @param dynamic 动态信息
	 * @return
	 */
    @RequestMapping(value="/publishDynamic")
    public String publishDynamic(FunDynamic dynamic){
    	dynamic.setCreatetime(new Date());
    	dynamic.setId(IdUtil.simpleUUID());
    	return String.valueOf(dynamicService.insert(dynamic)>0);
    }

	/**
	 * 删除动态
	 * @param id 动态id
	 * @return
	 */
	@RequestMapping(value="/deleteDynamic")
    public String deleteDynamic(String id){
    	return String.valueOf(dynamicService.deleteByPrimaryKey(id)>0);
    }

	/**
	 * 发表评论
	 * @param comment 评论信息
	 * @return
	 */
    @RequestMapping(value="/publishComment")
    public String publishComment(FunComment comment){
    	comment.setCreatetime(new Date());
    	comment.setId(IdUtil.simpleUUID());
    	return String.valueOf(commentService.insert(comment)>0);
    }

	/**
	 * 朋友圈所有动态
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value="/dynamicList")
    public String dynamicList(Integer id){
    	return JSON.toJSONStringWithDateFormat(dynamicService.selectFriendDynamics(id),"yyyy-MM-dd HH:mm:ss");
    }

	/**
	 * 动态下的所有回复
	 * @param did 动态id
	 * @return
	 */
	@RequestMapping(value="/commentList")
    public String commentList(String did){
		FunComment comment = new FunComment();
		comment.setDynamicid(did);
    	return JSON.toJSONStringWithDateFormat(commentService.select(comment),"yyyy-MM-dd HH:mm:ss");
    }

	/**
	 * 点赞
	 * @param id 动态id
	 * @param userid 用户id
	 * @param username 用户名称
	 * @return
	 */
    @RequestMapping(value="/like")
    public String like(String id, String userid, String username){
    	FunDynamic dynamic = dynamicService.selectByPrimaryKey(id);
    	dynamic.setLikeUserids(Imt.appendStr(dynamic.getLikeUserids(), userid));
    	dynamic.setLikeNames(Imt.appendStr(dynamic.getLikeNames(), username));
    	Map<String,Object> resMap = new HashMap<>(2);
    	resMap.put("success", dynamicService.updateByPrimaryKey(dynamic));
    	resMap.put("names", dynamic.getLikeNames());
    	return JSON.toJSONString(resMap);
    }

	/**
	 * 取消赞
	 * @param id 动态id
	 * @param userid 用户id
	 * @param username 用户名称
	 * @return
	 */
    @RequestMapping(value="/nolike")
    public String nolike(String id, String userid, String username){
    	FunDynamic dynamic = dynamicService.selectByPrimaryKey(id);
    	dynamic.setLikeUserids(Imt.delStr(dynamic.getLikeUserids(),userid));
    	dynamic.setLikeNames(Imt.delStr(dynamic.getLikeNames(), username));
    	Map<String,Object> resMap = new HashMap<>();
    	resMap.put("success", dynamicService.updateByPrimaryKey(dynamic));
    	resMap.put("names", dynamic.getLikeNames());
    	return JSON.toJSONString(resMap);
    }

}
