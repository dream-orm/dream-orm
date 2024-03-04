package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.smt.Statement;

/**
 * 语法编译器入口，该类继承HelperExpr，表示本语法器可以请求其他语法器辅助解析，进而完成复杂解析
 */
public class PackageExpr extends SqlExpr {
    private final PackageStatement statement = new PackageStatement();

    /**
     * 本语法器可以让CrudExpr语法器辅助解析
     *
     * @param exprReader
     */
    public PackageExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.LBRACE, ExprType.INVOKER, ExprType.SELECT, ExprType.INSERT, ExprType.UPDATE, ExprType.DELETE, ExprType.CREATE, ExprType.ALTER, ExprType.TRUNCATE, ExprType.DROP, ExprType.ACC);
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
        InvokerExpr invokerExpr = new InvokerExpr(exprReader);
        statement.setStatement(invokerExpr.expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        return exprDML(exprInfo);
    }

    @Override
    protected Statement exprInsert(ExprInfo exprInfo) throws AntlrException {
        return exprDML(exprInfo);
    }

    @Override
    protected Statement exprUpdate(ExprInfo exprInfo) throws AntlrException {
        return exprDML(exprInfo);
    }

    @Override
    protected Statement exprDelete(ExprInfo exprInfo) throws AntlrException {
        return exprDML(exprInfo);
    }

    protected Statement exprDML(ExprInfo exprInfo) throws AntlrException {
        DMLExpr dmlExpr = new DMLExpr(exprReader);
        statement.setStatement(dmlExpr.expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprCreate(ExprInfo exprInfo) throws AntlrException {
        return exprDDL(exprInfo);
    }

    @Override
    protected Statement exprAlter(ExprInfo exprInfo) throws AntlrException {
        return exprDDL(exprInfo);
    }

    @Override
    protected Statement exprTruncate(ExprInfo exprInfo) throws AntlrException {
        return exprDDL(exprInfo);
    }

    @Override
    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
        return exprDDL(exprInfo);
    }

    protected Statement exprDDL(ExprInfo exprInfo) throws AntlrException {
        DDLExpr ddlExpr = new DDLExpr(exprReader);
        statement.setStatement(ddlExpr.expr());
        setExprTypes(ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprAcc(ExprInfo exprInfo) throws AntlrException {
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return statement;
    }
}
