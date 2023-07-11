package com.moxa.dream.flex.mapper;

import com.moxa.dream.flex.def.SqlDef;
import com.moxa.dream.system.config.Page;

import java.util.List;

public interface FlexMapper {
    <T> T selectOne(SqlDef sqlDef, Class<T> type);

    <T> List<T> selectList(SqlDef sqlDef, Class<T> type);

    <T> Page<T> selectPage(SqlDef sqlDef, Class<T> type, Page page);

}
