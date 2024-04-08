package com.dream.stream.wrapper;

import com.dream.antlr.smt.FunctionStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

public class FunctionWrapper {
    private final ListColumnStatement columnStatement = new ListColumnStatement(",");

    public FunctionWrapper ascii(String column) {
        return functionWrapper(new FunctionStatement.AsciiStatement(), col(column));
    }

    public FunctionWrapper len(String column) {
        return functionWrapper(new FunctionStatement.CharLengthStatement(), col(column));
    }

    public FunctionWrapper length(String column) {
        return functionWrapper(new FunctionStatement.LengthStatement(), col(column));
    }

    public FunctionWrapper concat(String column, String... columns) {
        Statement[] statements = new Statement[columns.length + 1];
        statements[0] = col(column);
        for (int i = 0; i < columns.length; i++) {
            statements[i + 1] = col(columns[i]);
        }
        return functionWrapper(new FunctionStatement.ConcatStatement(), statements);
    }

    protected Statement col(String column) {
        return new SymbolStatement.LetterStatement(column);
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
        return wrap(functionStatement);
    }

    public FunctionWrapper wrap(String column) {
        return wrap(new SymbolStatement.LetterStatement(column));
    }

    public FunctionWrapper wrap(Statement statement) {
        this.columnStatement.add(statement);
        return this;
    }

    public ListColumnStatement getColumnStatement() {
        return columnStatement;
    }
}
