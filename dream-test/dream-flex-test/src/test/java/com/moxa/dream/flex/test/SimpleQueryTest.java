package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.Query;
import com.moxa.dream.flex.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.*;
import static com.moxa.dream.flex.test.table.table.BlogTableDef.blog;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;

public class SimpleQueryTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    /**
     * 测试普通查询
     */
    @Test
    public void testColumn() {
        Query query = select(user.id, user.name, user.email).from(user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试采用view类查询
     */
    @Test
    public void testView() {
        Query query = select(user.userView).from(user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试别名
     */
    @Test
    public void testColumnAs() {
        UserTableDef u = new UserTableDef("u");
        Query query = select(u.id.as("key"), u.name, u.email).from(u);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试关联多表
     */
    @Test
    public void testManyTable() {
        Query query = select(user.id, user.name, user.email).from(user).leftJoin(blog).on(user.id.eq(blog.id));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试列计算
     */
    @Test
    public void testRowCalc() {
        Query query = select(user.id.add(12), user.name, user.email).from(user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试case
     */
    @Test
    public void testCase() {
        Query query = select(user.id, case_(user.id).when(1).then("admin").when(2).then("guest").end()).from(user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试where
     */
    @Test
    public void testWhere() {
        Query query = select(user.id, user.name, user.email).from(user).where(user.name.like("admin").and(user.id.eq(2).or(user.id.eq(3))));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试where select
     */
    @Test
    public void testWhereSelect() {
        Query query = select(user.id, user.name, user.email).from(user).where(user.id.in(select(blog.user_id).from(blog)));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试where like
     */
    @Test
    public void testWhereLike() {
        Query query = select(user.id, user.name, user.email).from(user).where(user.name.like("a").and(user.name.likeLeft("b").and(user.name.likeRight("c"))));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void testWhereDynamic() {
        Query query = select(user.id, user.name, user.email).from(user)
                .where(user.name.like("a").when(false)
                        .and(user.name.likeLeft(null).when(false))
                        .and(user.name.likeRight("c").when(false)));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试groupBy和函数
     */
    @Test
    public void testGroupBy() {
        Query query = select(user.id, count()).from(user).groupBy(user.id);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试having函数
     */
    @Test
    public void testHaving() {
        Query query = select(user.id, count()).from(user).groupBy(user.id).having(user.id.eq(1).when(false));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试orderBy
     */
    @Test
    public void testOrderBy() {
        Query query = select(user.id, user.name, user.email).from(user).orderBy(user.id.asc());
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试分页
     */
    @Test
    public void testLimit() {
        Query query = select(user.id, user.name, user.email).from(user).limit(1, 1);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试union
     */
    @Test
    public void testUnion() {
        Query query = select(user.id, user.name).from(user).union(select(blog.id, blog.name).from(blog));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试forUpdate
     */
    @Test
    public void testForUpdate() {
        Query query = select(user.id, user.name).from(user).forUpdate();
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }
}
