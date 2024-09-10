package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.Statement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdPartitionStatement;
import com.dream.tdengine.statement.TdQueryStatement;

import java.util.Arrays;

public class TdChainPartitionDef extends TdChainIntervalDef {
    public TdChainPartitionDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }

    public TdChainGroupByDef partitionBy(String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnDefs[i] = FunctionDef.col(columns[i]);
        }
        return partitionBy(columnDefs);
    }

    public TdChainGroupByDef partitionBy(ColumnDef... columnDefs) {
        TdQueryStatement tdQueryStatement = (TdQueryStatement) statement();
        ListColumnStatement listColumnStatement = new ListColumnStatement();
        listColumnStatement.add(Arrays.stream(columnDefs).map(ColumnDef::getStatement).toArray(Statement[]::new));
        TdPartitionStatement tdPartitionStatement = new TdPartitionStatement();
        tdPartitionStatement.setParamsStatement(listColumnStatement);
        tdQueryStatement.setPartitionBy(tdPartitionStatement);
        return (TdChainGroupByDef) creatorFactory().newGroupByDef(statement());
    }
}
