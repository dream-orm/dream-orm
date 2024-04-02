package com.dream.helloworld.h2;


import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.sql.ToMySQL;
import com.dream.helloworld.h2.table.Account;
import com.dream.instruct.factory.CommandDialectFactory;
import com.dream.instruct.factory.DefaultCommandDialectFactory;
import com.dream.system.config.MappedStatement;
import com.dream.wrap.support.Wrappers;
import com.dream.wrap.wrapper.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldWrapQueryTest {
    private CommandDialectFactory dialectFactory = new DefaultCommandDialectFactory(new ToMySQL());

    /**
     * 测试select多个字段
     */
    @Test
    public void testSelect() throws AntlrException {
        QueryWrapper wrapper = Wrappers.query(Account.class).leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11")));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
}
