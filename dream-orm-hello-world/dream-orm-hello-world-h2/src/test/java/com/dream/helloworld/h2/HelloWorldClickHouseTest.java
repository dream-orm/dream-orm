package com.dream.helloworld.h2;

import com.dream.antlr.sql.ToClickHouse;
import com.dream.flex.def.QueryDef;
import com.dream.struct.factory.DefaultStructFactory;
import com.dream.system.config.MappedStatement;
import org.junit.Test;

import static com.dream.flex.def.FunctionDef.select;
import static com.dream.flex.def.FunctionDef.table;
import static com.dream.helloworld.h2.def.ClickhouseFunctionDef.column2;

public class HelloWorldClickHouseTest {
    DefaultStructFactory dialectFactory = new DefaultStructFactory(new ToClickHouse());

    @Test
    public void testSelect() {
        QueryDef queryDef = select("user_name").from(table("ais_audit_log_all").as("log").leftJoin(table("qudit_ueba_user_all").as("user")).on(column2("user", "user_id").eq(column2("log", "id"))));
        MappedStatement mappedStatement = dialectFactory.compile(queryDef, null);
        System.out.println(mappedStatement.getSql());
    }
}
