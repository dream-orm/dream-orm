package com.dream.system.compile;

import com.dream.antlr.expr.PackageExpr;
import com.dream.antlr.expr.SqlExpr;
import com.dream.antlr.factory.MyExprFactory;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.PackageStatement;
import com.dream.system.antlr.factory.DefaultMyExprFactory;

public class DefaultCompileFactory implements CompileFactory {
    private final MyExprFactory myExprFactory = new DefaultMyExprFactory();
    private MyFunctionFactory myFunctionFactory;

    public void setMyFunctionFactory(MyFunctionFactory myFunctionFactory) {
        this.myFunctionFactory = myFunctionFactory;
    }

    @Override
    public PackageStatement compile(String sql) throws Exception {
        ExprReader exprReader = new ExprReader(sql, myFunctionFactory, myExprFactory);
        SqlExpr sqlExpr = new PackageExpr(exprReader);
        return (PackageStatement) sqlExpr.expr();
    }
}
