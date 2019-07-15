package com.wzc.im.service.impl;

import com.wzc.im.bean.ImUser;
import com.wzc.im.bean.ImUserExample;
import com.wzc.im.dao.ImUserMapper;
import com.wzc.im.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
    private ImUserMapper userMapper;
	
	public ImUser selectByTell(String tell) {
		ImUserExample example =new ImUserExample();
		example.createCriteria().andCellphoneEqualTo(tell);
		return userMapper.selectByExample(example).get(0);
		
	}
    public int insert(ImUser user) {
    	if(userMapper.insertAndGetId(user)>0){
    		return user.getId();
    	}
        return 0;
    }
    public ImUser selectById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}
    public List<ImUser> selectAll() {
		return userMapper.selectByExample(null);
	}
    public boolean update(ImUser user) {
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }
	public ImUser login(int uid, String password) {
		ImUserExample example =new ImUserExample();
		example.createCriteria().andIdEqualTo(uid);
		example.createCriteria().andPasswordEqualTo(password);
		List<ImUser> user =userMapper.selectByExample(example);
		if(user.size()>0){
			return user.get(0);
		}
		return null;
	}
	public boolean delete(int id) {
		return userMapper.deleteByPrimaryKey(id)>0;
	}
	public List<ImUser> selectFriends(int id) {
		ImUser user = userMapper.selectByPrimaryKey(id);
		List<ImUser> users = new ArrayList<ImUser>();
		String friends = user.getFriends();
		if(friends!=null&&friends!=""){
			if(friends.indexOf(",")>-1){
				String[] uids =friends.split(",");
				for(String uid:uids){
					users.add(userMapper.selectByPrimaryKey(Integer.parseInt(uid)));
				}
			}else{
				users.add(userMapper.selectByPrimaryKey(Integer.parseInt(friends)));
			}
		}
		return users;
	}
}
