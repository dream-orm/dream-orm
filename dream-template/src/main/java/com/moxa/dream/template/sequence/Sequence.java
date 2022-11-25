package com.moxa.dream.template.sequence;

import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectWrapper;

public interface Sequence {
    default void init(TableInfo tableInfo) {
    }

    default boolean before() {
        return true;
    }

    default String[] columnNames() {
        return new String[0];
    }

    void sequence(ObjectWrapper wrapper, String property, Object arg);
}
