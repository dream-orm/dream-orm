package com.dream.helloworld;

import com.dream.flex.def.Query;
import com.dream.flex.mapper.FlexMapper;
import com.dream.helloworld.view.AccountView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dream.flex.def.FunctionDef.select;
import static com.dream.helloworld.table.table.AccountTableDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexTest {
    @Autowired
    private FlexMapper flexMapper;

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        Query query = select(account.accountView).from(account).where(account.id.eq(1));
        AccountView accountView = flexMapper.selectOne(query, AccountView.class);
        System.out.println("查询结果：" + accountView);
    }
}
