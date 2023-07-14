package com.moxa.dream.flex.mapper;

import com.moxa.dream.flex.def.Query;
import com.moxa.dream.system.config.Page;

import java.util.List;

public interface FlexMapper {
    <T> T selectOne(Query query, Class<T> type);

    <T> List<T> selectList(Query query, Class<T> type);

    <T> Page<T> selectPage(Query query, Class<T> type, Page page);

}
