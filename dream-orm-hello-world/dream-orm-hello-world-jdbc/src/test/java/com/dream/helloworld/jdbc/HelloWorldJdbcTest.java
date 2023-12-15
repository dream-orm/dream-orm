package com.dream.helloworld.jdbc;

import com.dream.helloworld.jdbc.mapper.MasterAccountMapper;
import com.dream.helloworld.jdbc.mapper.SlaveAccountMapper;
import com.dream.helloworld.jdbc.view.AccountView;
import com.dream.mate.share.holder.DataSourceHolder;
import com.dream.template.mapper.TemplateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldJdbcApplication.class)
public class HelloWorldJdbcTest {
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private MasterAccountMapper masterAccountMapper;
    @Autowired
    private SlaveAccountMapper slaveAccountMapper;

    /**
     * 采用注解share实现动态数据源切换
     */
    @Test
    public void testSelectById() {
        List<AccountView> accountViews1 = masterAccountMapper.selectList(1);
        List<AccountView> accountViews2 = slaveAccountMapper.selectList(1);
        System.out.println("master数量：" + accountViews1.size());
        System.out.println("slave数量：" + accountViews2.size());
        System.out.println(accountViews1);
        System.out.println(accountViews2);
    }
    /**
     * 手动选择数据源切换，注解share失效
     */
    @Test
    public void testSelectById2() {
        DataSourceHolder.use("master",()->{
            List<AccountView> accountViews1 = masterAccountMapper.selectList(1);
            List<AccountView> accountViews2 = slaveAccountMapper.selectList(1);
            System.out.println("master数量：" + accountViews1.size());
            System.out.println("slave数量：" + accountViews2.size());
            System.out.println(accountViews1);
            System.out.println(accountViews2);
            return 0;
        });
    }

}
