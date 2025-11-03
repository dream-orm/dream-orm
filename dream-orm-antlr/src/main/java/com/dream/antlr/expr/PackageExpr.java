package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.smt.Statement;

/**
 * 语法编译器入口
 */
public class PackageExpr extends SqlExpr {
    private final PackageStatement statement = new PackageStatement();

    public PackageExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        super(exprReader, myFunctionFactory);
        setExprTypes(ExprType.LBRACE, ExprType.INVOKER, ExprType.SELECT, ExprType.INSERT, ExprType.UPDATE, ExprType.DELETE, ExprType.WITH, ExprType.REPLACE, ExprType.ACC);
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
        setExprTypes(ExprType.SEMICOLON, ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprInvoker(ExprInfo exprInfo) throws AntlrException {
        InvokerExpr invokerExpr = new InvokerExpr(exprReader, myFunctionFactory);
        statement.setStatement(invokerExpr.expr());
        setExprTypes(ExprType.SEMICOLON, ExprType.ACC);
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

    @Override
    protected Statement exprWith(ExprInfo exprInfo) throws AntlrException {
        WithExpr withExpr = new WithExpr(exprReader, myFunctionFactory);
        statement.setStatement(withExpr.expr());
        setExprTypes(ExprType.SEMICOLON, ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprReplace(ExprInfo exprInfo) throws AntlrException {
        return exprDML(exprInfo);
    }

    protected Statement exprDML(ExprInfo exprInfo) throws AntlrException {
        DMLExpr dmlExpr = new DMLExpr(exprReader, myFunctionFactory);
        statement.setStatement(dmlExpr.expr());
        setExprTypes(ExprType.SEMICOLON, ExprType.ACC);
        return expr();
    }

    @Override
    protected Statement exprSemicolon(ExprInfo exprInfo) throws AntlrException {
        push();
        statement.setSemicolon(true);
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
