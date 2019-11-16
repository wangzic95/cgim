package com.wzc.im.common.base;

import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author WANGZIC
 */
public interface IBaseService<T, ID extends Serializable> {

    int deleteByPrimaryKey(ID id);


    int delete(T entity);


    int insert(T entity);


    int insertSelective(T entity);

    
    boolean existsWithPrimaryKey(ID id);

    
    List<T> selectAll();

    
    T selectByPrimaryKey(ID id);

    
    int selectCount(T entity);

    
    List<T> select(T entity);

    
    T selectOne(T entity);

    
    int updateByPrimaryKey(T entity);

    
    int updateByPrimaryKeySelective(T entity);

    
    int deleteByExample(Example example);

    
    List<T> selectByExample(Example example);

    
    int selectCountByExample(Example example);

    
    T selectOneByExample(Example example);

    
    int updateByExample(T entity, Example example);

    
    int updateByExampleSelective(T entity, Example example);

    
    List<T> selectByExampleAndRowBounds(Example example, RowBounds rowBounds);

    
    List<T> selectByRowBounds(T entity, RowBounds rowBounds);

    
    int insertList(List<? extends T> list);

    
    int insertUseGeneratedKeys(T entity);

}