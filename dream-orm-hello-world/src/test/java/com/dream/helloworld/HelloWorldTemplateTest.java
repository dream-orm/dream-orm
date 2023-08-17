package com.dream.helloworld;

import com.dream.helloworld.condition.AccountCondition;
import com.dream.helloworld.condition.OrderAccountCondition;
import com.dream.helloworld.view.AccountView;
import com.dream.helloworld.view.ValidatedAccountView;
import com.dream.helloworld.view.WrapAccountView;
import com.dream.template.mapper.TemplateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

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
        System.out.println("查询结果：" + accountView);
    }

    /**
     * 测试注解条件
     */
    @Test
    public void testSelectAnnotationCondition() {
        AccountCondition accountCondition = new AccountCondition();
        accountCondition.setName("a");
        accountCondition.setAge(Arrays.asList(18, 20, 21, 24));
        List<AccountView> accountViews = templateMapper.selectList(AccountView.class, accountCondition);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试注解校验
     */
    @Test
    public void testInsertValidated() {
        ValidatedAccountView validatedAccountView = new ValidatedAccountView();
        validatedAccountView.setId(12);
        validatedAccountView.setName("123456");
        templateMapper.insert(validatedAccountView);
    }

    /**
     * 测试注解修改
     */
    @Test
    public void testUpdateWrap() {
        WrapAccountView wrapAccountView = new WrapAccountView();
        wrapAccountView.setId(1);
        wrapAccountView.setName("哈哈");
        templateMapper.updateById(wrapAccountView);
    }

    /**
     * 测试注解排序
     */
    @Test
    public void testSelectOrder() {
        OrderAccountCondition accountCondition = new OrderAccountCondition();
        accountCondition.setName("a");
        List<AccountView> accountViews = templateMapper.selectList(AccountView.class, accountCondition);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试主键序列
     */
    @Test
    public void testInsertSequence() {
        AccountView accountView = new AccountView();
        accountView.setName("12");
        templateMapper.insert(accountView);
    }
}
