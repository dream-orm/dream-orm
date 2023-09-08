package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.WhereStatement;

/**
 * 条件语法解析器
 */
public class WhereExpr extends SqlExpr {
    private final WhereStatement whereStatement = new WhereStatement();

    public WhereExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.WHERE);
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) throws AntlrException {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader);
        Statement statement = operTreeExpr.expr();
        whereStatement.setStatement(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return whereStatement;
    }
}
