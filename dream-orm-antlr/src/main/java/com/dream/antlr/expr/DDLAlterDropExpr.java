package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DDLAlterStatement;
import com.dream.antlr.smt.Statement;

/**
 * 修改表删除字段语法解析器
 */
public class DDLAlterDropExpr extends HelperExpr {

    private DDLAlterStatement.DDLAlterDropStatement ddlAlterDropStatement;

    public DDLAlterDropExpr(ExprReader exprReader, DDLAlterStatement.DDLAlterDropStatement ddlAlterDropStatement) {
        this(exprReader, () -> new SymbolExpr(exprReader), ddlAlterDropStatement);
    }

    public DDLAlterDropExpr(ExprReader exprReader, Helper helper, DDLAlterStatement.DDLAlterDropStatement ddlAlterDropStatement) {
        super(exprReader, helper);
        this.ddlAlterDropStatement = ddlAlterDropStatement;
        setExprTypes(ExprType.DROP);
    }

    @Override
    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.COLUMN, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprColumn(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlAlterDropStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        ddlAlterDropStatement.setColumn(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
