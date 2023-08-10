package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DDLDropStatement;
import com.dream.antlr.smt.Statement;

/**
 * 删除表语法解析器
 */
public class DDLDropExpr extends HelperExpr {

    private DDLDropStatement ddlDropStatement;

    public DDLDropExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public DDLDropExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.DROP);
    }

    @Override
    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.DATABASE, ExprType.TABLE);
        return expr();
    }

    @Override
    protected Statement exprDatabase(ExprInfo exprInfo) throws AntlrException {
        ddlDropStatement = new DDLDropStatement.DDLDropDatabaseStatement();
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprTable(ExprInfo exprInfo) throws AntlrException {
        ddlDropStatement = new DDLDropStatement.DDLDropTableStatement();
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        ddlDropStatement.setStatement(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlDropStatement;
    }
}
