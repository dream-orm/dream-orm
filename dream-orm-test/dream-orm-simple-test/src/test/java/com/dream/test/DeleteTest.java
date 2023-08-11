package com.dream.test;

import com.dream.template.mapper.TemplateMapper;
import com.dream.test.base.mapper.UserMapper;
import com.dream.test.base.table.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class DeleteTest {
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        templateMapper.deleteById(User.class, 1);
    }

    @Test
    public void deleteByIds() {
        templateMapper.deleteByIds(User.class, Arrays.asList(1, 2, 3));
    }

    @Test
    public void deleteById2() {
        templateMapper.deleteByIds(User.class, Arrays.asList(1, 2, 3, 4, 5, 6));
    }

    @Test
    public void deleteBatch() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(1);
        User user2 = new User();
        user2.setId(2);
        userList.add(user);
        userList.add(user2);
        userMapper.delete(userList);
    }
}
