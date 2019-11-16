package com.wzc.im.common.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 自定义多继承Mapper
 * @author WANGZIC
 */
public interface IBaseMapper<T> extends Mapper<T>, MySqlMapper<T>{



}

