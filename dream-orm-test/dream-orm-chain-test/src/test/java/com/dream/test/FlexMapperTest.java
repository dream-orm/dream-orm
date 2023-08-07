package com.dream.test;

import com.dream.chain.mapper.FlexChainMapper;
import com.dream.test.table.User;
import com.dream.test.table.table.UserTableDef;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FlexMapperTest {
    @Autowired
    private FlexChainMapper flexChainMapper;
    @Autowired
    private HelloMapper helloMapper;

    @Test
    public void testSelect() {
        List<User> userList = flexChainMapper.select(UserTableDef.user.columns).from(UserTableDef.user).list(User.class);
        System.out.println(userList);
    }

    @Test
    public void testInsert() {
        flexChainMapper.insertInto(UserTableDef.user).columns(UserTableDef.user.id, UserTableDef.user.name).values(12, "11").execute();
    }

    @Test
    public void testUpdate() {
        flexChainMapper.update(UserTableDef.user).set(UserTableDef.user.name, "hello").where(UserTableDef.user.id.eq(1)).execute();
    }

    @Test
    public void testDelete() {
        flexChainMapper.delete(UserTableDef.user).where(UserTableDef.user.id.eq(1)).execute();
    }
}
