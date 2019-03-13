package com.wzc.im.service;

import com.wzc.im.bean.FunDynamic;

import java.util.List;

public interface IDynamicService {

	public boolean insert(FunDynamic dynamic);
    
    public boolean delete(String id);
    
    public boolean update(FunDynamic dynamic);
    
    public FunDynamic selectById(String id);
    
    public List<FunDynamic> selectByUserId(int id);
    
    public List<FunDynamic> selectFriendDynamics(int id);
}
