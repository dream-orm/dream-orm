package com.dream.tdengine.def;

import com.dream.system.config.Page;

import java.util.List;

public interface TdChainQuery {
    <T> T one(Class<T> type);

    <T> List<T> list(Class<T> type);

    <T> Page<T> page(Class<T> type, Page page);
}
