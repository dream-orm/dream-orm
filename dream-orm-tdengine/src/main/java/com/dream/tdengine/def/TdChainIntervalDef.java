package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdIntervalStatement;
import com.dream.tdengine.statement.TdQueryStatement;
import com.dream.tdengine.statement.TdWindowStatement;

public class TdChainIntervalDef extends TdChainSlidingDef {
    public TdChainIntervalDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }

    protected TdChainSlidingDef interval(String intervalVal) {
        return interval(intervalVal, null);
    }

    protected TdChainSlidingDef interval(String intervalVal, String intervalOffset) {
        TdQueryStatement tdQueryStatement = (TdQueryStatement) statement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.LetterStatement(intervalVal));
        if (intervalOffset != null) {
            listColumnStatement.add(new SymbolStatement.LetterStatement(intervalOffset));
        }
        TdIntervalStatement tdIntervalStatement = new TdIntervalStatement();
        tdIntervalStatement.setParamsStatement(listColumnStatement);
        TdWindowStatement.TdIntervalWindowStatement tdIntervalWindowStatement = new TdWindowStatement.TdIntervalWindowStatement();
        tdIntervalWindowStatement.setInterval(tdIntervalStatement);
        tdQueryStatement.setWindnow(tdIntervalWindowStatement);
        return new TdChainSlidingDef(statement(), creatorFactory(), flexMapper);
    }
}
