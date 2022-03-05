package com.moxa.dream.test.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToMSSQL;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToORACLE;
import com.moxa.dream.antlr.sql.ToPGSQL;
import com.moxa.dream.test.antlr.AbstractSqlTest;

import java.util.ArrayList;
import java.util.List;


public class CrudInvokerSqlTest extends AbstractSqlTest {
    private List<InvokerFactory> invokerFactoryList = new ArrayList<>();

    public CrudInvokerSqlTest() {
        AntlrInvokerFactory injectInvokerFactory = new AntlrInvokerFactory();
        invokerFactoryList.add(injectInvokerFactory);
    }

    public static void main(String[] args) {
        CrudInvokerSqlTest invokerSqlTest = new CrudInvokerSqlTest();
        invokerSqlTest.testWhereInvoker();
        invokerSqlTest.testGroupInvoker();
        invokerSqlTest.testHavingInvoker();
        invokerSqlTest.testSortInvoker();
        invokerSqlTest.testAscInvoker();
        invokerSqlTest.testDescInvoker();
        invokerSqlTest.testLimitInvoker();
        invokerSqlTest.testOffsetInvoker();
    }

    public void testWhereInvoker() {
        PackageStatement packageStatement = createStatement("@where:fleet(`select 1 from dual`,1=1)", null);
//        DMLStatement dmlStatement = createStatement("@where:inject(`select 1 from dual`,1=1)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testGroupInvoker() {
        PackageStatement packageStatement = createStatement("@group(`select 1 from dual`,a,b,c)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testHavingInvoker() {
        PackageStatement packageStatement = createStatement("@having:fleet(@group(`select 1 from dual`,a,b,c),1=2)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testSortInvoker() {
        PackageStatement packageStatement = createStatement("@sort(`select 1 from dual`,a,b)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testAscInvoker() {
        PackageStatement packageStatement = createStatement("select 1 from dual order by @asc(a),@asc(b)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testDescInvoker() {
        PackageStatement packageStatement = createStatement("select 1 from dual order by @desc(a),@desc(b)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testLimitInvoker() {
//        DMLStatement dmlStatement = createStatement("@limit(`select 1 from dual order by a`,10,11)", null);
        try {
            System.out.println("注入sql:@limit(`select 1 from dual order by a`,10,11)");
            System.out.println("\nmysql:" + new ToMYSQL().toResult(createStatement("@limit(`select 1 from dual order by a`,10,11)", null), invokerFactoryList, null));
            System.out.println("\npgsql:" + new ToPGSQL().toResult(createStatement("@limit(`select 1 from dual order by a`,10,11)", null), invokerFactoryList, null));
            System.out.println("\nmssql:" + new ToMSSQL().toResult(createStatement("@limit(`select 1 from dual order by a`,10,11)", null), invokerFactoryList, null));
            System.out.println("\noracle:" + new ToORACLE().toResult(createStatement("@limit(`select 1 from dual order by a`,10,11)", null), invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testOffsetInvoker() {
        PackageStatement packageStatement = createStatement("@offset(`select 1 from dual order by a`,10,11)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

}
