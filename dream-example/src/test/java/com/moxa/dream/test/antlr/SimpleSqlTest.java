package com.moxa.dream.test.antlr;

/**
 * 测试一般函数的使用
 */
public class SimpleSqlTest extends AbstractSqlTest {

    public static void main(String[] args) {
        SimpleSqlTest simpleSqlTest = new SimpleSqlTest();
//        simpleSqlTest.testStr();
//        simpleSqlTest.testNumber();
//        simpleSqlTest.testDate();
//        simpleSqlTest.testOther();
//        simpleSqlTest.testCaseWhen();
        simpleSqlTest.testLimit();
        simpleSqlTest.testOffset();
        simpleSqlTest.testCompare();
        simpleSqlTest.testSelectDot();
        simpleSqlTest.testSelectFunction();
        simpleSqlTest.testInsert();
    }


    public void testStr() {
        testSqlForMany("SELECT ascii(s),len(s),CHAR_LENGTH(s),CHARACTER_LENGTH(s),concat(s1,s2),concat(s1,s2,s3),concat_ws(s,s1,s2,s3),repeat(s,n),reverse(s),replace(s,from_s,to_s)" +
                ",instr(s,sub),locate(sub,s),locate(sub,s,pos),lower(s),LCASE(s),lpad(s1,num,s2),rpad(s1,num,s2),left(s,num),right(s,num)" +
                ",SPACE(num),substr(str,num),substr(str,num1,num2),UPPER(s),trim(s),coalesce(s1,s2,s3)", null, null);

    }

    public void testNumber() {
        testSqlForMany("SELECT abs(s),acos(s),asin(s),atan(s),avg(s),avg(distinct s),avg(all s),ceil(s),ceiling(s)" +
                ",count(s),count(distinct s),count(all s),exp(s),floor(s),ln(s),log(s),log(s1,s2)" +
                ",log2(s),log10(s),max(s),min(s),mod(s1,s2),PI(),pow(s1,s2),power(s1,s2),rand()" +
                ",round(s),round(s1,s2),sign(s),sin(s),sqrt(s),sum(s),sum(distinct s),sum(all s),tan(s),truncate(s1,s2)", null, null);
    }

    public void testDate() {
        testSqlForMany("SELECT curdate(),datediff(s1,s2),date_sub(s1,s2 day),date_add(s1,s2 year),date_add(s1,s2 quarter),date_add(s1,s2 month)" +
                ",date_add(s1,s2 week),date_add(s1,s2 day),date_add(s1,s2 hour),date_add(s1,s2 minute),date_add(s1,s2 second)" +
                ",date_add(s1,interval s2 year),date_add(s1,interval s2 quarter),date_add(s1,interval s2 month)" +
                ",date_add(s1,interval s2 week),date_add(s1,interval s2 day),date_add(s1,interval s2 hour),date_add(s1,interval s2 minute),date_add(s1,interval s2 second)" +
                ",DAYOFYEAR(s),year(s),month(s),now(),day(s),DAYOFWEEK(s),hour(s),last_day(s),minute(s),quarter(s),second(s),weekofyear(s)" +
                ",date_format(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j'),str_to_date(s,'%Y-%y-%m-%d-%e-%H-%k-%h-%l-%i-%s-%S-%j')", null, null);

    }

    public void testOther() {
//        oracle不支持，执行报错
//        testSqlForMany("select convert(s,date),convert(s,time),convert(s,datetime),cast(s as date),cast(s as time),cast(s as datetime)",null,null);
        testSqlForMany("SELECT row_number() over(),row_number() over(partition by p),row_number() over(partition by p order by o),row_number() over(order by o),convert(s,signed),convert(s,signed int),convert(s,signed integer),convert(s,float),convert(s,char),convert(s,decimal),convert(s,decimal(s)),convert(s,decimal(s,b)),cast(s as signed),cast(s as signed int),cast(s as signed integer),cast(s as float),cast(s as decimal)," +
                "cast(s as decimal(s)),cast(s as decimal(s,b)),to_number(s),to_char(s),to_char(s,'yyyy-MM-dd ddd HH:HH12:HH24:Mi:SS'),to_date(s,'yyyy-MM-dd ddd HH:HH12:HH24:Mi:SS')" +
                ",isnull(a),ifnull(s1,s2),IF(s1,s2,s3),nullif(s1,s2)", null, null);
    }

    public void testCaseWhen() {
        testSqlForMany("SELECT CASE A WHEN 'A' THEN 1 WHEN 2 THEN 2 ELSE 3 END,CASE WHEN B>0 THEN 1 WHEN B<0 THEN 2 ELSE 0 END FROM DUAL", null, null);
    }

    public void testLimit() {
        testSqlForMany("SELECT 1 FROM DUAL ORDER BY ID LIMIT 10,20", null, null);
        testSqlForMany("SELECT 1 FROM DUAL ORDER BY ID LIMIT 10", null, null);
    }

    public void testOffset() {
        testSqlForMany("SELECT 1 FROM DUAL  OFFSET 10 LIMIT 20", null, null);
        testSqlForMany("SELECT 1 FROM DUAL LIMIT 20 OFFSET 10", null, null);
    }

    public void testCompare() {
        testSqlForMany("SELECT 1&2,1^2,1|2,1<<2,1>>2,1+2+(1-2-1*2-1)/2 FROM DUAL where 1<>1  and '1' is not null and 1>=1 or 1<1 and 1>1 or 1=1 and 1 between 1 and 2 and 1 in(a) and 2 in(select a) and not exists(select a)", null, null);
    }

    public void testSelectDot() {
        testSqlForMany("SELECT a.b.c,c.d", null, null);
    }

    public void testSelectFunction() {
        testSqlForMany("SELECT year,max,year.max", null, null);
    }

    public void testInsert() {
        testSqlForMany("insert into dual(id,name)values(id,name),(?,?)", null, null);
    }
}
