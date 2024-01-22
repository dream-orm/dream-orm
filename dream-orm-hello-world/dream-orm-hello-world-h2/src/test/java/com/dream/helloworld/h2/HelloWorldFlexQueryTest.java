package com.dream.helloworld.h2;

import com.dream.antlr.smt.Statement;
import com.dream.drive.factory.DefaultFlexDialect;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.CaseColumnDef;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.dialect.FlexDialect;
import com.dream.helloworld.h2.def.AccountDef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static com.dream.flex.def.FunctionDef.*;
import static com.dream.helloworld.h2.def.AccountDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexQueryTest {
    FlexDialect flexDialect = new DefaultFlexDialect();

    /**
     * 测试select多个字段
     */
    @Test
    public void testSelect() {
        QueryDef queryDef = select(account.id, account.name);
        SqlInfo sqlInfo = flexDialect.toSQL(queryDef);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试select数组
     */
    @Test
    public void testSelectArray() {
        QueryDef queryDef = select(account.accountView);
        SqlInfo sqlInfo = flexDialect.toSQL(queryDef);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试字段别名
     */
    @Test
    public void testSelectColumnAs() {
        ColumnDef columnDef = account.name.as("uname");
        Statement statement = columnDef.getStatement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试case
     */
    @Test
    public void testCase() {
        CaseColumnDef columnDef = case_(account.age).when(11).then(11).when("11").then("字符串11").else_("默认值").end();
        Statement statement = columnDef.getStatement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试case条件
     */
    @Test
    public void testCaseCondition() {
        CaseColumnDef columnDef = case_().when(account.age.eq(11)).then(11).when(account.age.eq("11")).then("字符串11").else_("默认值").end();
        Statement statement = columnDef.getStatement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试from语句
     */
    @Test
    public void testFrom() {
        QueryDef queryDef = select(account.id).from(account);
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试关联查询
     */
    @Test
    public void testFromJoin() {
        AccountDef account2 = new AccountDef("account2");
        QueryDef queryDef = select(account.id).from(account.leftJoin(account2, account.id, account2.id));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试关联查询2
     */
    @Test
    public void testFromJoin2() {
        AccountDef account2 = new AccountDef("account2");
        QueryDef queryDef = select(account.id).from(account.leftJoin(account2).on( account.id.eq(account2.id)));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试Where
     */
    @Test
    public void testWhere() {
        QueryDef queryDef = select(account.id).from(account).where(account.name.like("a"));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试Where动态条件
     */
    @Test
    public void testWhereDynamic1() {
        QueryDef queryDef = select(account.id).from(account).where(account.name.like("a", Objects::isNull));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试Where动态条件
     */
    @Test
    public void testWhereDynamic2() {
        QueryDef queryDef = select(account.id).from(account).where(account.name.like("a").when(false));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试group
     */
    @Test
    public void testGroup() {
        QueryDef queryDef = select(count(account.id)).from(account).groupBy(account.age);
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试having
     */
    @Test
    public void testHaving() {
        QueryDef queryDef = select(count(account.id)).from(account).groupBy(account.age).having(account.name.likeLeft("a"));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试orderBy
     */
    @Test
    public void testOrderBy() {
        QueryDef queryDef = select(count(account.id)).from(account).orderBy(account.age.desc(), account.name.asc());
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试Limit
     */
    @Test
    public void testLimit() {
        QueryDef queryDef = select(count(account.id)).from(account).limit(5, 10);
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }


    /**
     * 测试Offset
     */
    @Test
    public void testOffset() {
        QueryDef queryDef = select(count(account.id)).from(account).offset(10, 5);
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试Union
     */
    @Test
    public void testUnion() {
        QueryDef queryDef = select(count(account.id)).from(account).union(select(count(account.id)).from(account));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试UnionAll
     */
    @Test
    public void testUnionAll() {
        QueryDef queryDef = select(count(account.id)).from(account).unionAll(select(count(account.id)).from(account));
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试forUpdate
     */
    @Test
    public void testforUpdate() {
        QueryDef queryDef = select(count(account.id)).from(account).forUpdate();
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 测试forUpdateNoWait
     */
    @Test
    public void forUpdateNoWait() {
        QueryDef queryDef = select(count(account.id)).from(account).forUpdateNoWait();
        Statement statement = queryDef.statement();
        SqlInfo sqlInfo = flexDialect.toSQL(statement);
        System.out.println(sqlInfo.getSql());
    }
}
