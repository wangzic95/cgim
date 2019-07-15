package com.wzc.im.service;

import com.wzc.im.bean.FunComment;

import java.util.List;

public interface ICommentService {

	public boolean insert(FunComment comment);
	
	public List<FunComment> selectByDynamicId(String did);
	
}
