package com.dream.test;

import com.dream.antlr.sql.ToMYSQL;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.InsertIntoValuesDef;
import org.junit.jupiter.api.Test;

import static com.dream.test.table.table.UserTableDef.user;


public class InsertTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    @Test
    public void test0() {
        InsertIntoValuesDef valuesDef = FunctionDef.insertInto(user).columns(user.id, user.name).values(1, "aa");
        SqlInfo sqlInfo = printSqlTest.toSQL(valuesDef.statement());
        System.out.println(sqlInfo);
    }

    @Test
    public void test1() {
        InsertIntoValuesDef valuesDef = FunctionDef.insertInto(user).columns(user.id, user.name).values(FunctionDef.select(user.id, user.name).from(user).where(user.id.eq(1)));
        SqlInfo sqlInfo = printSqlTest.toSQL(valuesDef.statement());
        System.out.println(sqlInfo);
    }
}
