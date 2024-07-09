package com.dream.helloworld.h2;

import com.dream.antlr.sql.ToMySQL;
import com.dream.flex.def.DeleteDef;
import com.dream.struct.factory.DefaultStructFactory;
import com.dream.system.config.MappedStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dream.flex.def.FunctionDef.delete;
import static com.dream.helloworld.h2.def.AccountDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexDeleteTest {

    DefaultStructFactory dialectFactory = new DefaultStructFactory(new ToMySQL());


    @Test
    public void testDelete() {
        DeleteDef deleteDef = delete(account).where(account.id.eq(1));
        MappedStatement mappedStatement = dialectFactory.compile(deleteDef, null);
        System.out.println(mappedStatement.getSql());
    }
}
