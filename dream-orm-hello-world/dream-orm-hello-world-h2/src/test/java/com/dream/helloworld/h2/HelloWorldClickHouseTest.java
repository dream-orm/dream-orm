package com.dream.helloworld.h2;

import com.dream.antlr.sql.ToClickHouse;
import com.dream.drive.factory.DefaultFlexDialect;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.QueryDef;
import com.dream.flex.dialect.FlexDialect;
import org.junit.Test;

import static com.dream.flex.def.FunctionDef.col;
import static com.dream.flex.def.FunctionDef.select;
import static com.dream.helloworld.h2.def.ClickhouseFunctionDef.plus;
import static com.dream.helloworld.h2.def.ClickhouseFunctionDef.toTypeName;

public class HelloWorldClickHouseTest {
    FlexDialect flexDialect = new DefaultFlexDialect(new ToClickHouse());

    @Test
    public void testSelect() {
        QueryDef queryDef = select(plus(col(1), col(2)), toTypeName(col(122)));
        SqlInfo sqlInfo = flexDialect.toSQL(queryDef);
        System.out.println(sqlInfo.getSql());
    }
}
