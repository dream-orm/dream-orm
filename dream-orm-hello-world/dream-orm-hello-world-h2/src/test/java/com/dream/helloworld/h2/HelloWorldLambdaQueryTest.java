package com.dream.helloworld.h2;


import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.sql.ToMySQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.lambda.config.SqlInfo;
import com.dream.lambda.dialect.AbstractLambdaDialect;
import com.dream.lambda.support.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldLambdaQueryTest {

    private ToSQL toSQL = new ToMySQL();

    /**
     * 测试select多个字段
     */
    @Test
    public void testSelect() throws AntlrException {
        QueryStatement statement = Wrappers.query().lt("abc", 11).eq("ab3", 12).statement();
        SqlInfo sqlInfo = new AbstractLambdaDialect(toSQL) {
            @Override
            protected List<Invoker> invokerList() {
                return null;
            }
        }.toSQL(statement, null);
        System.out.println(sqlInfo);
    }
}
