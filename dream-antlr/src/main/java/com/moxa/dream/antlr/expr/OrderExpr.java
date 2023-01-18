package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.OrderStatement;
import com.moxa.dream.antlr.smt.Statement;

public class OrderExpr extends SqlExpr {
    private final OrderStatement orderStatement = new OrderStatement();

    public OrderExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.ORDER);
    }

    @Override
    protected Statement exprOrder(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.BY);
        return expr();
    }

    @Override
    protected Statement exprBy(ExprInfo exprInfo) throws AntlrException {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new AscDescExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
        orderStatement.setOrder(listColumnExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return orderStatement;
    }

    class AscDescExpr extends HelperExpr {
        private Statement sortStatement;
        private Statement statement;

        public AscDescExpr(ExprReader exprReader) {
            this(exprReader, () -> new ColumnExpr(exprReader));
        }


        public AscDescExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
        }

        @Override
        protected Statement exprAsc(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new OrderStatement.AscStatement(sortStatement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprDesc(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new OrderStatement.DescStatement(sortStatement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            if (statement == null) {
                statement = sortStatement;
            }
            return statement;
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            this.sortStatement = statement;
            setExprTypes(ExprType.NIL, ExprType.ASC, ExprType.DESC);
            return expr();
        }
    }
}
