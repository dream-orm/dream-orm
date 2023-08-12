package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DDLAlterStatement;
import com.dream.antlr.smt.Statement;

/**
 * 修改表重命名语法解析器
 */
public class DDLAlterRenameExpr extends HelperExpr {

    private DDLAlterStatement.DDLAlterRenameStatement ddlAlterRenameStatement;

    public DDLAlterRenameExpr(ExprReader exprReader, DDLAlterStatement.DDLAlterRenameStatement ddlAlterRenameStatement) {
        this(exprReader, () -> new SymbolExpr(exprReader), ddlAlterRenameStatement);
    }

    public DDLAlterRenameExpr(ExprReader exprReader, Helper helper, DDLAlterStatement.DDLAlterRenameStatement ddlAlterRenameStatement) {
        super(exprReader, helper);
        this.ddlAlterRenameStatement = ddlAlterRenameStatement;
        setExprTypes(ExprType.RENAME);
    }

    @Override
    protected Statement exprRename(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.TO, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprTo(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlAlterRenameStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        ddlAlterRenameStatement.setNewTable(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
