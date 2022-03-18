package com.moxa.dream.test.antlr;

import com.moxa.dream.antlr.bind.ResultInfo;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.$Invoker;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToMSSQL;
import com.moxa.dream.antlr.sql.ToORACLE;
import com.moxa.dream.antlr.sql.ToPGSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.*;


public class SpeedTest extends AbstractSqlTest {
    private List<InvokerFactory> invokerFactoryList = new ArrayList<>();

    public SpeedTest() {
        invokerFactoryList.add(new AntlrInvokerFactory());
    }

    public static void main(String[] args) {
        SpeedTest speedTest = new SpeedTest();
        speedTest.testSimpleFunctionSpeed();
        speedTest.testInvokerSpeed();
        speedTest.testParamInvokerSpeed();
    }

    public void testSimpleFunctionSpeed() {
        PackageStatement statement = createStatement("SELECT ascii(s),len(s),CHAR_LENGTH(s),concat(s1,s2),concat(s1,s2,s3),concat_ws(s,s1,s2,s3),repeat(s,n),reverse(s),replace(s,from_s,to_s)" +
                ",instr(s,sub),locate(sub,s),locate(sub,s,pos),lower(s),LCASE(s),lpad(s1,num,s2),rpad(s1,num,s2),left(s,num),right(s,num)" +
                ",SPACE(num),substr(str,num),substr(str,num1,num2),UPPER(s),trim(s),abs(s),acos(s),asin(s),atan(s),avg(s),avg(distinct s),avg(all s),ceil(s),ceiling(s)" +
                ",count(s),count(distinct s),count(all s),exp(s),floor(s),ln(s),log(s),log(s1,s2)" +
                ",log2(s),log10(s),max(s),min(s),mod(s1,s2),PI(),pow(s1,s2),power(s1,s2),rand()" +
                ",round(s),round(s1,s2),sign(s),sin(s),sqrt(s),sum(s),sum(distinct s),sum(all s),tan(s),truncate(s1,s2),curdate(),datediff(s1,s2),date_add(s1,s2 year),date_add(s1,s2 quarter),date_add(s1,s2 month)" +
                ",date_add(s1,s2 week),date_add(s1,s2 day),date_add(s1,s2 hour),date_add(s1,s2 minute),date_add(s1,s2 second)" +
                ",date_add(s1,interval s2 year),date_add(s1,interval s2 quarter),date_add(s1,interval s2 month)" +
                ",date_add(s1,interval s2 week),date_add(s1,interval s2 day),date_add(s1,interval s2 hour),date_add(s1,interval s2 minute),date_add(s1,interval s2 second)" +
                ",DAYOFYEAR(s),year(s),month(s),now(),day(s),DAYOFWEEK(s),hour(s),last_day(s),minute(s),quarter(s),second(s),weekofyear(s)" +
                ",date_format(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j'),str_to_date(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j'),to_char(s,'yyyy-MM-dd ddd HH:HH12:HH24:Mi:SS'),to_date(s,'yyyy-MM-dd ddd HH:HH12:HH24:Mi:SS')" +
                ",ifnull(s1,s2),IF(s1,s2,s3),nullif(s1,s2)", null);
        testSpeed(100000000, statement, new ToMSSQL());
    }

    public void testInvokerSpeed() {
        PackageStatement packageStatement = createStatement("@scan(`update dual set 1=1`)", null);
        ToSQL toSQL = new ToORACLE();
        String sql = "";
        int count = 100000000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            try {
                sql = toSQL.toResult(packageStatement, invokerFactoryList, null).getSql();
            } catch (InvokerException e) {
                throw new RuntimeException(e);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(toSQL.getName() + "\t执行次数：" + count + "\t执行时间：" + (end - start) + "\t\t" + sql);
    }

    //使用invoker函数，需要每次都要根据参数解析，不会进行缓存，因此会非常耗时
    public void testParamInvokerSpeed() {
        Map<String, Object> paramMap = new HashMap<>();
        //比较两种foreach速度，单参数做了优化，不进行反射，性能会提高很多
        PackageStatement packageStatement = createStatement("select a1,a2,a3,a4,a5 from dual where a in(@foreach(a,@$(name)))", null);
        paramMap.put("a", Arrays.asList(Map.of("name", "a"), Map.of("name", "a"), Map.of("name", "b"), Map.of("name", "c"), Map.of("name", "d"), Map.of("name", "e")));
        int count = 1000000;
        System.out.println();
        System.out.println("双参数foreach：");
        testSpeed(count, packageStatement, new ToORACLE(), invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap)));
        packageStatement = createStatement("select a1,a2,a3,a4,a5 from dual where a in(@foreach(a))", null);
        paramMap.put("a", Arrays.asList("a", "b", "c", "d", "e"));
        System.out.println("单参数foreach：");
        testSpeed(count, packageStatement, new ToORACLE(), invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap)));
        System.out.println("@non性能测试：");
        packageStatement = createStatement("select 1 from dual where @non(a=@$(a) or (a=null) and a=@rep(a)  and b=@$(b) or c=@$(c))", null);
        paramMap = new HashMap<>();
        paramMap.put("a", null);
        paramMap.put("b", "'test_b'");
        paramMap.put("c", "'test_c'");
        testSpeed(count, packageStatement, new ToPGSQL(), invokerFactoryList, Map.of(ObjectWrapper.class, ObjectWrapper.wrapper(paramMap)));

    }

    public void testSpeed(int count, PackageStatement statement, ToSQL toSQL) {
        long start = System.currentTimeMillis();
        String sql = "";
        for (int i = 0; i < count; i++) {
            try {
                sql = toSQL.toResult(statement, null, null).getSql();
            } catch (InvokerException e) {
                throw new RuntimeException(e);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(toSQL.getName() + "\t执行次数：" + count + "\t执行时间：" + (end - start) + "\t\t" + sql);
    }

    public void testSpeed(int count, PackageStatement statement, ToSQL toSQL, List<InvokerFactory> invokerFactoryList, Map<Class, Object> paramList) {
        long start = System.currentTimeMillis();
        String sql = "";
        ResultInfo resultInfo = null;
        for (int i = 0; i < count; i++) {
            try {
                sql = (resultInfo = toSQL.toResult(statement, invokerFactoryList, paramList)).toString();
            } catch (InvokerException e) {
                throw new RuntimeException(e);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(toSQL.getName() + "\t执行次数：" + count + "\t执行时间：" + (end - start) + "\t\t" + sql + "\t\t参数：" + (resultInfo.getSqlInvoker($Invoker.class)).getParamInfoList());
    }
}
