package com.dream.stream.mapper;

import com.dream.stream.wrapper.QueryWrapper;
import com.dream.system.config.Page;

import java.util.List;

public interface StreamMapper {
    <T> T selectOne(QueryWrapper<T> queryWrapper);

    <T> List<T> selectList(QueryWrapper<T> queryWrapper);

    <T> Page<T> selectPage(QueryWrapper<T> queryWrapper, Page<T> page);

    <T> boolean exists(QueryWrapper<T> queryWrapper);
}
