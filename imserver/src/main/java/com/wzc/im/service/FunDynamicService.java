package com.wzc.im.service;

import com.wzc.im.common.base.IBaseService;
import com.wzc.im.entity.FunDynamic;

import java.util.List;

/**
 * @author WANGZIC
 */
public interface FunDynamicService extends IBaseService<FunDynamic,String> {

    List<FunDynamic> selectFriendDynamics(int id);
}
