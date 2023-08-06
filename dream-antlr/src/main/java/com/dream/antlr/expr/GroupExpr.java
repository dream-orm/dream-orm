package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.Statement;

/**
 * 分组语法解析器
 */
public class GroupExpr extends SqlExpr {
    private final GroupStatement groupStatement = new GroupStatement();

    public GroupExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.GROUP);
    }

    @Override
    protected Statement exprGroup(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.BY);
        return expr();
    }

    @Override
    protected Statement exprBy(ExprInfo exprInfo) throws AntlrException {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ","));

        groupStatement.setGroup(listColumnExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return groupStatement;
    }
}
