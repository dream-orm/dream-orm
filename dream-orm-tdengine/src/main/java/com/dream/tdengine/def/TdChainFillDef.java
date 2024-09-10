package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.struct.invoker.TakeMarkInvokerStatement;
import com.dream.tdengine.statement.TdFillStatement;
import com.dream.tdengine.statement.TdQueryStatement;
import com.dream.tdengine.statement.TdWindowStatement;

import java.util.Arrays;

public class TdChainFillDef extends TdChainOrderByDef {
    public TdChainFillDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }

    public TdChainOrderByDef fillNone() {
        return fill("NONE");
    }

    public TdChainOrderByDef fillValue(Object... values) {
        return fill("VALUE", values);
    }

    public TdChainOrderByDef fillPrev() {
        return fill("PREV");
    }

    public TdChainOrderByDef fillNull() {
        return fill("NULL");
    }

    public TdChainOrderByDef fillLinear() {
        return fill("LINEAR");
    }

    public TdChainOrderByDef fillNext() {
        return fill("NEXT");
    }

    public TdChainOrderByDef fill(String flag, Object... values) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (flag != null) {
            listColumnStatement.add(new SymbolStatement.LetterStatement(flag));
        }
        if (values != null && values.length > 0) {
            listColumnStatement.add(Arrays.stream(values).map(value -> new TakeMarkInvokerStatement(null, value)).toArray(Statement[]::new));
        }
        TdFillStatement tdFillStatement = new TdFillStatement();
        tdFillStatement.setParamsStatement(listColumnStatement);
        TdQueryStatement tdQueryStatement = (TdQueryStatement) statement();
        TdWindowStatement.TdIntervalWindowStatement tdIntervalWindowStatement = (TdWindowStatement.TdIntervalWindowStatement) tdQueryStatement.getWindnow();
        tdIntervalWindowStatement.setFill(tdFillStatement);
        return (TdChainOrderByDef) creatorFactory().newOrderByDef(statement());
    }
}
