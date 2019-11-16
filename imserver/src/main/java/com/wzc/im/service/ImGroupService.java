package com.wzc.im.service;

import com.wzc.im.common.base.IBaseService;
import com.wzc.im.entity.ImGroup;
import com.wzc.im.entity.ImUser;

import java.util.List;

/**
 * @author WANGZIC
 */
public interface ImGroupService extends IBaseService<ImGroup,Integer> {

    List<ImGroup> selectContainsMeGroups(int id);

    List<ImUser> getGroupMembers(int gid);
}
