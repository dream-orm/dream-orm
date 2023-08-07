package com.dream.test;

import com.dream.antlr.sql.ToMYSQL;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.Delete;
import com.dream.flex.def.FunctionDef;
import com.dream.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;


public class DeleteTest {
    private PrintSqlTest printSqlTest = new PrintSqlTest(new ToMYSQL());

    @Test
    public void test0() {
        Delete delete = FunctionDef.delete(UserTableDef.user).where(UserTableDef.user.id.eq(1));
        SqlInfo sqlInfo = printSqlTest.toSQL(delete.statement());
        System.out.println(sqlInfo);
    }

    @Test
    public void test1() {
        Delete delete = FunctionDef.delete(UserTableDef.user).where(UserTableDef.user.id.eq(1).and(UserTableDef.user.name.like(11)));
        SqlInfo sqlInfo = printSqlTest.toSQL(delete.statement());
        System.out.println(sqlInfo);
    }
}
