package com.dream.template.sequence;

import com.dream.system.config.MappedStatement;
import com.dream.system.table.TableInfo;

/**
 * 主键序列策略
 */
public interface Sequence {
    /**
     * 是否主键自增
     *
     * @return
     */
    boolean isAutoIncrement(TableInfo tableInfo);

    /**
     * 自定义主键策略方法
     *
     * @param tableInfo       主表详情
     * @param mappedStatement 编译后的接口方法详情
     * @param result
     */
    void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object result);
}
