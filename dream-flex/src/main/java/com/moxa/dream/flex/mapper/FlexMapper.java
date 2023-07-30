package com.moxa.dream.flex.mapper;

import com.moxa.dream.flex.def.Query;
import com.moxa.dream.flex.def.Update;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface FlexMapper {
    Set<Class<? extends Inject>> WHITE_SET = new HashSet<>();

    <T> T selectOne(Query query, Class<T> type);

    <T> List<T> selectList(Query query, Class<T> type);

    <T> Page<T> selectPage(Query query, Class<T> type, Page page);

    int update(Update update);
}
