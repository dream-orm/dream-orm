package com.dream.antlr.expr;

import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

/**
 * 创建字段语法解析器
 */
public class DDLCreateColumnExpr extends HelperExpr {

    public DDLCreateColumnExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    @Override
    protected Statement nil() {
        return null;
    }

    public DDLCreateColumnExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.CREATE);
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        return null;
    }

}
