package com.moxa.dream.template.sequence;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.table.TableInfo;

public interface Sequence {
    default void init(TableInfo tableInfo) {
    }

    default boolean before() {
        return true;
    }

    //不需要重写此方法
    default String[] columnNames(TableInfo tableInfo) {
        return new String[0];
    }

    void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg);
}
