package com.moxa.dream.test.antlr.invoker;

import com.moxa.dream.antlr.callback.Callback;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.test.antlr.AbstractSqlTest;
import com.moxa.dream.test.antlr.invoker.call.MyCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CallInvokerTest extends AbstractSqlTest {
    private List<InvokerFactory> invokerFactoryList = new ArrayList<>();

    public CallInvokerTest() {
        AntlrInvokerFactory injectInvokerFactory = new AntlrInvokerFactory();
        invokerFactoryList.add(injectInvokerFactory);
    }

    public static void main(String[] args) {
        CallInvokerTest invokerSqlTest = new CallInvokerTest();
        invokerSqlTest.testCallInvoker();

    }

    public void testCallInvoker() {

        PackageStatement packageStatement = createStatement("select * from dual where @call(callA,'a',true,11)", null);
        try {
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, Map.of(Callback.class, new MyCallBack())));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }
}
