package com.wzc.im.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wzc.im.common.base.IBaseServiceImpl;
import com.wzc.im.entity.ImGroup;
import com.wzc.im.entity.ImMessage;
import com.wzc.im.entity.ImUser;
import com.wzc.im.mapper.ImGroupMapper;
import com.wzc.im.service.ImGroupService;
import com.wzc.im.service.ImUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WANGZIC
 */
@Service
public class ImGroupServiceImpl extends IBaseServiceImpl<ImGroup,Integer> implements ImGroupService {

	@Autowired
	private ImGroupMapper groupMapper;

	@Autowired
	private ImUserService userService;
	
	@Override
	public List<ImUser> getGroupMembers(int gid) {
		String memberstr =groupMapper.selectByPrimaryKey(gid).getMembers();
		if(StrUtil.isBlank(memberstr)){
			return new ArrayList<>();
		}
		Example example = new Example(ImUser.class);
		example.createCriteria().andIn("id", Arrays.asList(memberstr.split(",")));
		return userService.selectByExample(example);
	}

	@Override
	public List<ImGroup> selectContainsMeGroups(int id) {
		List<ImGroup> groups = new ArrayList<ImGroup>();
		String groupstr =userService.selectByPrimaryKey(id).getGroups();
		if(StrUtil.isNotBlank(groupstr)){
			Example example = new Example(ImGroup.class);
			example.createCriteria().andIn("gid", Arrays.asList(groupstr.split(",")));
			groups.addAll(groupMapper.selectByExample(example));
		}
		return groups;
	}
}
