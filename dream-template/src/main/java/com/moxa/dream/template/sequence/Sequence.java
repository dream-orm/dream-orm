package com.moxa.dream.template.sequence;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectWrapper;

public interface Sequence {
    default void init(TableInfo tableInfo) {
    }

    default boolean before() {
        return true;
    }

    default String[] columnNames(TableInfo tableInfo) {
        return new String[0];
    }

    void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg);
}
