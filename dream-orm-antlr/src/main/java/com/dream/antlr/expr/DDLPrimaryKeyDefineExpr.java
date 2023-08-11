package com.dream.antlr.expr;

import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

import static com.dream.antlr.config.ExprType.PRIMARY;

/**
 * 主键声明语法解析器
 */
public class DDLPrimaryKeyDefineExpr extends SqlExpr {

    public DDLPrimaryKeyDefineExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(PRIMARY);
    }

    @Override
    protected Statement nil() {
        return null;
    }
}
