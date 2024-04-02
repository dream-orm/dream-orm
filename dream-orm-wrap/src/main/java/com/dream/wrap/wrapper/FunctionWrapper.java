package com.dream.wrap.wrapper;

import com.dream.antlr.smt.FunctionStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

public class FunctionWrapper {
    private ListColumnStatement columnStatement = new ListColumnStatement(",");

    public FunctionWrapper ascii(String column) {
        return functionWrapper(new FunctionStatement.AsciiStatement(), column(column));
    }

    public FunctionWrapper len(String column) {
        return functionWrapper(new FunctionStatement.CharLengthStatement(), column(column));
    }

    public FunctionWrapper length(String column) {
        return functionWrapper(new FunctionStatement.LengthStatement(), column(column));
    }

    protected Statement column(String column) {
        return new SymbolStatement.SingleMarkStatement(String.valueOf(column));
    }

    protected FunctionWrapper functionWrapper(FunctionStatement functionStatement, Statement... statements) {
        return functionWrapper(functionStatement, ",", statements);
    }

    protected FunctionWrapper functionWrapper(FunctionStatement functionStatement, String split, Statement... statements) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(split);
        for (Statement statement : statements) {
            listColumnStatement.add(statement);
        }
        functionStatement.setParamsStatement(listColumnStatement);
        this.columnStatement.add(functionStatement);
        return this;
    }

    protected ListColumnStatement getColumnStatement() {
        return columnStatement;
    }
}
