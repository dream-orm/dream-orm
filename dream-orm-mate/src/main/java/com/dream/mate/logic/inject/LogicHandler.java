package com.dream.mate.logic.inject;

import com.dream.system.config.MethodInfo;
import com.dream.system.table.TableInfo;

public interface LogicHandler {

    /**
     * 判断是否应用逻辑删除
     *
     * @param methodInfo mapper方法详尽信息
     * @param table      主表
     * @return
     */
    default boolean isLogic(MethodInfo methodInfo, String table) {
        if (methodInfo != null) {
            TableInfo tableInfo = methodInfo.getConfiguration().getTableFactory().getTableInfo(table);
            return tableInfo != null && tableInfo.getColumnInfo(getLogicColumn(table)) != null;
        }
        return false;
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
     * @param table 主表
     * @return
     */
    String getLogicColumn(String table);


}
