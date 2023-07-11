package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.def.FunctionDef;
import com.moxa.dream.flex.def.ResultInfo;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.select;
import static com.moxa.dream.flex.test.table.table.BlogTableDef.blog;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;

public class MyTest {
    private ToSQL toSQL=new ToMYSQL();
    @Test
    public void test1() {
        ResultInfo muser = select(user.id, user.name.as("ne"), user.del_flag)
                .from(user.as("u"))
                .toSQL(toSQL);
        System.out.println(muser);
    }
    @Test
    public void test2() {
        ResultInfo muser = select(user.id, user.name, user.del_flag)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .toSQL(toSQL);
        System.out.println(muser);
    }
    @Test
    public void test3() {
        ResultInfo muser = select(user.id, user.name, user.del_flag)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1,2,3)))
                .toSQL(toSQL);
        System.out.println(muser);
    }
    @Test
    public void test4() {
        ResultInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1,2,3)))
                .groupBy(user.dept_id)
                .toSQL(toSQL);
        System.out.println(muser);
    }
    @Test
    public void test5() {
        ResultInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1,2,3)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0))
                .toSQL(toSQL);
        System.out.println(muser);
    }
    @Test
    public void test6() {
        ResultInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1,2,3)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0))
                .orderBy(user.del_flag.desc())
                .toSQL(toSQL);
        System.out.println(muser);
    }
    @Test
    public void test7() {
        ResultInfo muser = select(user.dept_id)
                .from(user.as("u"))
                .leftJoin(blog)
                .on(user.id.eq(blog.user_id))
                .where(user.id.eq(1).and(user.name.like("12")).and(user.tenant_id.notIn(1,2,3)))
                .groupBy(user.dept_id)
                .having(user.del_flag.eq(0))
                .orderBy(user.del_flag.desc())
                .limit(2,3)
                .toSQL(toSQL);
        System.out.println(muser);
    }
}
