package com.dream.flex.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.IStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public class FlexTableSymbolStatement extends SymbolStatement implements IStatement {
    private Statement statement;

    public FlexTableSymbolStatement(String table) {
        super(table);
        this.statement = new FlexTableInvokerStatement(table);
    }

    @Override
    public int getNameId() {
        return FlexTableSymbolStatement.class.getSimpleName().hashCode();
    }

    @Override
    public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toSQL.toStr(statement, assist, invokerList);
    }
}
