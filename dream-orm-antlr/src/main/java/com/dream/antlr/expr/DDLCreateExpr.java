package com.dream.antlr.expr;

import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

/**
 * 创建语法解析器
 */
public class DDLCreateExpr extends HelperExpr {

    public DDLCreateExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public DDLCreateExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.CREATE);
    }

    @Override
    protected Statement nil() {
        return null;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        return null;
    }

}
