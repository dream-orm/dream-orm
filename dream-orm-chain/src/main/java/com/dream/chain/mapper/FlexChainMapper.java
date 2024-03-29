package com.dream.chain.mapper;

import com.dream.chain.def.ChainDeleteWhereDef;
import com.dream.chain.def.ChainFromDef;
import com.dream.chain.def.ChainInsertIntoColumnsDef;
import com.dream.chain.def.ChainUpdateColumnDef;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.TableDef;

/**
 * 链式强化接口
 */
public interface FlexChainMapper {
    /**
     * 查询链式操作
     *
     * @param columnDefs 查询字段集
     * @return 查询链式
     */
    default ChainFromDef select(ColumnDef... columnDefs) {
        return select(false, columnDefs);
    }

    /**
     * 查询链式操作
     *
     * @param distinct   是否去重
     * @param columnDefs columnDefs 查询字段集
     * @return
     */
    ChainFromDef select(boolean distinct, ColumnDef... columnDefs);

    /**
     * 更新链式操作
     *
     * @param tableDef 表
     * @return 更新链式
     */
    ChainUpdateColumnDef update(TableDef tableDef);

    /**
     * 插入链式操作
     *
     * @param tableDef 表
     * @return 插入链式
     */
    ChainInsertIntoColumnsDef insertInto(TableDef tableDef);

    /**
     * 删除链式操作
     *
     * @param tableDef 表
     * @return 删除链式
     */
    ChainDeleteWhereDef delete(TableDef tableDef);
}
