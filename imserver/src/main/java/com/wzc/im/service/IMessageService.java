package com.wzc.im.service;

import com.wzc.im.bean.ImMessage;

import java.util.List;

public interface IMessageService {
	
	public boolean insert(ImMessage message);
	
	public boolean delete(String id);
	
	public ImMessage selectById(String id);
	
	public List<ImMessage> selectOfflineLogs(int uid);

}
