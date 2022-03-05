package com.moxa.dream.test.antlr.invoker;

import com.moxa.dream.antlr.bind.ResultInfo;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.$Invoker;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.test.antlr.AbstractSqlTest;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.*;

public class ParamInvokerTest extends AbstractSqlTest {
    private List<InvokerFactory> invokerFactoryList = new ArrayList<>();

    public ParamInvokerTest() {
        AntlrInvokerFactory injectInvokerFactory = new AntlrInvokerFactory();
        invokerFactoryList.add(injectInvokerFactory);
    }


    public static void main(String[] args) {
        ParamInvokerTest paramInvokerTest = new ParamInvokerTest();
        paramInvokerTest.testRepInvoker();
        paramInvokerTest.test$Invoker();
        paramInvokerTest.testNonInvoker();
        paramInvokerTest.testSimpleForEachInvoker();
        paramInvokerTest.testMapForEachInvoker();
    }

    /**
     * 测试@rep()函数，将参数原封不动带入sql类型mybatis ${}
     */
    public void testRepInvoker() {
        PackageStatement packageStatement = createStatement("select 1 from dual where a=@rep(a) and b=@rep(b) or c=@rep(c)", null);
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("a", null);
            paramMap.put("b", "'test_b'");
            paramMap.put("c", "'test_c'");
            System.out.println(new ToMYSQL().toResult(packageStatement, invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap))));
        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将参数使用占位符表示，类似mybatis #{}
     */
    public void test$Invoker() {
        PackageStatement packageStatement = createStatement("select 1 from dual where a=@$(a) and b=@$(b) or c=@$(c)", null);
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("a", null);
            paramMap.put("b", "'test_b'");
            paramMap.put("c", "'test_c'");
            ResultInfo resultInfo;
            System.out.println((resultInfo = new ToMYSQL().toResult(packageStatement, invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap)))) + "\t\t参数：" + (resultInfo.getSqlInvoker($Invoker.class)).getParamInfoList());

        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试如果为空则去除，类似mybatis动态sql if语句，这里很有意思
     */
    public void testNonInvoker() {
        PackageStatement packageStatement = createStatement("select year from dual where @non(date between @$(startDate) and @$(endDate))", null);
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("endDate", "2020-12-12");
            ResultInfo resultInfo;
            System.out.println((resultInfo = new ToMYSQL().toResult(packageStatement, invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap)))) + "\t\t参数：" + (resultInfo.getSqlInvoker($Invoker.class)).getParamInfoList());

        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试foreach用法，单参数执行速度远远高于双参数foreach
     */
    public void testSimpleForEachInvoker() {
        PackageStatement packageStatement = createStatement("select 1 from dual where a in(@foreach(a))", null);
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("a", Arrays.asList("a", "b", "c", "d", "e"));
            ResultInfo resultInfo;
            System.out.println((resultInfo = new ToMYSQL().toResult(packageStatement, invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap)))) + "\t\t参数：" + (resultInfo.getSqlInvoker($Invoker.class)).getParamInfoList());

        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 性能慢，能用单参数foreach就不要用双参数foreach
     */
    public void testMapForEachInvoker() {
        PackageStatement packageStatement = createStatement("select 1 from dual where a in(@foreach(a,name))", null);
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("a", Arrays.asList(Map.of("name", "a"), Map.of("name", "a"), Map.of("name", "b"), Map.of("name", "c"), Map.of("name", "d"), Map.of("name", "e")));
//            paramMap.put("a",new Object[]{Map.of("name","a"),Map.of("name","a"),Map.of("name","b"),Map.of("name","c"),Map.of("name","d"),Map.of("name","e")});
            ResultInfo resultInfo;
            System.out.println((resultInfo = new ToMYSQL().toResult(packageStatement, invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap)))) + "\t\t参数：" + (resultInfo.getSqlInvoker($Invoker.class)).getParamInfoList());

        } catch (InvokerException e) {
            throw new RuntimeException(e);
        }
    }
}
