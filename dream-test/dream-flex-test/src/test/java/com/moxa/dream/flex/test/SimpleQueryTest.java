package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.Query;
import com.moxa.dream.flex.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.*;
import static com.moxa.dream.flex.def.TableDef.table;
import static com.moxa.dream.flex.test.table.table.BlogTableDef.blog;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;

public class SimpleQueryTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());


    @Test
    public void testColumn() {
        Query query = select(user.id, user.name, user.email).from(user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }
    @Test
    public void testView() {
        Query query = select(user.userView).from(user);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }
    @Test
    public void testColumnAs() {
        UserTableDef u=new UserTableDef("u");
        Query query = select(u.id.as("key"), u.name, u.email).from(u);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }
    @Test
    public void test0() {
        Query query = select(user.id.add(0).divide(1).multiply(2).sub(4).as("id"), user.name.as("ne"), user.del_flag)
                .from(user)
                .where(user.name.eq(1).and(user.name.eq(user.name).and(user.name.lt(2)).and(user.name.leq(2.5)).and(user.name.gt(3).and(user.name.geq(4)))));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test1() {
        Query query = select(user.id.add(0).divide(1).multiply(2).sub(4).as("id"), user.name.as("ne"), user.del_flag)
                .from(user)
                .where(user.name.in(1, 2, 3).and(user.name.notIn(1, 2, 3)).and(user.tenant_id.isNull().or(user.tenant_id.isNotNull())));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test2() {
        Query query = select(user.id, user.name, user.del_flag)
                .from(user)
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test3() {
        Query query = select(user.id.as("id"), user.name, user.del_flag)
                .from(user)
                .innerJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test4() {
        Query query = select(user.dept_id)
                .from(user)
                .rightJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)))
                .groupBy(user.dept_id).forUpdate();
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test5() {
        Query query = select(user.dept_id)
                .from(user)
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0)).forUpdateNoWait();
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test6() {
        Query query = select(user.dept_id)
                .from(user)
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0))
                .orderBy(user.del_flag.desc());
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test7() {
        Integer[] a = {4, 5, 6};
        Query query = select(user.dept_id)
                .from(user)
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)).and(user.tenant_id.in(a)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0))
                .orderBy(user.del_flag.desc())
                .limit(2, 3);
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test8() {
        UserTableDef user2 = new UserTableDef("u2");
        Query query = select(user2.id, user2.name, user2.del_flag)
                .from(table(select(user.id, user.name, user.del_flag).from(user)).as("u2"))
                .leftJoin(blog)
                .on(user2.id.eq(blog.user_id));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test9() {
        Query query = select(user.userView)
                .from(user)
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test10() {
        Query query = select(user.userView)
                .from(user)
                .where(exists(select(blog.user_id).from(blog).where(blog.user_id.eq(1))));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test11() {
        Query query = select(user.userView)
                .from(user)
                .where(notExists(select(blog.user_id).from(blog).where(blog.user_id.eq(1))));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }

    @Test
    public void test12() {
        Query query = select(user.userView)
                .from(user)
                .where(user.id.notLike(1).and(user.id.notLikeLeft(2)));
        SqlInfo sqlInfo = printSqlTest.toSQL(query);
        System.out.println(sqlInfo);
    }
}
