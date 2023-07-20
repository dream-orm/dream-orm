package com.moxa.dream.flex.test;

import com.moxa.dream.flex.def.FromDef;
import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.flex.test.table.User;
import com.moxa.dream.system.config.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.moxa.dream.flex.def.FunctionDef.select;
import static com.moxa.dream.flex.test.table.table.UserTableDef.user;

@SpringBootTest
public class FlexMapperTest {
    @Autowired
    private FlexMapper flexMapper;
    @Autowired
    private HelloMapper helloMapper;

    @Test
    public void test0() {
        FromDef fromDef = select(user.name).from(user);
        Page<User> list = flexMapper.selectPage(fromDef, User.class, new Page(1, 1));
//            helloMapper.selectPage(new Page(1,1));
    }

    @Test
    public void test1() {
        FromDef fromDef = select(user.name).from(user);
        List<User> list = flexMapper.selectList(fromDef, User.class);
        System.out.println(list);
    }

    @Test
    public void test2() {
        FromDef fromDef = select(user.name).from(user);
        Page<User> list = flexMapper.selectPage(fromDef, User.class, new Page(1, 1));
//             helloMapper.selectPage(new Page(1, 1));

    }
}
