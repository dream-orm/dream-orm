package com.moxa.dream.flex.test;

import com.moxa.dream.chain.mapper.FlexChainMapper;
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
    private FlexChainMapper flexChainMapper;
    @Autowired
    private HelloMapper helloMapper;

    @Test
    public void test0() {
        List<User> userList = flexChainMapper.select(user.columns).from(user).list(User.class);
        System.out.println(userList);
    }

}
