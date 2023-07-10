package com.moxa.dream.flex.mapper;

import com.moxa.dream.flex.def.ForUpdateDef;
import com.moxa.dream.system.config.Page;

import java.util.List;

public interface FlexMapper {
    <T> T selectOne(ForUpdateDef forUpdateDef, Class<T> type);

    <T> List<T> selectList(ForUpdateDef forUpdateDef, Class<T> type);

    <T> Page<T> selectPage(ForUpdateDef forUpdateDef, Class<T> type, Page page);

}
