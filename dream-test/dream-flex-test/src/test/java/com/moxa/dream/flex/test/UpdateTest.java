package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.Update;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.*;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;


public class UpdateTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    @Test
    public void test0() {
        Update update = update(user).set(user.name, concat(user.name, column("111"))).where(user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }

    @Test
    public void test1() {
        Update update = update(user).set(user.name, "ali").where(user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }

    @Test
    public void test2() {
        Update update = update(user).set(user.name, select(user.name).from(user).where(user.id.eq(2))).where(user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }

    @Test
    public void test3() {
        Update update = update(user).set(user.age,user.age.add(1)).where(user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }
}
