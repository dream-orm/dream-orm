package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.OverStatement;
import com.dream.antlr.smt.Statement;

public class OverExpr extends SqlExpr {
    private OverStatement overStatement;

    public OverExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        super(exprReader, myFunctionFactory);
        setExprTypes(ExprType.OVER);
    }

    @Override
    protected Statement exprOver(ExprInfo exprInfo) throws AntlrException {
        overStatement = new OverStatement();
        push();
        setExprTypes(ExprType.LBRACE);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.PARTITION, ExprType.ORDER, ExprType.RBRACE);
        return expr();
    }

    @Override
    protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprPartition(ExprInfo exprInfo) throws AntlrException {
        overStatement.setPartitionStatement(new PartitionExpr(exprReader, myFunctionFactory).expr());
        setExprTypes(ExprType.ORDER, ExprType.RBRACE);
        return expr();
    }

    @Override
    protected Statement exprOrder(ExprInfo exprInfo) throws AntlrException {
        overStatement.setOrderStatement(new OrderExpr(exprReader, myFunctionFactory).expr());
        setExprTypes(ExprType.RBRACE);
        return expr();
    }

    @Override
    protected Statement nil() {
        return overStatement;
    }

    public static class PartitionExpr extends HelperExpr {
        private OverStatement.PartitionStatement partitionStatement;

        public PartitionExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
            this(exprReader, () -> new ColumnExpr(exprReader, myFunctionFactory), myFunctionFactory);
        }


        public PartitionExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
            super(exprReader, helper, myFunctionFactory);
            setExprTypes(ExprType.PARTITION);
        }

        @Override
        protected Statement exprPartition(ExprInfo exprInfo) throws AntlrException {
            partitionStatement = new OverStatement.PartitionStatement();
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
        protected Statement exprHelp(Statement statement) throws AntlrException {
            partitionStatement.setStatement(statement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return partitionStatement;
        }
    }
}
