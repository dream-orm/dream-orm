package com.dream.test;

import com.dream.antlr.sql.ToMYSQL;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.Query;
import com.dream.test.table.table.BlogTableDef;
import com.dream.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;

public class SimpleQueryTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    /**
     * 测试普通查询
     */
    @Test
    public void testColumn() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试采用view类查询
     */
    @Test
    public void testView() {
        Query query = FunctionDef.select(UserTableDef.user.userView).from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试别名
     */
    @Test
    public void testColumnAs() {
        UserTableDef u = new UserTableDef("u");
        Query query = FunctionDef.select(u.id.as("key"), u.name, u.email).from(u);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试关联多表
     */
    @Test
    public void testManyTable() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user).leftJoin(BlogTableDef.blog).on(UserTableDef.user.id.eq(BlogTableDef.blog.id));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试列计算
     */
    @Test
    public void testRowCalc() {
        Query query = FunctionDef.select(UserTableDef.user.id.add(12), UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试case
     */
    @Test
    public void testCase() {
        Query query = FunctionDef.select(UserTableDef.user.id, FunctionDef.case_(UserTableDef.user.id).when(1).then("admin").when(2).then("guest").end()).from(UserTableDef.user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试where
     */
    @Test
    public void testWhere() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user).where(UserTableDef.user.name.like("admin").and(UserTableDef.user.id.eq(2).or(UserTableDef.user.id.eq(3))));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试where select
     */
    @Test
    public void testWhereSelect() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user).where(UserTableDef.user.id.in(FunctionDef.select(BlogTableDef.blog.user_id).from(BlogTableDef.blog)));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试where like
     */
    @Test
    public void testWhereLike() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user).where(UserTableDef.user.name.like("a").and(UserTableDef.user.name.likeLeft("b").and(UserTableDef.user.name.likeRight("c"))));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试where dynamic
     */
    @Test
    public void testWhereDynamic() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user)
                .where(UserTableDef.user.name.like("a").when(true)
                        .and(UserTableDef.user.name.likeLeft(null).when(false))
                        .and(UserTableDef.user.name.likeRight("c", c -> c != null)));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试groupBy和函数
     */
    @Test
    public void testGroupBy() {
        Query query = FunctionDef.select(UserTableDef.user.id, FunctionDef.count()).from(UserTableDef.user).groupBy(UserTableDef.user.id);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试having函数
     */
    @Test
    public void testHaving() {
        Query query = FunctionDef.select(UserTableDef.user.id, FunctionDef.count()).from(UserTableDef.user).groupBy(UserTableDef.user.id).having(UserTableDef.user.id.eq(1).when(false));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试orderBy
     */
    @Test
    public void testOrderBy() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user).orderBy(UserTableDef.user.id.asc());
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试分页
     */
    @Test
    public void testLimit() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name, UserTableDef.user.email).from(UserTableDef.user).limit(1, 1);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试union
     */
    @Test
    public void testUnion() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name).from(UserTableDef.user).union(FunctionDef.select(BlogTableDef.blog.id, BlogTableDef.blog.name).from(BlogTableDef.blog));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    /**
     * 测试forUpdate
     */
    @Test
    public void testForUpdate() {
        Query query = FunctionDef.select(UserTableDef.user.id, UserTableDef.user.name).from(UserTableDef.user).forUpdate();
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }
}
