package com.moxa.dream.sql.mock;

import com.moxa.dream.system.config.MappedStatement;

import java.util.Collection;

public interface MockCompileFactory {
    MappedStatement compile(String sql,Object param, Class<? extends Collection> rowType, Class<?> colType,boolean cache, int timeOut);
}
