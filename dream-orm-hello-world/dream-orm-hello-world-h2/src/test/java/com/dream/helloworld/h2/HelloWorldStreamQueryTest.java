package com.dream.helloworld.h2;


import com.dream.antlr.sql.ToMySQL;
import com.dream.helloworld.h2.table.Account;
import com.dream.stream.support.Wrappers;
import com.dream.stream.wrapper.QueryWrapper;
import com.dream.stream.wrapper.defaults.DefaultFromWrapper;
import com.dream.struct.factory.StructFactory;
import com.dream.struct.factory.DefaultStructFactory;
import com.dream.system.config.MappedStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldStreamQueryTest {
    private StructFactory dialectFactory = new DefaultStructFactory(new ToMySQL());

    /**
     * 测试select多个字段
     */
    @Test
    public void testSelectColumn() {
        DefaultFromWrapper<Account> wrapper = Wrappers.query(Account.class).select("id", "name", "age");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testSelectFunc() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select(i -> i.len("name").ascii("name").length("name"));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testWhere() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class)
                .leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11")));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testWhere2() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).where(i -> i.leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11"))));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testWhereNot() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class)
                .notIn("age", 1, 2, 3)
                .notIn("age", Wrappers.query(Account.class).select("age"))
                .notLike("name", "a")
                .notLikeLeft("name", "b")
                .notLikeRight("name", "c")
                .notBetween("age", 11, 34);
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testGroup() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select("avg(age) age", "name").groupBy("name");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testHaving() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select("avg(age) age", "name").groupBy("name").leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11")));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testHaving2() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).select("avg(age) age", "name").groupBy("name").having(i -> i.leq("age", 11).and(a -> a.leq("age", 11).or(b -> b.like("name", "11"))));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testOrder() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).orderBy("name", "age");
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testOrder2() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).orderBy(i -> i.asc("name").desc("age"));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testLimit() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).limit(5, 10);
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testOffset() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).offset(5, 10);
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testUnion() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).union(Wrappers.query(Account.class));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testUnionAll() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).unionAll(Wrappers.query(Account.class));
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testForUpdate() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).forUpdate();
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

    @Test
    public void testForUpdateNoWait() {
        QueryWrapper<Account> wrapper = Wrappers.query(Account.class).forUpdateNoWait();
        MappedStatement mappedStatement = dialectFactory.compile(wrapper, null);
        System.out.println(mappedStatement.getSql());
    }

}
