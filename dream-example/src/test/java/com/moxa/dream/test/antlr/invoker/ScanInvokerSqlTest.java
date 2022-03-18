package com.moxa.dream.test.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToDREAM;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.test.antlr.AbstractSqlTest;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScanInvokerSqlTest extends AbstractSqlTest {
    private List<InvokerFactory> invokerFactoryList = new ArrayList<>();

    public ScanInvokerSqlTest() {
        AntlrInvokerFactory injectInvokerFactory = new AntlrInvokerFactory();
        invokerFactoryList.add(injectInvokerFactory);
    }

    public static void main(String[] args) {
        ScanInvokerSqlTest invokerSqlTest = new ScanInvokerSqlTest();
        invokerSqlTest.testSelectInvoker();
        invokerSqlTest.testInsertInvoker();
        invokerSqlTest.testUpdateInvoker();
        invokerSqlTest.testDeleteInvoker();
    }

    public void testSelectInvoker() {
        PackageStatement packageStatement = createStatement("@scan(`select dual.a as a0,dual.b,c from dual where a=@$(a) and c between @$(c1) and @$(c2)`)", null);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            System.out.println(new ToDREAM().toResult(packageStatement, invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(objectMap))).getSql() + "\n" + packageStatement.getValue(ScanInvoker.ScanInfo.class));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testInsertInvoker() {
//        DMLStatement dmlStatement = createStatement("@scan(`insert into dual select * from dual`)",null);
        PackageStatement packageStatement = createStatement("@scan(`insert into dual(a,b,c) values(@$(a),@$(b),@$(c))`)", null);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(objectMap))).getSql() + "\n" + packageStatement.getValue(ScanInvoker.ScanInfo.class));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testUpdateInvoker() {
        PackageStatement packageStatement = createStatement("@scan(`update dual set 1=1`)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null).getSql() + "\n" + packageStatement.getValue(ScanInvoker.ScanInfo.class));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    public void testDeleteInvoker() {
        PackageStatement packageStatement = createStatement("@scan(`delete from dual`)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, null).getSql() + "\n" + packageStatement.getValue(ScanInvoker.ScanInfo.class));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

}
