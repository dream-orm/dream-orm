package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.JoinStatement;
import com.dream.antlr.smt.Statement;

/**
 * 表连接语法解析器
 */
public class JoinExpr extends SqlExpr {
    private JoinStatement joinStatement;
    private ExprType[] ON = new ExprType[]{ExprType.ON};

    public JoinExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        super(exprReader, myFunctionFactory);
        setExprTypes(ExprType.LEFT, ExprType.RIGHT, ExprType.CROSS, ExprType.INNER, ExprType.FULL, ExprType.JOIN, ExprType.NIL);
    }

    @Override
    protected Statement exprCross(ExprInfo exprInfo) throws AntlrException {
        push();
        joinStatement = new JoinStatement.CrossJoinStatement();
        ON = new ExprType[]{ExprType.ON, ExprType.NIL};
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprLeft(ExprInfo exprInfo) throws AntlrException {
        push();
        joinStatement = new JoinStatement.LeftJoinStatement();
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprRight(ExprInfo exprInfo) throws AntlrException {
        push();
        joinStatement = new JoinStatement.RightJoinStatement();
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprOuter(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprInner(ExprInfo exprInfo) throws AntlrException {
        push();
        joinStatement = new JoinStatement.InnerJoinStatement();
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprFull(ExprInfo exprInfo) throws AntlrException {
        push();
        joinStatement = new JoinStatement.FullJoinStatement();
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprJoin(ExprInfo exprInfo) throws AntlrException {
        push();
        AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader, myFunctionFactory);
        aliasColumnExpr.setExprTypes(ExprType.HELP);
        if (joinStatement == null) {
            joinStatement = new JoinStatement.InnerJoinStatement();
        }
        joinStatement.setJoinTable(aliasColumnExpr.expr());
        setExprTypes(ON);
        return expr();
    }

    @Override
    protected Statement exprOn(ExprInfo exprInfo) throws AntlrException {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader, myFunctionFactory);
        Statement statement = operTreeExpr.expr();
        joinStatement.setOn(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return joinStatement;
    }

    public static class CommaJoinExpr extends HelperExpr {
        private JoinStatement joinStatement;

        public CommaJoinExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
            this(exprReader, () -> {
                AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader, myFunctionFactory);
                aliasColumnExpr.setExprTypes(ExprType.HELP);
                return aliasColumnExpr;
            }, myFunctionFactory);
        }

        public CommaJoinExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
            super(exprReader, helper, myFunctionFactory);
            setExprTypes(ExprType.HELP);
        }

        @Override
        protected Statement nil() {
            return joinStatement;
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            joinStatement = new JoinStatement.CommaJoinStatement();
            joinStatement.setJoinTable(statement);
            setExprTypes(ExprType.NIL);
            return joinStatement;
        }
    }
}
