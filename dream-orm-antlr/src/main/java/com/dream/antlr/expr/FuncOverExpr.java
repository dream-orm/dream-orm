package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.FuncOverStatement;
import com.dream.antlr.smt.Statement;

/**
 * 函数语法解析器
 */
public class FuncOverExpr extends HelperExpr {
    FuncOverStatement funcOverStatement = new FuncOverStatement();

    public FuncOverExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        this(exprReader, () -> new FunctionExpr(exprReader, myFunctionFactory), myFunctionFactory);
    }

    public FuncOverExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
        super(exprReader, helper, myFunctionFactory);
        setExprTypes(ExprType.HELP);
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        funcOverStatement.setFunctionStatement(statement);
        setExprTypes(ExprType.OVER, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprOver(ExprInfo exprInfo) throws AntlrException {
        OverExpr overExpr = new OverExpr(exprReader, myFunctionFactory);
        funcOverStatement.setOverStatement(overExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        if (funcOverStatement.getOverStatement() == null) {
            return funcOverStatement.getFunctionStatement();
        } else {
            return funcOverStatement;
        }
    }
}
