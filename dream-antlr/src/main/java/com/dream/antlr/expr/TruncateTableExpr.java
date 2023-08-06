package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.TruncateTableStatement;

/**
 * 清空表语法解析器
 */
public class TruncateTableExpr extends HelperExpr {

    private final TruncateTableStatement truncateTableStatement = new TruncateTableStatement();

    public TruncateTableExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public TruncateTableExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.TRUNCATE);
    }

    @Override
    protected Statement exprTruncate(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.TABLE);
        return expr();
    }

    @Override
    protected Statement exprTable(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        truncateTableStatement.setTable(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return truncateTableStatement;
    }
}
