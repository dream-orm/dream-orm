package com.moxa.dream.mate.logic.interceptor;

import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.table.TableInfo;

public interface LogicHandler {
    default boolean isLogic(MethodInfo methodInfo, TableInfo tableInfo) {
        return tableInfo.getFieldName(getLogicColumn()) != null;
    }

    default String getPositiveValue() {
        return "1";
    }

    default String getNegativeValue() {
        return "0";
    }

    String getLogicColumn();


}
