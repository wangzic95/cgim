package com.wzc.im.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wzc.im.common.base.IBaseServiceImpl;
import com.wzc.im.entity.FunDynamic;
import com.wzc.im.entity.ImUser;
import com.wzc.im.mapper.FunDynamicMapper;
import com.wzc.im.service.FunDynamicService;
import com.wzc.im.service.ImUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author WANGZIC
 */
@Service
public class FunDynamicServiceImpl extends IBaseServiceImpl<FunDynamic,String> implements FunDynamicService {

	@Autowired
	private FunDynamicMapper dynamicMapper;
	
	@Autowired
	private ImUserService userService;

	@Override
	public List<FunDynamic> selectFriendDynamics(int id) {
		Example example =new Example(FunDynamic.class);
		Example.Criteria criteria = example.createCriteria();
		ImUser user = userService.selectByPrimaryKey(id);
		String friends =user.getFriends();
		if(StrUtil.isNotBlank(friends)){
			friends = friends+","+id;
			criteria.andIn("createuserid", Arrays.asList(friends.split(",")));
		}
		example.setOrderByClause(" createtime desc");
		return dynamicMapper.selectByExample(example);
	}
}
