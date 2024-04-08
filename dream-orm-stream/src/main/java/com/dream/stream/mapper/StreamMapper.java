package com.dream.stream.mapper;

import com.dream.stream.wrapper.QueryWrapper;
import com.dream.struct.factory.StructFactory;
import com.dream.system.config.MethodInfo;
import com.dream.system.config.Page;

import java.util.List;
import java.util.function.Consumer;

public interface StreamMapper {
    StreamMapper useDialect(StructFactory dialectFactory);

    StreamMapper useMethodInfo(Consumer<MethodInfo> consumer);

    <T> T selectOne(QueryWrapper<T> queryWrapper);

    <T> List<T> selectList(QueryWrapper<T> queryWrapper);

    <T> Page<T> selectPage(QueryWrapper<T> queryWrapper, Page<T> page);

    <T> boolean exists(QueryWrapper<T> queryWrapper);
}
