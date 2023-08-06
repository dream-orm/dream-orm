package com.moxa.dream.flex.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.Delete;
import org.junit.jupiter.api.Test;

import static com.moxa.dream.flex.def.FunctionDef.delete;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;


public class DeleteTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    @Test
    public void test0() {
        Delete delete = delete(user).where(user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(delete.getStatement());
        System.out.println(sqlInfo);
    }

    @Test
    public void test1() {
        Delete delete = delete(user).where(user.id.eq(1).and(user.name.like(11)));
        SqlInfo sqlInfo = printSqlTest.toSQL(delete.getStatement());
        System.out.println(sqlInfo);
    }
}
