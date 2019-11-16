package com.wzc.im.common.base;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author WANGZIC
 */
public abstract class IBaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {

    @Autowired
    private IBaseMapper<T> baseMapper;

    @Override
    public int deleteByPrimaryKey(ID id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(T entity) {
        return baseMapper.delete(entity);
    }

    @Override
    public int insert(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int insertSelective(T entity) {
        return baseMapper.insertSelective(entity);
    }

    @Override
    public boolean existsWithPrimaryKey(ID id) {
        return baseMapper.existsWithPrimaryKey(id);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(ID id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int selectCount(T entity) {
        return baseMapper.selectCount(entity);
    }

    @Override
    public List<T> select(T entity) {
        return baseMapper.select(entity);
    }

    @Override
    public T selectOne(T entity) {
        return baseMapper.selectOne(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return baseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteByExample(Example example) {
        return baseMapper.deleteByExample(example);
    }

    @Override
    public List<T> selectByExample(Example example) {
        return baseMapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Example example) {
        return baseMapper.selectCountByExample(example);
    }

    @Override
    public T selectOneByExample(Example example) {
        return baseMapper.selectOneByExample(example);
    }

    @Override
    public int updateByExample(T entity, Example example) {
        return baseMapper.updateByExample(entity,example);
    }

    @Override
    public int updateByExampleSelective(T entity, Example example) {
        return baseMapper.updateByExampleSelective(entity,example);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Example example, RowBounds rowBounds) {
        return baseMapper.selectByExampleAndRowBounds(example,rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T entity, RowBounds rowBounds) {
        return baseMapper.selectByRowBounds(entity,rowBounds);
    }

    @Override
    public int insertList(List<? extends T> list) {
        return baseMapper.insertList(list);
    }

    @Override
    public int insertUseGeneratedKeys(T entity) {
        return baseMapper.insertUseGeneratedKeys(entity);
    }

}