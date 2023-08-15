package com.dream.helloworld;

import com.dream.helloworld.view.AccountView;
import com.dream.template.mapper.TemplateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldTemplateTest {
    @Autowired
    private TemplateMapper templateMapper;
    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = templateMapper.selectById(AccountView.class, 1);
        System.out.println("查询结果："+accountView);
    }
}
