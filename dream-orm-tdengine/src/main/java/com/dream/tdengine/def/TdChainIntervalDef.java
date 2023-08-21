package com.dream.tdengine.def;

import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdIntervalStatement;
import com.dream.tdengine.statement.TdQueryStatement;

public class TdChainIntervalDef extends TdChainSlidingDef {
    public TdChainIntervalDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainSlidingDef interval(String intervalVal) {
        return interval(intervalVal, null);
    }

    public TdChainSlidingDef interval(String intervalVal, String intervalOffset) {
        TdQueryStatement tdQueryStatement = (TdQueryStatement) statement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.LetterStatement(intervalVal));
        if (intervalOffset != null) {
            listColumnStatement.add(new SymbolStatement.LetterStatement(intervalOffset));
        }
        TdIntervalStatement tdIntervalStatement = new TdIntervalStatement();
        tdIntervalStatement.setParamsStatement(listColumnStatement);
        tdQueryStatement.setInterval(tdIntervalStatement);
        return new TdChainSlidingDef(statement(), creatorFactory(), flexMapper);
    }

    public TdChainHavingDef groupBy(ColumnDef... columnDefs) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.getStatement());
        }
        groupStatement.setGroup(listColumnStatement);
        statement().setGroupStatement(groupStatement);
        return (TdChainHavingDef) creatorFactory().newGroupByDef(statement());
    }
}
