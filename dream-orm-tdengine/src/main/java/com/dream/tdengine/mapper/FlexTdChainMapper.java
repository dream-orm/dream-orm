package com.dream.tdengine.mapper;

import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.TableDef;
import com.dream.tdengine.def.TdChainDeleteTableDef;
import com.dream.tdengine.def.TdChainInsertIntoTableDef;
import com.dream.tdengine.def.TdChainSelectDef;
import com.dream.tdengine.def.TdChainUpdateColumnDef;

/**
 * 链式强化接口
 */
public interface FlexTdChainMapper {
    default TdChainSelectDef select(String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columnDefs.length; i++) {
            columnDefs[i] = FunctionDef.col(columns[i]);
        }
        return select(columnDefs);
    }

    default TdChainSelectDef select(boolean distinct, String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columnDefs.length; i++) {
            columnDefs[i] = FunctionDef.col(columns[i]);
        }
        return select(distinct, columnDefs);
    }

    /**
     * 查询链式操作
     *
     * @param columnDefs 查询字段集
     * @return 查询链式
     */
    TdChainSelectDef select(ColumnDef... columnDefs);

    /**
     * 查询链式操作
     *
     * @param distinct   是否去重
     * @param columnDefs columnDefs 查询字段集
     * @return
     */
    TdChainSelectDef select(boolean distinct, ColumnDef... columnDefs);

    default TdChainUpdateColumnDef update(String table) {
        return update(FunctionDef.tab(table));
    }

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

    default TdChainDeleteTableDef delete(String table) {
        return delete(FunctionDef.tab(table));
    }

    /**
     * 删除链式操作
     *
     * @param tableDef 表
     * @return 删除链式
     */
    TdChainDeleteTableDef delete(TableDef tableDef);
}
