package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.select;
import static com.moxa.dream.flex.def.TableDef.table;
import static com.moxa.dream.flex.test.table.table.BlogTableDef.blog;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;

public class SimpleQueryTest {
    private ToSQL toSQL = new ToMYSQL();

    @Test
    public void test0() {
        SqlInfo muser = select(user.id.add(0).divide(1).multiply(2).sub(4).as("id"), user.name.as("ne"), user.del_flag)
                .from(user.as("u"))
                .where(user.name.eq(1).and(user.name.eq(user.name).and(user.name.lt(2)).and(user.name.leq(2.5)).and(user.name.gt(3).and(user.name.geq(4)))))
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test1() {
        SqlInfo muser = select(user.id.add(0).divide(1).multiply(2).sub(4).as("id"), user.name.as("ne"), user.del_flag)
                .from(user.as("u"))
                .where(user.name.in(1, 2, 3).and(user.name.notIn(1, 2, 3)).and(user.tenant_id.isNull().or(user.tenant_id.isNotNull())))
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test2() {
        SqlInfo muser = select(user.id, user.name, user.del_flag)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test3() {
        SqlInfo muser = select(user.id, user.name, user.del_flag)
                .from(user.as("u"))
                .innerJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)))
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test4() {
        SqlInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .rightJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)))
                .groupBy(user.dept_id).forUpdate()
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test5() {
        SqlInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0)).forUpdateNoWait()
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test6() {
        SqlInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0))
                .orderBy(user.del_flag.desc())
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test7() {
        Integer[] a = {4, 5, 6};
        SqlInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1, 2, 3)).and(user.tenant_id.in(a)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0))
                .orderBy(user.del_flag.desc())
                .limit(2, 3)
                .toSQL(toSQL);
        System.out.println(muser);
    }

    @Test
    public void test8() {
        UserTableDef user2 = new UserTableDef("u2");
        SqlInfo muser = select(user2.id, user2.name, user2.del_flag)
                .from(table(select(user.id, user.name, user.del_flag).from(user)).as("u2"))
                .leftJoin(blog)
                .on(user2.id.eq(blog.user_id))
                .toSQL(toSQL);
        System.out.println(muser);
    }
}
