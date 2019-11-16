package com.wzc.im.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wzc.im.common.base.IBaseServiceImpl;
import com.wzc.im.entity.ImUser;
import com.wzc.im.mapper.ImUserMapper;
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
public class ImUserServiceImpl extends IBaseServiceImpl<ImUser,Integer> implements ImUserService {

	@Autowired
    private ImUserMapper userMapper;

	@Override
	public List<ImUser> selectFriends(int id) {
		ImUser user = userMapper.selectByPrimaryKey(id);
		List<ImUser> users = new ArrayList<ImUser>();
		String friends = user.getFriends();
		if(StrUtil.isNotBlank(friends)){
			Example example = new Example(ImUser.class);
			example.createCriteria().andIn("id", Arrays.asList(friends.split(",")));
			users.addAll(userMapper.selectByExample(example));
		}
		return users;
	}
}
