package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.FromStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

/**
 * from语法解析器
 */
public class FromExpr extends HelperExpr {
    private final FromStatement fromStatement = new FromStatement();

    public FromExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        this(exprReader, () -> new AliasColumnExpr(exprReader, myFunctionFactory), myFunctionFactory);
    }

    public FromExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
        super(exprReader, helper, myFunctionFactory);
        setExprTypes(ExprType.FROM);
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprCross(ExprInfo exprInfo) throws AntlrException {
        return exprJoin(exprInfo);
    }

    @Override
    protected Statement exprLeft(ExprInfo exprInfo) throws AntlrException {
        return exprJoin(exprInfo);
    }

    @Override
    protected Statement exprRight(ExprInfo exprInfo) throws AntlrException {
        return exprJoin(exprInfo);
    }

    @Override
    protected Statement exprInner(ExprInfo exprInfo) throws AntlrException {
        return exprJoin(exprInfo);
    }

    @Override
    protected Statement exprJoin(ExprInfo exprInfo) throws AntlrException {
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new JoinExpr(exprReader, myFunctionFactory), new ExprInfo(ExprType.BLANK, " "), myFunctionFactory);
        ListColumnStatement listColumnStatement = (ListColumnStatement) listColumnExpr.expr();
        if (listColumnStatement.getColumnList().length > 0) {
            fromStatement.setJoinList(listColumnStatement);
        }
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprComma(ExprInfo exprInfo) throws AntlrException {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new JoinExpr.CommaJoinExpr(exprReader, myFunctionFactory), new ExprInfo(ExprType.COMMA, ""), myFunctionFactory);
        fromStatement.setJoinList(listColumnExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return fromStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        fromStatement.setMainTable(statement);
        setExprTypes(ExprType.LEFT, ExprType.RIGHT, ExprType.CROSS, ExprType.INNER, ExprType.JOIN, ExprType.COMMA, ExprType.NIL);
        return expr();
    }
}
