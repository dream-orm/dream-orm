package com.moxa.dream.test;


import com.moxa.dream.BootApplication;
import com.moxa.dream.base.mapper.UserMapper;
import com.moxa.dream.base.service.UserService;
import com.moxa.dream.base.table.User;
import com.moxa.dream.base.view.UserView4;
import com.moxa.dream.template.mapper.TemplateMapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class InsertTest {
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setId(10);
        user.setName("name");
        user.setAge(11);
        user.setEmail("email");
        templateMapper.insert(user);
    }

    @Test
    public void testInsertFetchKey() {
        User user = new User();
        user.setName("name");
        user.setAge(11);
        user.setEmail("email");
        Object o = templateMapper.insertFetchKey(user);
        System.out.println(o);
    }

    @Test
    public void testInsertBatch() {
        List<User> userList = new ArrayList<>();
        for (int k = 0; k < 1500; k++) {
            User user = new User();
            user.setId(10000 + k);
            user.setName("name");
            user.setAge(11);
            user.setEmail("email");
            userList.add(user);
        }
        List<Object> list = userService.insertBatch(userList);
        System.out.println(list);
    }


    @Test
    public void testInsertMany2() {
        List<User> userList = new ArrayList<>();
        for (int k = 0; k < 10; k++) {
            User user = new User();
            user.setId(20 + k + 10);
            user.setName("name");
            user.setAge(11);
            user.setEmail("email");
            userList.add(user);
        }
        userService.insertBatch2(userList);
    }

    @Test
    public void testInsertInject() {
        UserView4 user = new UserView4();
        user.setId(1011);
        user.setName("name");
        user.setEmail("email");
        templateMapper.insert(user);
        Integer delFlag = user.getDelFlag();
        if (delFlag != 0) {
            throw new DreamRunTimeException("error");
        }
    }
}
