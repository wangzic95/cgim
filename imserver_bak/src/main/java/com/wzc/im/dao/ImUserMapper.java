package com.wzc.im.dao;

import com.wzc.im.bean.ImUser;
import com.wzc.im.bean.ImUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImUserMapper {
    int countByExample(ImUserExample example);

    int deleteByExample(ImUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ImUser record);

    int insertSelective(ImUser record);
    
    int insertAndGetId(ImUser record);

    List<ImUser> selectByExampleWithBLOBs(ImUserExample example);

    List<ImUser> selectByExample(ImUserExample example);

    ImUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ImUser record, @Param("example") ImUserExample example);

    int updateByExampleWithBLOBs(@Param("record") ImUser record, @Param("example") ImUserExample example);

    int updateByExample(@Param("record") ImUser record, @Param("example") ImUserExample example);

    int updateByPrimaryKeySelective(ImUser record);

    int updateByPrimaryKeyWithBLOBs(ImUser record);

    int updateByPrimaryKey(ImUser record);
}