package com.moxa.dream.system.provider;

import com.moxa.dream.system.core.action.Action;

import java.util.Collection;

public interface ActionProvider {

    String sql(Class<?> type);

    default Action[] init(Class<?> type) {
        return null;
    }

    default Action[] loop(Class<?> type) {
        return null;
    }

    default Action[] destroy(Class<?> type) {
        return null;
    }

    default Class<? extends Collection> rowType(Class<?> type) {
        return null;
    }

    default Class<?> colType(Class<?> type) {
        return null;
    }

    default Boolean cache(Class<?> type) {
        return null;
    }

    default Integer timeOut(Class<?> type) {
        return null;
    }
}
