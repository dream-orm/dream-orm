package com.dream.helloworld.h2;


import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.PackageExpr;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.sql.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class HelloWorldAntlrTest {
    static List<ToSQL> toSQLList = Arrays.asList(new ToMySQL(), new ToPostgreSQL(), new ToSQLServer(), new ToSQLServer08(), new ToOracle(), new ToOracle11(), new ToDM(), new ToClickHouse());

    @Test
    public void testStr() {
        testSqlForMany("SELECT ascii(s),strcmp(a,b),length(s),CHAR_LENGTH(s),CHARACTER_LENGTH(s),concat(s1,s2),concat(s1,s2,s3),group_concat(a,b),group_concat(distinct a,b),group_concat(distinct a,b order by a separator ','),concat_ws(s,s1,s2,s3),repeat(s,n),reverse(s),replace(s,from_s,to_s)" +
                ",instr(s,sub),locate(sub,s),locate(sub,s,pos),lower(s),LCASE(s),UCASE(s),lpad(s1,num,s2),rpad(s1,num,s2),left(s,num),right(s,num)" +
                ",SPACE(num),substr(str,num),substr(str,num1,num2),UPPER(s),trim(s),coalesce(s1,s2,s3),find_in_set(strList,str)");
    }

    @Test
    public void testAlias() {
        testSqlForMany("SELECT a.'a', a.`b` 'b',c `c`,e as e,f as 'f',g as 'g'");
    }

    @Test
    public void testChina() {
        testSqlForMany("SELECT \n" +
                "  DATE_FORMAT(DATART_VTABLE.NO1, '%Y-%m-%d') AS NO1（按日） \n" +
                "FROM\n" +
                "  (SELECT \n" +
                "    id,\n" +
                "    rel_name,\n" +
                "    NAME,\n" +
                "    rel_id,\n" +
                "    create_time,\n" +
                "    NOW() AS no1,\n" +
                "    DATE_ADD(create_time, INTERVAL 2 YEAR) \n" +
                "  FROM\n" +
                "    DASHBOARD) AS DATART_VTABLE \n" +
                "WHERE CREATE_TIME >= STR_TO_DATE(\n" +
                "    '2024-02-01 00:00:00',\n" +
                "    '%Y-%m-%d %H:%i:%s'\n" +
                "  ) \n" +
                "  AND CREATE_TIME < STR_TO_DATE(\n" +
                "    '2024-07-01 00:00:00',\n" +
                "    '%Y-%m-%d %H:%i:%s'\n" +
                "  ) \n" +
                "LIMIT 100 OFFSET 0 ");
    }

    @Test
    public void testAs() {
        testSqlForMany("SELECT d.name as ddd FROM (select * from table1) as d");
    }

    @Test
    public void testInterval() {
        testSqlForMany("select CURDATE()+INTERVAL 1 YEAR-INTERVAL 1 QUARTER+INTERVAL 1 second");
    }

    @Test
    public void testJoin() {
        testSqlForMany("SELECT * from a left join a on 1=1 right join b on 1=1 inner join c  on 1=1 full join d on 1=1");
    }

    @Test
    public void testNumber() {
        testSqlForMany("SELECT 123,abs(s),acos(s),asin(s),atan(s),avg(s),avg(distinct s),avg(all s),ceil(s),ceiling(s)" +
                ",count(s),count(distinct s),count(all s),exp(s),floor(s),ln(s),log(s),log(s1,s2)" +
                ",log2(s),log10(s),max(s),min(s),mod(s1,s2),PI(),pow(s1,s2),power(s1,s2),rand()" +
                ",round(s),round(s1,s2),sign(s),sin(s),sqrt(s),sum(s),sum(distinct s),sum(all s),tan(s),truncate(s1,s2)");
    }

    @Test
    public void testDate() {
        testSqlForMany("SELECT CURTIME(),CURRENT_TIME(),curdate(),datediff(s1,s2),date_sub(s1,interval 2 year)" +
                ",date_add(s1,interval 2 year),date_add(s1,interval 4 quarter),date_add(s1,interval 4 month)" +
                ",date_add(s1,interval 3 week),date_add(s1,interval 4 day),date_add(s1,interval 4 hour),date_add(s1,interval 4 minute),date_add(s1,interval 4 second)" +
                ",DAYOFYEAR(s),year(s),month(s),now(),day(s),DAYOFWEEK(s),hour(s),last_day(s),minute(s),quarter(s),second(s),weekofyear(s)" +
                ",date_format(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j'),str_to_date(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j')");
        testSqlForMany("select date_format(now(),'%T-%W-%a-%M-%M-%b-%v')");
        testSqlForMany("select EXTRACT(year from now()),EXTRACT(QUARTER from now()),EXTRACT(month from now()),EXTRACT(week from now()),EXTRACT(day from now()),EXTRACT(hour from now()),EXTRACT(minute from now()),EXTRACT(second from now())");
    }

    @Test
    public void testOther() {
        testSqlForMany("SELECT cast(s as double),cast(s as decimal(s1,b)),convert(s,decimal(s,b)),convert(s,date),convert(s,time),convert(s,datetime),cast(s as date),cast(s as time),cast(s as datetime),row_number() over(),row_number() over(partition by p),row_number() over(partition by p order by o),row_number() over(order by o),convert(s,signed),convert(s,signed int),convert(s,signed integer),convert(s,float),convert(s,double),convert(s,char),convert(s,decimal),convert(s,decimal(s)),convert(s,decimal(s,b)),cast(s as signed),cast(s as signed int),cast(s as signed integer),cast(s as float),cast(s as decimal)," +
                "cast(s as decimal(s)),cast(s as decimal(s,b)),isnull(a),ifnull(s1,s2),IF(s1,s2,s3),nullif(s1,s2)");
    }

    @Test
    public void testCaseWhen() {
        testSqlForMany("SELECT CASE A WHEN 'A' THEN 1 WHEN 2 THEN 2 ELSE 3 END,CASE WHEN B>0 THEN 1 WHEN B<0 THEN 2 ELSE 0 END FROM DUAL");
    }

    @Test
    public void testLimit() {
        testSqlForMany("SELECT * FROM customer_article_record order by custom_id  LIMIT 0,10");
        testSqlForMany("SELECT * FROM customer_article_record  LIMIT 10");
    }

    @Test
    public void testOffset() {
        testSqlForMany("SELECT * FROM DUAL LIMIT 20 OFFSET 10");
    }

    @Test
    public void testCompare() {
        testSqlForMany("SELECT 123.677 FROM DUAL where a is  null");
    }

    @Test
    public void testSelectDot() {
        testSqlForMany("SELECT a.b.c,c.d from dual where s is null and f is not  null");
    }

    @Test
    public void testForUpdate() {
        testSqlForMany("SELECT distinct a from dual for update");
    }

    @Test
    public void testForUpdateNoWait() {
        testSqlForMany("SELECT distinct a from dual for update nowait");
    }

    @Test
    public void testSelectFunction() {
        testSqlForMany("SELECT to_char(11),to_number('11'),to_char(now(),'yyyy-mm-dd hh24:mi:ss'),to_number('111','000'),to_date('2020-12-12','yyyy-mm-dd'),to_date('2020-12-12 12:12:12','yyyy-mm-dd hh24:mi:ss'),`year`.`max`");
    }

    @Test
    public void testInsert() {
        testSqlForMany("insert into dual(id,name)values(id,name)");
    }

    @Test
    public void testInsertMany() {
        testSqlForMany("insert into dual(id,name)values(id,name),(id,name)");
    }

    @Test
    public void testInsertIgnore() {
        testSqlForMany("insert ignore into dual(id,name)values(id,name)");
    }

    @Test
    public void testReplaceInto() {
        testSqlForMany("replace into dual(id,name)values(id,name)");
    }


    protected void testSqlForMany(String sql) {
        System.out.println();
        try {
            PackageStatement statement = createStatement(sql);
            for (ToSQL toSQL : toSQLList) {
                System.out.println(toSQL.getName() + "->" + toSQL.toStr(statement.clone(), null, null));
            }
        } catch (AntlrException e) {
            throw new RuntimeException(e);
        }
    }

    protected PackageStatement createStatement(String sql) throws AntlrException {
        return (PackageStatement) new PackageExpr(new ExprReader(sql)).expr();
    }
}
