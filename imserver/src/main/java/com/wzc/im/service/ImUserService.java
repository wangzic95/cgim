package com.wzc.im.service;

import com.wzc.im.common.base.IBaseService;
import com.wzc.im.entity.ImUser;

import java.util.List;

/**
 * @author WANGZIC
 */
public interface ImUserService extends IBaseService<ImUser,Integer> {


    List<ImUser> selectFriends(int id);

}
