package com.dream.helloworld.db.tenant;

import com.dream.helloworld.db.tenant.mapper.AccountMapper;
import com.dream.helloworld.db.tenant.view.AccountView;
import com.dream.mate.share.holder.DataSourceHolder;
import com.dream.template.mapper.TemplateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldDbTenantApplication.class)
public class HelloWorldDbTenantTest {
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 采用注解share实现动态数据源切换
     */
    @Test
    public void testSelectById() {
        List<AccountView> accountViews = accountMapper.selectList(1);
        System.out.println(accountViews);
    }
}
