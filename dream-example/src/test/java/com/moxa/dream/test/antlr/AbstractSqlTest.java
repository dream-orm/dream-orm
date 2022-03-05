package com.moxa.dream.test.antlr;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.PackageExpr;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSqlTest {
    static List<ToSQL> toSQLList = Arrays.asList(new ToDREAM(), new ToMYSQL(), new ToPGSQL(), new ToMSSQL(), new ToORACLE());
    private List<InvokerFactory> invokerFactoryList = new ArrayList<>();

    public AbstractSqlTest() {
        AntlrInvokerFactory injectInvokerFactory = new AntlrInvokerFactory();
        invokerFactoryList.add(injectInvokerFactory);
    }

    protected PackageStatement createStatement(String sql, MyFunctionFactory myFunctionFactory) {
        return (PackageStatement) new PackageExpr(new ExprReader(sql, myFunctionFactory)).expr();
    }

    protected void testSqlForMany(String sql, MyFunctionFactory myFunctionFactory, List<InvokerFactory> invokerFactoryList) {
        System.out.println();
        for (ToSQL toSQL : toSQLList) {
            try {
                System.out.println(toSQL.getName() + "->" + toSQL.toResult(createStatement(sql, myFunctionFactory), invokerFactoryList, null).getSql());
            } catch (InvokerException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
