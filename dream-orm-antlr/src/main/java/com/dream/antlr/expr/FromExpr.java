package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.FromStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

/**
 * from语法解析器
 */
public class FromExpr extends HelperExpr {
    private final FromStatement fromStatement = new FromStatement();

    public FromExpr(ExprReader exprReader) {
        this(exprReader, () -> new AliasColumnExpr(exprReader));
    }

    public FromExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
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
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new JoinExpr(exprReader), new ExprInfo(ExprType.BLANK, " "));
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
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new JoinExpr.CommaJoinExpr(exprReader), new ExprInfo(ExprType.COMMA, ""));
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
