package com.dream.test;

import com.dream.antlr.sql.ToMYSQL;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.Update;
import com.dream.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;


public class UpdateTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    @Test
    public void test0() {
        Update update = FunctionDef.update(UserTableDef.user).set(UserTableDef.user.name, FunctionDef.concat(UserTableDef.user.name, FunctionDef.col("111"))).where(UserTableDef.user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }

    @Test
    public void test1() {
        Update update = FunctionDef.update(UserTableDef.user).set(UserTableDef.user.name, "ali").where(UserTableDef.user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }

    @Test
    public void test2() {
        Update update = FunctionDef.update(UserTableDef.user).set(UserTableDef.user.name, FunctionDef.select(UserTableDef.user.name).from(UserTableDef.user).where(UserTableDef.user.id.eq(2))).where(UserTableDef.user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }

    @Test
    public void test3() {
        Update update = FunctionDef.update(UserTableDef.user).set(UserTableDef.user.age, UserTableDef.user.age.add(1)).where(UserTableDef.user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(update);
        System.out.println(sqlInfo);
    }
}
