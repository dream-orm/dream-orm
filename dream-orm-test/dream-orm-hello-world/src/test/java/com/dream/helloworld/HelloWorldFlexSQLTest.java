package com.dream.helloworld;

import com.dream.antlr.smt.Statement;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.CaseColumnDef;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.Query;
import com.dream.helloworld.debug.FlexDebug;
import com.dream.helloworld.table.table.AccountTableDef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static com.dream.flex.def.FunctionDef.*;
import static com.dream.helloworld.table.table.AccountTableDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexSQLTest {
    FlexDebug flexDebug = new FlexDebug();

    /**
     * 测试select多个字段
     */
    @Test
    public void testSelect() {
        Query query = select(account.id, account.name);
        SqlInfo sqlInfo = flexDebug.toSQL(query);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试select数组
     */
    @Test
    public void testSelectArray() {
        Query query = select(account.accountView);
        SqlInfo sqlInfo = flexDebug.toSQL(query);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试字段别名
     */
    @Test
    public void testSelectColumnAs() {
        ColumnDef columnDef = account.name.as("uname");
        Statement statement = columnDef.getStatement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试case
     */
    @Test
    public void testCase() {
        CaseColumnDef columnDef = case_(account.age).when(11).then(11).when("11").then("字符串11").else_("默认值").end();
        Statement statement = columnDef.getStatement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试case条件
     */
    @Test
    public void testCaseCondition() {
        CaseColumnDef columnDef = case_().when(account.age.eq(11)).then(11).when(account.age.eq("11")).then("字符串11").else_("默认值").end();
        Statement statement = columnDef.getStatement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试from语句
     */
    @Test
    public void testFrom() {
        Query query = select(account.id).from(account);
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试关联查询
     */
    @Test
    public void testFromJoin() {
        AccountTableDef account2 = new AccountTableDef("account2");
        Query query = select(account.id).from(account).leftJoin(account2).on(account2.id.eq(account.id));
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
    /**
     * 测试Where
     */
    @Test
    public void testWhere() {
        Query query = select(account.id).from(account).where(account.name.like("a"));
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
    /**
     * 测试Where动态条件
     */
    @Test
    public void testWhereDynamic1() {
        Query query = select(account.id).from(account).where(account.name.like("a", Objects::isNull));
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
    /**
     * 测试Where动态条件
     */
    @Test
    public void testWhereDynamic2() {
        Query query = select(account.id).from(account).where(account.name.like("a").when(false));
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试group
     */
    @Test
    public void testGroup() {
        Query query = select(count(account.id)).from(account).groupBy(account.age);
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
    /**
     * 测试having
     */
    @Test
    public void testHaving() {
        Query query = select(count(account.id)).from(account).groupBy(account.age).having(account.name.likeLeft("a"));
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
    /**
     * 测试orderBy
     */
    @Test
    public void testOrderBy() {
        Query query = select(count(account.id)).from(account).orderBy(account.age.desc(),account.name.asc());
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试Limit
     */
    @Test
    public void testLimit() {
        Query query = select(count(account.id)).from(account).limit(5,10);
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }


    /**
     * 测试Offset
     */
    @Test
    public void testOffset() {
        Query query = select(count(account.id)).from(account).offset(10,5);
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试Union
     */
    @Test
    public void testUnion() {
        Query query = select(count(account.id)).from(account).union(select(count(account.id)).from(account));
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试UnionAll
     */
    @Test
    public void testUnionAll() {
        Query query = select(count(account.id)).from(account).unionAll(select(count(account.id)).from(account));
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试forUpdate
     */
    @Test
    public void testforUpdate() {
        Query query = select(count(account.id)).from(account).forUpdate();
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
    /**
     * 测试forUpdateNoWait
     */
    @Test
    public void forUpdateNoWait() {
        Query query = select(count(account.id)).from(account).forUpdateNoWait();
        Statement statement = query.statement();
        SqlInfo sqlInfo = flexDebug.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
}
