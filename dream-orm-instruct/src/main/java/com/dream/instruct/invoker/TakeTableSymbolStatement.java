package com.dream.instruct.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.IStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public class TakeTableSymbolStatement extends SymbolStatement implements IStatement {
    private Statement statement;

    public TakeTableSymbolStatement(String table) {
        super(table);
        this.statement = new TakeTableInvokerStatement(table);
    }

    @Override
    public int getNameId() {
        return TakeTableSymbolStatement.class.getSimpleName().hashCode();
    }

    @Override
    public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toSQL.toStr(statement, assist, invokerList);
    }
}
