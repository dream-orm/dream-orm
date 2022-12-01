package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.antlr.smt.Statement;

public class InsertExpr extends HelperExpr {
    private final InsertStatement insertStatement = new InsertStatement();

    public InsertExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public InsertExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.INSERT);
    }

    @Override
    protected Statement exprInsert(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.INTO);
        return expr();
    }

    @Override
    protected Statement exprInto(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) {
        BraceExpr braceExpr = new BraceExpr(exprReader);
        Statement statement = braceExpr.expr();
        insertStatement.setParams(statement);
        setExprTypes(ExprType.VALUE, ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    @Override
    protected Statement exprValue(ExprInfo exprInfo) {
        ValueExpr valuesExpr = new ValueExpr(exprReader);
        insertStatement.setValues(valuesExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprValues(ExprInfo exprInfo) {
        ValuesExpr valuesExpr = new ValuesExpr(exprReader);
        insertStatement.setValues(valuesExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) {
        QueryExpr queryExpr = new QueryExpr(exprReader);
        Statement statement = queryExpr.expr();
        insertStatement.setValues(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return insertStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) {
        insertStatement.setTable(statement);
        setExprTypes(ExprType.LBRACE, ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    public static class ValueExpr extends SqlExpr {
        private final InsertStatement.ValueStatement valueStatement = new InsertStatement.ValueStatement();

        public ValueExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.VALUE);
        }

        @Override
        protected Statement exprValue(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.LBRACE);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) {
            BraceExpr braceExpr = new BraceExpr(exprReader);
            valueStatement.setStatement(braceExpr.expr());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return valueStatement;
        }
    }

    public static class ValuesExpr extends SqlExpr {
        private final InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();

        public ValuesExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.VALUES);
        }

        @Override
        protected Statement exprValues(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.LBRACE);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) {
            BraceExpr braceExpr = new BraceExpr(exprReader);
            valuesStatement.setStatement(braceExpr.expr());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return valuesStatement;
        }
    }
}
