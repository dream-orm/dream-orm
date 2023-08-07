package com.dream.test;


import org.junit.Test;

public class SimpleSqlTest extends AbstractSqlTest {

    @Test
    public void testStr() {
        testSqlForMany("SELECT ascii(s),len(s),length(s),CHAR_LENGTH(s),CHARACTER_LENGTH(s),concat(s1,s2),concat(s1,s2,s3),group_concat(a,b),group_concat(distinct a,b),group_concat(distinct a,b order by a separator ','),concat_ws(s,s1,s2,s3),repeat(s,n),reverse(s),replace(s,from_s,to_s)" +
                ",instr(s,sub),locate(sub,s),locate(sub,s,pos),lower(s),LCASE(s),lpad(s1,num,s2),rpad(s1,num,s2),left(s,num),right(s,num)" +
                ",SPACE(num),substr(str,num),substr(str,num1,num2),UPPER(s),trim(s),coalesce(s1,s2,s3)", null, null);

    }

    @Test
    public void testAlias() {
        testSqlForMany("SELECT a.'a', a.`b` 'b',c `c`,e as e,f as 'f',g as 'g'", null, null);
    }

    @Test
    public void testJoin() {
        testSqlForMany("SELECT * from a left join a on 1=1 right join a on 1=1 inner join a  on 1=1", null, null);
    }

    @Test
    public void testNumber() {
        testSqlForMany("SELECT abs(s),acos(s),asin(s),atan(s),avg(s),avg(distinct s),avg(all s),ceil(s),ceiling(s)" +
                ",count(s),count(distinct s),count(all s),exp(s),floor(s),ln(s),log(s),log(s1,s2)" +
                ",log2(s),log10(s),max(s),min(s),mod(s1,s2),PI(),pow(s1,s2),power(s1,s2),rand()" +
                ",round(s),round(s1,s2),sign(s),sin(s),sqrt(s),sum(s),sum(distinct s),sum(all s),tan(s),truncate(s1,s2)", null, null);
    }

    @Test
    public void testDate() {
        testSqlForMany("SELECT curdate(),datediff(s1,s2),date_sub(s1,s2 day),date_add(s1,s2 year),date_add(s1,s2 quarter),date_add(s1,s2 month)" +
                ",date_add(s1,s2 week),date_add(s1,s2 day),date_add(s1,s2 hour),date_add(s1,s2 minute),date_add(s1,s2 second)" +
                ",date_add(s1,interval s2 year),date_add(s1,interval s2 quarter),date_add(s1,interval s2 month)" +
                ",date_add(s1,interval s2 week),date_add(s1,interval s2 day),date_add(s1,interval s2 hour),date_add(s1,interval s2 minute),date_add(s1,interval s2 second)" +
                ",DAYOFYEAR(s),year(s),month(s),now(),day(s),DAYOFWEEK(s),hour(s),last_day(s),minute(s),quarter(s),second(s),weekofyear(s)" +
                ",date_format(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j'),str_to_date(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j')", null, null);

    }

    @Test
    public void testOther() {
        testSqlForMany("SELECT convert(s,date),convert(s,time),convert(s,datetime),cast(s as date),cast(s as time),cast(s as datetime),row_number() over(),row_number() over(partition by p),row_number() over(partition by p order by o),row_number() over(order by o),convert(s,signed),convert(s,signed int),convert(s,signed integer),convert(s,float),convert(s,char),convert(s,decimal),convert(s,decimal(s)),convert(s,decimal(s,b)),cast(s as signed),cast(s as signed int),cast(s as signed integer),cast(s as float),cast(s as decimal)," +
                "cast(s as decimal(s)),cast(s as decimal(s,b)),isnull(a),ifnull(s1,s2),IF(s1,s2,s3),nullif(s1,s2)", null, null);
    }

    @Test
    public void testCaseWhen() {
        testSqlForMany("SELECT CASE A WHEN 'A' THEN 1 WHEN 2 THEN 2 ELSE 3 END,CASE WHEN B>0 THEN 1 WHEN B<0 THEN 2 ELSE 0 END FROM DUAL", null, null);
    }

    @Test
    public void testLimit() {
        testSqlForMany("SELECT 1 FROM DUAL ORDER BY ID LIMIT 3000,6000", null, null);
        testSqlForMany("SELECT 1 FROM DUAL ORDER BY ID LIMIT 10", null, null);
    }

    @Test
    public void testOffset() {
        testSqlForMany("SELECT 1 FROM DUAL LIMIT 20 OFFSET 10", null, null);
    }

    @Test
    public void testCompare() {
        testSqlForMany("SELECT 1/2,1&2,1^2,1|2,1<<2,1>>2,1+2+(1-2-1*2-1)/2 FROM DUAL where 1!=1 and 1<>1  and '1' is not null and 1>=1 or 1<1 and 1>1 or 1=1 and 1 between 1 and 2 and 1 in(a) and 2 in(select a) and not exists(select a)", null, null);
    }

    @Test
    public void testSelectDot() {
        testSqlForMany("SELECT a.b.c,c.d from dual where s is null and f is not  null", null, null);
    }

    @Test
    public void testForUpdate() {
        testSqlForMany("SELECT distinct a from dual for update", null, null);
    }

    @Test
    public void testForUpdateNoWait() {
        testSqlForMany("SELECT distinct a from dual for update nowait", null, null);
    }

    @Test
    public void testSelectFunction() {
        testSqlForMany("SELECT to_char(11),to_number('11'),to_char(now(),'yyyy-mm-dd hh24:mi:ss'),to_number('111','000'),to_date('2020-12-12','yyyy-mm-dd'),to_date('2020-12-12 12:12:12','yyyy-mm-dd hh24:mi:ss'),`year`.`max`", null, null);
    }

    @Test
    public void testInsert() {
        testSqlForMany("insert into dual(id,name)values(id,name)", null, null);
    }

    @Test
    public void testTruncate() {
        testSqlForMany("truncate table `aaa`", null, null);
    }

    @Test
    public void testDrop() {
        testSqlForMany("drop table `aaa`", null, null);
    }
}
