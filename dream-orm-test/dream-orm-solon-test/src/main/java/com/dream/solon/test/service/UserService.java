package com.dream.solon.test.service;

import com.dream.solon.test.mapper.UserMapper;
import com.dream.solon.test.table.User;
import com.dream.template.mapper.TemplateMapper;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.util.List;
import java.util.Map;

@Component
public class UserService {
    @Inject
    private UserMapper userMapper;
    @Inject
    private TemplateMapper templateMapper;

    public Map findByName(String state) {
        Map map = userMapper.findByName(state);
        return map;
    }

    public User findByName2(String state) {
        User user = userMapper.findByName2(state);
        return user;
    }

    public Integer update(User user) {
        return userMapper.update(user);
    }

    public List<Object> insertBatch(List<User> userList) {
        return templateMapper.batchInsert(userList);
    }

    public void insertBatch2(List<User> userList) {
    }
}
