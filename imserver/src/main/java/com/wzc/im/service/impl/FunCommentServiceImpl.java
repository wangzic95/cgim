package com.wzc.im.service.impl;

import com.wzc.im.common.base.IBaseServiceImpl;
import com.wzc.im.entity.FunComment;
import com.wzc.im.mapper.FunCommentMapper;
import com.wzc.im.service.FunCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WANGZIC
 */
@Service
public class FunCommentServiceImpl extends IBaseServiceImpl<FunComment,String> implements FunCommentService {

	@Autowired
	private FunCommentMapper mapper;
	
}
