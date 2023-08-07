package com.dream.test;

import com.dream.flex.def.FromDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Page;
import com.dream.test.table.User;
import com.dream.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FlexMapperTest {
    @Autowired
    private FlexMapper flexMapper;
    @Autowired
    private HelloMapper helloMapper;

    @Test
    public void test0() {
        FromDef fromDef = FunctionDef.select(UserTableDef.user.columns).from(UserTableDef.user);
        Page<User> list = flexMapper.selectPage(fromDef, User.class, new Page(1, 1));
        System.out.println(list);
//            helloMapper.selectPage(new Page(1,1));
    }

    @Test
    public void test1() {
        FromDef fromDef = FunctionDef.select(UserTableDef.user.name).from(UserTableDef.user);
        List<User> list = flexMapper.selectList(fromDef, User.class);
        System.out.println(list);
    }

    @Test
    public void test2() {
        FromDef fromDef = FunctionDef.select(UserTableDef.user.name).from(UserTableDef.user);
        Page<User> list = flexMapper.selectPage(fromDef, User.class, new Page(1, 1));
//             helloMapper.selectPage(new Page(1, 1));

    }
}
