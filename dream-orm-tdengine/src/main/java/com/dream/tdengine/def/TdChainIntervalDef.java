package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdIntervalStatement;
import com.dream.tdengine.statement.TdQueryStatement;

public class TdChainIntervalDef extends TdChainGroupByDef {
    public TdChainIntervalDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainHavingDef interval(String intervalVal) {
        return interval(intervalVal, null);
    }

    public TdChainHavingDef interval(String intervalVal, String intervalOffset) {
        TdQueryStatement tdQueryStatement = (TdQueryStatement)statement();
        ListColumnStatement listColumnStatement=new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.LetterStatement(intervalVal));
        if(intervalOffset!=null){
            listColumnStatement.add(new SymbolStatement.LetterStatement(intervalOffset));
        }
        TdIntervalStatement tdIntervalStatement=new TdIntervalStatement();
        tdIntervalStatement.setParamsStatement(listColumnStatement);
        tdQueryStatement.setInterval(tdIntervalStatement);
        return (TdChainHavingDef) creatorFactory().newHavingDef(tdQueryStatement);
    }


}
