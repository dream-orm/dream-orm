package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

/**
 * 插入语法解析器
 */
public class InsertExpr extends HelperExpr {
    private final InsertStatement insertStatement = new InsertStatement();

    public InsertExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        this(exprReader, () -> new SymbolExpr(exprReader, myFunctionFactory), myFunctionFactory);
    }

    public InsertExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
        super(exprReader, helper, myFunctionFactory);
        setExprTypes(ExprType.INSERT);
    }

    @Override
    protected Statement exprInsert(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.IGNORE, ExprType.INTO);
        return expr();
    }

    @Override
    protected Statement exprIgnore(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.INTO);
        insertStatement.setIgnore(true);
        return expr();
    }

    @Override
    protected Statement exprInto(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.MY_FUNCTION, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprMyFunction(ExprInfo exprInfo) throws AntlrException {
        push();
        insertStatement.setTable(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.LBRACE);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        BraceExpr braceExpr = new BraceExpr(exprReader, myFunctionFactory);
        Statement statement = braceExpr.expr();
        insertStatement.setColumns(statement);
        setExprTypes(ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    @Override
    protected Statement exprValues(ExprInfo exprInfo) throws AntlrException {
        ValuesExpr valuesExpr = new ValuesExpr(exprReader, myFunctionFactory);
        insertStatement.setValues(valuesExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        QueryExpr queryExpr = new QueryExpr(exprReader, myFunctionFactory);
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
    protected Statement exprHelp(Statement statement) throws AntlrException {
        insertStatement.setTable(statement);
        setExprTypes(ExprType.LBRACE, ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    public static class ValuesExpr extends HelperExpr {
        private final InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();

        public ValuesExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
            this(exprReader, () -> new ListColumnExpr(exprReader, () -> new BraceExpr(exprReader, myFunctionFactory), new ExprInfo(ExprType.COMMA, ","), myFunctionFactory), myFunctionFactory);
        }

        public ValuesExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
            super(exprReader, helper, myFunctionFactory);
            setExprTypes(ExprType.VALUES);
        }

        @Override
        protected Statement exprValues(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.INVOKER, ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement exprInvoker(ExprInfo exprInfo) throws AntlrException {
            InvokerExpr invokerExpr = new InvokerExpr(exprReader, myFunctionFactory);
            Statement statement = invokerExpr.expr();
            valuesStatement.setStatement(statement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            valuesStatement.setStatement(statement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return valuesStatement;
        }

    }
}
