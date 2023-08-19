package com.dream.tdengine.mapper;

import com.dream.tdengine.def.TdChainDeleteTableDef;
import com.dream.tdengine.def.TdChainInsertIntoTableDef;
import com.dream.tdengine.def.TdChainSelectDef;
import com.dream.tdengine.def.TdChainUpdateColumnDef;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.TableDef;

/**
 * 链式强化接口
 */
public interface FlexTdChainMapper {
    /**
     * 查询链式操作
     *
     * @param columnDefs 查询字段集
     * @return 查询链式
     */
    TdChainSelectDef select(ColumnDef... columnDefs);

    /**
     * 更新链式操作
     *
     * @param tableDef 表
     * @return 更新链式
     */
    TdChainUpdateColumnDef update(TableDef tableDef);

    /**
     * 插入链式操作
     *
     * @param subTableName 表
     * @return 插入链式
     */
    TdChainInsertIntoTableDef insertInto(String subTableName);

    /**
     * 删除链式操作
     *
     * @param tableDef 表
     * @return 删除链式
     */
    TdChainDeleteTableDef delete(TableDef tableDef);
}
