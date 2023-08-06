package com.moxa.dream.flex.test;

import com.moxa.dream.chain.mapper.FlexChainMapper;
import com.moxa.dream.flex.test.table.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.moxa.dream.flex.test.table.table.UserTableDef.user;

@SpringBootTest
public class FlexMapperTest {
    @Autowired
    private FlexChainMapper flexChainMapper;
    @Autowired
    private HelloMapper helloMapper;

    @Test
    public void testSelect() {
        List<User> userList = flexChainMapper.select(user.columns).from(user).list(User.class);
        System.out.println(userList);
    }

    @Test
    public void testInsert() {
        flexChainMapper.insertInto(user).columns(user.id, user.name).values(12, "11").execute();
    }

    @Test
    public void testUpdate() {
        flexChainMapper.update(user).set(user.name, "hello").where(user.id.eq(1)).execute();
    }

    @Test
    public void testDelete() {
        flexChainMapper.delete(user).where(user.id.eq(1)).execute();
    }
}
