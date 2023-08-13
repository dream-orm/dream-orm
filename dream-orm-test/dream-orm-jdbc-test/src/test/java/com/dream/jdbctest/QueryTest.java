package com.dream.jdbctest;


import com.dream.jdbc.mapper.JdbcMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class QueryTest {
    @Autowired
    private JdbcMapper jdbcMapper;

    @Test
    public void test() {
        List<Map> ts = jdbcMapper.queryForList("select * from user", Map.class);
    }

    @Test
    public void test2() {
        List<Long> ts = jdbcMapper.queryForList("select id from user", Long.class);
    }

    @Test
    public void test3() {
        List<User> ts = jdbcMapper.queryForList("select * from user where id=?", User.class, 1);
    }

    @Test
    public void test4() {
        User ts = jdbcMapper.queryForObject("select * from user where id=?", User.class, 1);
    }

    @Test
    public void test5() {
        int execute = jdbcMapper.execute("create table aaa(id int)");
    }

    @Test
    public void test6() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setName("a");
        user.setAge(12);
        userList.add(user);
        userList.add(user);
        List<Object> objectList = jdbcMapper.batchExecute("insert into user(name,age)values(?,?)", userList, (ps, ms) -> {
            User u = (User) ms.getArg();
            ps.setString(1, u.getName());
            ps.setInt(2, u.getAge());
        });
        System.out.println(objectList);
    }
}
