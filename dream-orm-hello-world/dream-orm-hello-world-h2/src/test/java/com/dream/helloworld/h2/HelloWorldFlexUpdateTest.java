package com.dream.helloworld.h2;

import com.dream.antlr.sql.ToClickHouse;
import com.dream.flex.def.UpdateDef;
import com.dream.regular.factory.DefaultCommandDialectFactory;
import com.dream.system.config.MappedStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dream.flex.def.FunctionDef.update;
import static com.dream.helloworld.h2.def.AccountDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexUpdateTest {
    DefaultCommandDialectFactory dialectFactory = new DefaultCommandDialectFactory(new ToClickHouse());

    @Test
    public void testUpdate() {
        UpdateDef updateDef = update(account).set(account.age, account.age.add(1)).set(account.name, "accountName").where(account.id.eq(1));
        MappedStatement mappedStatement = dialectFactory.compile(updateDef, null);
        System.out.println(mappedStatement.getSql());
    }
}
