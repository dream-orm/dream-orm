package com.moxa.dream.base.service;

import com.moxa.dream.base.mapper.UserMapper;
import com.moxa.dream.base.table.User;
import com.moxa.dream.template.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
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
