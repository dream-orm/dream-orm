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
public class HavingExpr extends HelperExpr {
    private final HavingStatement havingStatement = new HavingStatement();

    public HavingExpr(ExprReader exprReader) {
        this(exprReader, () -> new CompareExpr(exprReader));
    }

    public HavingExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.HAVING);
    }

    @Override
    protected Statement exprHaving(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    public Statement nil() {
        return havingStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        havingStatement.setCondition(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
