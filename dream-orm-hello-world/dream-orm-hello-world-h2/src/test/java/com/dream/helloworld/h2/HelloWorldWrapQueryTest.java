package com.dream.helloworld.h2;


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
    public void testSelectColumn() {
        QueryWrapper wrapper = Wrappers.query(Account.class).select("a","b","c");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
    @Test
    public void testSelectFunc() {
        QueryWrapper wrapper = Wrappers.query(Account.class).select(i -> i.len("a").ascii("b").length("c"));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
    @Test
    public void testWhere() {
        QueryWrapper wrapper = Wrappers.query(Account.class)
                .leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11")));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testWhere2() {
        QueryWrapper wrapper = Wrappers.query(Account.class).where(i -> i.leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11"))));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testGroup() {
        QueryWrapper wrapper = Wrappers.query(Account.class).groupBy("a");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testHaving() {
        QueryWrapper wrapper = Wrappers.query(Account.class).groupBy("a").leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11")));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testHaving2() {
        QueryWrapper wrapper = Wrappers.query(Account.class).groupBy("a").having(i -> i.leq("b", 11).and(a -> a.leq("age", 11).or(b -> b.like("a", "11"))));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }
}
