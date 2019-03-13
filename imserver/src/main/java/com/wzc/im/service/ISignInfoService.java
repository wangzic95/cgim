package com.wzc.im.service;

import com.wzc.im.bean.FunSigninfo;

import java.util.List;

public interface ISignInfoService {

	public boolean insert(FunSigninfo signinfo);
	
	public boolean delete(String id);
	
	public FunSigninfo selectById(String sid);
	
	public List<FunSigninfo> selectByCreateId(String uid);
}
