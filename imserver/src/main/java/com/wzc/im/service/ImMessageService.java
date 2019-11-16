package com.wzc.im.service;

import com.wzc.im.common.base.IBaseService;
import com.wzc.im.entity.ImMessage;

import java.util.List;


/**
 * @author WANGZIC
 */
public interface ImMessageService extends IBaseService<ImMessage,String> {

    /**
     * 获取用户离线消息列表
     * @param uid 用户id
     * @return 离线消息列表
     */
    List<ImMessage> selectOfflineLogs(int uid);
}
