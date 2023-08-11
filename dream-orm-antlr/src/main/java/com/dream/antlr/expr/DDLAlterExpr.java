package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DDLAlterStatement;
import com.dream.antlr.smt.Statement;

/**
 * 修改语法解析器
 */
public class DDLAlterExpr extends SqlExpr {
    private DDLAlterStatement ddlAlterStatement;
    private Statement table;

    public DDLAlterExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.ALTER);
    }

    @Override
    protected Statement exprAlter(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.TABLE);
        return expr();
    }

    @Override
    protected Statement exprTable(ExprInfo exprInfo) throws AntlrException {
        push();
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        this.table = symbolExpr.expr();
        setExprTypes(ExprType.ADD, ExprType.DROP, ExprType.MODIFY, ExprType.RENAME);
        return expr();
    }

    @Override
    protected Statement exprAdd(ExprInfo exprInfo) throws AntlrException {
        DDLAlterStatement.DDLAlterAddStatement ddlAlterAddStatement = new DDLAlterStatement.DDLAlterAddStatement();
        ddlAlterAddStatement.setTable(table);
        this.ddlAlterStatement = (DDLAlterStatement) new DDLAlterAddExpr(exprReader, ddlAlterAddStatement).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
        DDLAlterStatement.DDLAlterDropStatement ddlAlterDropStatement = new DDLAlterStatement.DDLAlterDropStatement();
        ddlAlterDropStatement.setTable(table);
        this.ddlAlterStatement = (DDLAlterStatement) new DDLAlterDropExpr(exprReader, ddlAlterDropStatement).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprModify(ExprInfo exprInfo) throws AntlrException {
        DDLAlterStatement.DDLAlterModifyStatement ddlAlterModifyStatement = new DDLAlterStatement.DDLAlterModifyStatement();
        ddlAlterModifyStatement.setTable(table);
        this.ddlAlterStatement = (DDLAlterStatement) new DDLAlterModifyExpr(exprReader, ddlAlterModifyStatement).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRename(ExprInfo exprInfo) throws AntlrException {
        DDLAlterStatement.DDLAlterRenameStatement ddlAlterRenameStatement = new DDLAlterStatement.DDLAlterRenameStatement();
        ddlAlterRenameStatement.setTable(table);
        this.ddlAlterStatement = (DDLAlterStatement) new DDLAlterRenameExpr(exprReader, ddlAlterRenameStatement).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlAlterStatement;
    }
}
