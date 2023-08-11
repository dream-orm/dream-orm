package com.dream.test;

import com.dream.template.mapper.TemplateMapper;
import com.dream.test.base.mapper.UserMapper;
import com.dream.test.base.table.User;
import com.dream.test.base.view.UserView4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class UpdateTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TemplateMapper templateMapper;

    @Test
    public void updateId() {
        User user = new User();
        user.setId(1);
        user.setName("name");
        user.setAge(1);
        user.setEmail("email");
//            cityService.update(city);
        templateMapper.updateById(user);
    }

    @Test
    public void updateNonId() {
        User user = new User();
        user.setId(1);
        user.setEmail("country");
        templateMapper.updateNonById(user);
    }

    @Test
    public void updateNonId2() {
        User user = new User();
        user.setId(1);
        user.setName("hli");
        user.setEmail("");
        userMapper.updateNon(user);
    }

    @Test
    public void updateInject() {
        UserView4 user = new UserView4();
        user.setId(1);
        user.setName("name");
        user.setEmail("email");
//            cityService.update(city);
        templateMapper.updateNonById(user);
    }

    @Test
    public void updateBatch() {
        List<UserView4> userView4List = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            UserView4 user = new UserView4();
            user.setId(i);
            user.setName("name");
            user.setEmail("email");
            userView4List.add(user);
        }
//            cityService.update(city);
        templateMapper.batchUpdateById(userView4List);
    }


    @Test
    public void testTruncate() {
        userMapper.truncate();
    }

    @Test
    public void testDrop() {
        userMapper.drop();
    }

}
