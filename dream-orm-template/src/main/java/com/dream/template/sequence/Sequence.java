package com.dream.template.sequence;

import com.dream.system.config.MappedStatement;
import com.dream.system.table.TableInfo;

/**
 * 主键序列策略
 */
public interface Sequence {
    /**
     * 主键序列策略初始化
     *
     * @param tableInfo 主表详情
     */
    default void init(TableInfo tableInfo) {
    }

    /**
     * 是否SQL操作前进行策略操作
     *
     * @return
     */
    default boolean before() {
        return true;
    }

    /**
     * 返回SQL返回字段
     *
     * @param tableInfo 主表详情
     * @return
     */
    default String[] columnNames(TableInfo tableInfo) {
        return new String[0];
    }

    /**
     * 自定义主键策略方法
     *
     * @param tableInfo       主表详情
     * @param mappedStatement 编译后的接口方法详情
     * @param arg             参数
     */
    void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg);
}
