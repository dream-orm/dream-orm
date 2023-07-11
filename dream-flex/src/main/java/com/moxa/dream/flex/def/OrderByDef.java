package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.LimitStatement;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class OrderByDef extends LimitDef {

    protected OrderByDef(QueryStatement statement) {
        super(statement);
    }

    public LimitDef limit(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(false);
        limitStatement.setFirst(new SymbolStatement.LetterStatement(offset.toString()));
        limitStatement.setSecond(new SymbolStatement.LetterStatement(rows.toString()));
        statement.setLimitStatement(limitStatement);
        return new LimitDef(statement);
    }

    public LimitDef offset(Integer offset, Integer rows) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new SymbolStatement.LetterStatement(rows.toString()));
        limitStatement.setSecond(new SymbolStatement.LetterStatement(offset.toString()));
        statement.setLimitStatement(limitStatement);
        return new LimitDef(statement);
    }
}
