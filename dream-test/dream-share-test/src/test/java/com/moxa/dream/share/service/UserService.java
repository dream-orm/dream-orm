package com.moxa.dream.share.service;

import com.moxa.dream.share.mapper.UserMapper;
import com.moxa.dream.share.table.User;
import com.moxa.dream.util.exception.DreamRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void share() {
        User user = new User();
        user.setId(2);
        user.setAge(11);
        user.setEmail("123");
        user.setName("321");
        userMapper.insert(user);
        List<User> userList = userMapper.findByName("321");
        if (userList.isEmpty()) {
            throw new DreamRunTimeException("error");
        }
        List<User> userList1 = userMapper.findByName2("321");
        if (!userList1.isEmpty()) {
            throw new DreamRunTimeException("error2");
        }
    }
}
