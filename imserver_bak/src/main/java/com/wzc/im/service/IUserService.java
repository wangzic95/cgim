package com.wzc.im.service;

import com.wzc.im.bean.ImUser;

import java.util.List;

public interface IUserService {
	
	public ImUser login(int uid, String password);
    
    public ImUser selectByTell(String tell);
	
	public ImUser selectById(int id);
	
	public List<ImUser> selectFriends(int id);
    
	public List<ImUser> selectAll();
    
    public int insert(ImUser user);

    public boolean update(ImUser user);
    
    public boolean delete(int id);
    
}
