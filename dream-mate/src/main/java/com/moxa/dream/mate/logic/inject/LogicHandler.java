package com.moxa.dream.mate.logic.inject;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.table.TableInfo;

public interface LogicHandler {
    default boolean isLogic(MethodInfo methodInfo, TableInfo tableInfo) {
        return tableInfo.getFieldName(getLogicColumn()) != null;
    }

    default String getNormalValue() {
        return "0";
    }

    default String getDeletedValue() {
        return "1";
    }

    String getLogicColumn();


}
