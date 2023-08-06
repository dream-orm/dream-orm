package com.dream.test;

import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.PackageExpr;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSqlTest {
    static List<ToSQL> toSQLList = Arrays.asList(new ToMYSQL(), new ToPGSQL(), new ToMSSQL(), new ToORACLE(), new ToDM());
    private List<InvokerFactory> invokerFactoryList = new ArrayList<>();

    public AbstractSqlTest() {
    }

    protected PackageStatement createStatement(String sql, MyFunctionFactory myFunctionFactory) throws AntlrException {
        return (PackageStatement) new PackageExpr(new ExprReader(sql, myFunctionFactory)).expr();
    }

    protected void testSqlForMany(String sql, MyFunctionFactory myFunctionFactory, List<InvokerFactory> invokerFactoryList) {
        System.out.println();
        for (ToSQL toSQL : toSQLList) {
            try {
                System.out.println(toSQL.getName() + "->" + toSQL.toStr(createStatement(sql, myFunctionFactory), null, null));
            } catch (AntlrException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
