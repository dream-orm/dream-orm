package com.dream.template.sequence;

import com.dream.system.table.TableInfo;

public interface SequenceWrapper extends Sequence {

    /**
     * 返回查询的字段
     *
     * @param tableInfo 主表详情
     * @return
     */
    default String[] columnNames(TableInfo tableInfo) {
        return new String[0];
    }
}
