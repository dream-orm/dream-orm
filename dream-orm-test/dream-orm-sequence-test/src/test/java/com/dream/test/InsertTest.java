package com.dream.test;


import com.dream.template.mapper.TemplateMapper;
import com.dream.test.base.table.SysUser;
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

    @Test
    public void testInsert() {
        SysUser user = new SysUser();
        user.setName("name");
        user.setAge(11);
        user.setEmail("email");
        templateMapper.insert(user);
    }

    @Test
    public void testInsertFetchKey() {
        SysUser user = new SysUser();
        user.setName("name");
        user.setAge(11);
        user.setEmail("email");
        Object o = templateMapper.insertFetchKey(user);
        System.out.println(o);
    }

    @Test
    public void testInsertBatch() {
        List<SysUser> userList = new ArrayList<>();
        for (int k = 0; k < 1; k++) {
            SysUser user = new SysUser();
            user.setName("name");
            user.setAge(11);
            user.setEmail("email");
            userList.add(user);
        }
        List<Object> list = templateMapper.batchInsert(userList);
        System.out.println(list);
    }
}
