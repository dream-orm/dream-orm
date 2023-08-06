package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.InsertIntoValuesDef;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.insertInto;
import static com.moxa.dream.flex.def.FunctionDef.select;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;


public class InsertTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    @Test
    public void test0() {
        InsertIntoValuesDef valuesDef = insertInto(user).columns(user.id, user.name).values(1, "aa");
        SqlInfo sqlInfo = printSqlTest.toSQL(valuesDef.statement());
        System.out.println(sqlInfo);
    }

    @Test
    public void test1() {
        InsertIntoValuesDef valuesDef = insertInto(user).columns(user.id, user.name).values(select(user.id, user.name).from(user).where(user.id.eq(1)));
        SqlInfo sqlInfo = printSqlTest.toSQL(valuesDef.statement());
        System.out.println(sqlInfo);
    }
}
