package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdPartitionStatement;
import com.dream.tdengine.statement.TdQueryStatement;

public class TdChainPartitionByDef extends TdChainIntervalDef {
    public TdChainPartitionByDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainIntervalDef partitionBy(String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnDefs[i] = FunctionDef.column(columns[i]);
        }
        return partitionBy(columnDefs);
    }

    public TdChainIntervalDef partitionBy(ColumnDef... columnDefs) {
        TdQueryStatement tdQueryStatement = (TdQueryStatement)statement();
        ListColumnStatement listColumnStatement=new ListColumnStatement();
        for(ColumnDef columnDef:columnDefs){
            listColumnStatement.add(columnDef.getStatement());
        }
        TdPartitionStatement tdPartitionStatement = new TdPartitionStatement();
        tdPartitionStatement.setParamsStatement(listColumnStatement);
        tdQueryStatement.setPartitionBy(tdPartitionStatement);
        return new TdChainIntervalDef(statement(),creatorFactory(),flexMapper);
    }
}
