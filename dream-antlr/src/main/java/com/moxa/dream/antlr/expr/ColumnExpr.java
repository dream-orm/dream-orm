package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.Constant;
import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.SingleMarkStatement;
import com.moxa.dream.antlr.smt.Statement;

public class ColumnExpr extends SqlExpr {
    private Statement statement;

    public ColumnExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(Constant.FUNCTION).addExprTypes(Constant.SYMBOL).addExprTypes(ExprType.CASE, ExprType.LBRACE, ExprType.INVOKER, ExprType.SINGLE_MARK);
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) {
        FunctionExpr functionExpr = new FunctionExpr(exprReader);
        statement = functionExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSymbol(ExprInfo exprInfo) {
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        symbolExpr.setExprTypes(Constant.SYMBOL);
        statement = symbolExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCase(ExprInfo exprInfo) {
        CaseExpr caseExpr = new CaseExpr(exprReader);
        statement = caseExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) {
        BraceExpr braceExpr = new BraceExpr(exprReader);
        statement = braceExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInvoker(ExprInfo exprInfo) {
        InvokerExpr invokerExpr = new InvokerExpr(exprReader);
        statement = invokerExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSingleMark(ExprInfo exprInfo) {
        statement = new SingleMarkExpr(exprReader).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return statement;
    }

    public static class SingleMarkExpr extends HelperExpr {
        private SingleMarkStatement statement = new SingleMarkStatement();

        public SingleMarkExpr(ExprReader exprReader) {
            this(exprReader, () -> new CompareExpr(exprReader));
        }

        public SingleMarkExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.SINGLE_MARK);
        }

        @Override
        protected Statement exprSingleMark(ExprInfo exprInfo) {
            push();
            if (statement.getStatement() == null) {
                setExprTypes(ExprType.SELECT, ExprType.INSERT, ExprType.UPDATE, ExprType.DELETE, ExprType.HELP);
            } else setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprSelect(ExprInfo exprInfo) {
            statement.setStatement(new CrudExpr(exprReader).expr());
            setExprTypes(ExprType.SINGLE_MARK);
            return expr();
        }

        @Override
        protected Statement exprInsert(ExprInfo exprInfo) {
            statement.setStatement(new CrudExpr(exprReader).expr());
            setExprTypes(ExprType.SINGLE_MARK);
            return expr();
        }

        @Override
        protected Statement exprUpdate(ExprInfo exprInfo) {
            statement.setStatement(new CrudExpr(exprReader).expr());
            setExprTypes(ExprType.SINGLE_MARK);
            return expr();
        }

        @Override
        protected Statement exprDelete(ExprInfo exprInfo) {
            statement.setStatement(new CrudExpr(exprReader).expr());
            setExprTypes(ExprType.SINGLE_MARK);
            return expr();
        }

        @Override
        public Statement exprHelp(Statement statement) {
            this.statement.setStatement(statement);
            setExprTypes(ExprType.SINGLE_MARK);
            return expr();
        }

        @Override
        protected Statement nil() {
            return statement;
        }
    }
}
