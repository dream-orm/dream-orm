package com.dream.helloworld.h2;

import com.dream.antlr.sql.ToClickHouse;
import com.dream.drive.factory.DefaultFlexDialect;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.QueryDef;
import com.dream.flex.dialect.FlexDialect;
import org.junit.Test;

import static com.dream.flex.def.FunctionDef.select;
import static com.dream.flex.def.FunctionDef.table;
import static com.dream.helloworld.h2.def.ClickhouseFunctionDef.column2;

public class HelloWorldClickHouseTest {
    FlexDialect flexDialect = new DefaultFlexDialect(new ToClickHouse());

    @Test
    public void testSelect() {
        QueryDef queryDef = select("user_name").from(table("ais_audit_log_all").as("log").leftJoin(table("qudit_ueba_user_all").as("user")).on(column2("user", "user_id").eq(column2("log", "id"))));
        SqlInfo sqlInfo = flexDialect.toSQL(queryDef);
        System.out.println(sqlInfo.getSql());
    }
}
