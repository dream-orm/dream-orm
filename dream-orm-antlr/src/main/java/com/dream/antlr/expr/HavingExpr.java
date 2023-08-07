package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.HavingStatement;
import com.dream.antlr.smt.Statement;

/**
 * 分组条件语法解析器
 */
public class HavingExpr extends SqlExpr {
    private final HavingStatement havingStatement = new HavingStatement();

    public HavingExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.HAVING);
    }

    @Override
    protected Statement exprHaving(ExprInfo exprInfo) throws AntlrException {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader);
        havingStatement.setCondition(operTreeExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return havingStatement;
    }
}
