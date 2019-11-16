package com.wzc.im.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wzc.im.common.base.IBaseServiceImpl;
import com.wzc.im.entity.ImMessage;
import com.wzc.im.entity.ImUser;
import com.wzc.im.mapper.ImMessageMapper;
import com.wzc.im.service.ImMessageService;
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
public class ImMessageServiceImpl extends IBaseServiceImpl<ImMessage,String> implements ImMessageService {

	@Autowired
	private ImMessageMapper messageMapper;
	
	@Autowired
	private ImUserService userService;

	@Override
	public List<ImMessage> selectOfflineLogs(int uid) {
		ImUser user =userService.selectByPrimaryKey(uid);
		String logstr = user.getOfflinelogs();
		List<ImMessage> messages = new ArrayList<ImMessage>();
		if(StrUtil.isNotBlank(logstr)){
			Example example = new Example(ImMessage.class);
			example.createCriteria().andIn("mid", Arrays.asList(logstr.split(",")));
			messages.addAll(messageMapper.selectByExample(example));
		}
		user.setOfflinelogs("");
		userService.updateByPrimaryKeySelective(user);
		return messages;
	}
}
