package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.OrderStatement;
import com.dream.antlr.smt.Statement;

/**
 * 排序语法解析器
 */
public class OrderExpr extends HelperExpr {
    private final OrderStatement orderStatement = new OrderStatement();

    public OrderExpr(ExprReader exprReader) {
        this(exprReader, () -> new ListColumnExpr(exprReader, () -> new AscDescExpr(exprReader), new ExprInfo(ExprType.COMMA, ",")));
    }

    public OrderExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
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
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    public Statement nil() {
        return orderStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        orderStatement.setStatement(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    static class AscDescExpr extends HelperExpr {
        private Statement sortStatement;
        private Statement statement;

        public AscDescExpr(ExprReader exprReader) {
            this(exprReader, () -> new CompareExpr(exprReader));
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
