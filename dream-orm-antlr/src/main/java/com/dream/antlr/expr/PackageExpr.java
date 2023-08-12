package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.smt.Statement;

/**
 * 语法编译器入口
 */
public class PackageExpr extends HelperExpr {
    private final PackageStatement statement = new PackageStatement();

    public PackageExpr(ExprReader exprReader) {
        this(exprReader, () -> new CrudExpr(exprReader));
    }

    public PackageExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.LBRACE, ExprType.INVOKER, ExprType.CREATE, ExprType.ALTER, ExprType.TRUNCATE, ExprType.DROP, ExprType.HELP, ExprType.ACC);
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        BraceStatement braceStatement = new BraceStatement();
        braceStatement.setStatement(this.expr());
        statement.setStatement(braceStatement);
        setExprTypes(ExprType.RBRACE);
        return expr();
    }

    @Override
    protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprInvoker(ExprInfo exprInfo) throws AntlrException {
        statement.setStatement(new InvokerExpr(exprReader).expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprCreate(ExprInfo exprInfo) throws AntlrException {
        statement.setStatement(new DDLCreateExpr(exprReader).expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprAlter(ExprInfo exprInfo) throws AntlrException {
        statement.setStatement(new DDLAlterExpr(exprReader).expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprTruncate(ExprInfo exprInfo) throws AntlrException {
        statement.setStatement(new DDLTruncateExpr(exprReader).expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
        statement.setStatement(new DDLDropExpr(exprReader).expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprAcc(ExprInfo exprInfo) throws AntlrException {
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        this.statement.setStatement(statement);
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement nil() {
        return statement;
    }
}
