package com.dream.mate.logic.inject;

import com.dream.system.config.MethodInfo;
import com.dream.system.table.TableInfo;

public interface LogicHandler {

    /**
     * 判断是否应用逻辑删除
     *
     * @param methodInfo mapper方法详尽信息
     * @param tableInfo  主表详尽信息
     * @return
     */
    default boolean isLogic(MethodInfo methodInfo, TableInfo tableInfo) {
        return tableInfo.getFieldName(getLogicColumn()) != null;
    }

    /**
     * 未删除的标识
     *
     * @return
     */
    default String getNormalValue() {
        return "0";
    }

    /**
     * 删除后的标识
     *
     * @return
     */
    default String getDeletedValue() {
        return "1";
    }

    /**
     * 逻辑删除字段
     *
     * @return
     */
    String getLogicColumn();


}
