package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdQueryStatement;
import com.dream.tdengine.statement.TdSlidingStatement;
import com.dream.tdengine.statement.TdWindowStatement;

public class TdChainSlidingDef extends TdChainFillDef {
    public TdChainSlidingDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }

    public TdChainFillDef sliding(String slidingVal) {
        TdSlidingStatement tdSlidingStatement = new TdSlidingStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.LetterStatement(slidingVal));
        tdSlidingStatement.setParamsStatement(listColumnStatement);
        TdQueryStatement tdQueryStatement = (TdQueryStatement) statement();
        TdWindowStatement.TdIntervalWindowStatement tdIntervalWindowStatement = (TdWindowStatement.TdIntervalWindowStatement) tdQueryStatement.getWindnow();
        tdIntervalWindowStatement.setSliding(tdSlidingStatement);
        return new TdChainFillDef(statement(), creatorFactory(), flexMapper);
    }

    @Override
    public TdChainLimitDef sLimit(Integer offset, Integer rows) {
        return super.sLimit(offset, rows);
    }

    @Override
    public TdChainLimitDef sOffset(Integer offset, Integer rows) {
        return super.sOffset(offset, rows);
    }
}
