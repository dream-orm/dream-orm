package com.moxa.dream.system.compile;

import com.moxa.dream.antlr.expr.PackageExpr;
import com.moxa.dream.antlr.expr.SqlExpr;
import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.PackageStatement;

public class DefaultCompileFactory implements CompileFactory {
    private MyFunctionFactory myFunctionFactory;

    public void setMyFunctionFactory(MyFunctionFactory myFunctionFactory) {
        this.myFunctionFactory = myFunctionFactory;
    }

    @Override
    public PackageStatement compile(String sql) throws Exception {
        ExprReader exprReader = new ExprReader(sql, myFunctionFactory);
        SqlExpr sqlExpr = new PackageExpr(exprReader);
        PackageStatement statement = (PackageStatement) sqlExpr.expr();
        return statement;
    }
}
