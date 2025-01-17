package com.dream.helloworld.h2;

import com.dream.helloworld.h2.condition.AccountCondition;
import com.dream.helloworld.h2.condition.OrderAccountCondition;
import com.dream.helloworld.h2.view.AccountView;
import com.dream.helloworld.h2.view.AccountWithDeptView;
import com.dream.helloworld.h2.view.ValidatedAccountView;
import com.dream.helloworld.h2.view.WrapAccountView;
import com.dream.system.config.Page;
import com.dream.template.mapper.TemplateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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

    @Test
    public void testSelectByIds() {
        List<AccountView> accountViews = templateMapper.selectByIds(AccountView.class, Arrays.asList(1, 2, 3));
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试分页
     */
    @Test
    public void testSelectPage() {
        Page page = new Page<>(1, 10, 10);
        AccountCondition accountCondition = new AccountCondition();
        accountCondition.setName("a");
        accountCondition.setAge(Arrays.asList(18, 20, 21, 24));
        templateMapper.selectPage(AccountView.class, accountCondition, page);
        System.out.println("总数：" + page.getTotal() + "\n查询结果：" + page.getRows());
    }

    /**
     * 测试注解条件
     */
    @Test
    public void testSelectAnnotationCondition() {
        AccountCondition accountCondition = new AccountCondition();
        accountCondition.setName("a");
        accountCondition.setAge(Arrays.asList(18, 20, 21, 24));
        accountCondition.setAges(new Integer[]{10, 20});
        List<AccountView> accountViews = templateMapper.selectList(AccountView.class, accountCondition);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试注解条件
     */
    @Test
    public void testFetch() {
        List<AccountWithDeptView> accountViews = templateMapper.selectList(AccountWithDeptView.class, null);
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
     * 测试修改
     */
    @Test
    public void testUpdate() {
        AccountView accountView = new AccountView();
        accountView.setId(1);
        accountView.setName("Jone");
        accountView.setAge(11);
        accountView.setEmail("email123");
        templateMapper.updateById(accountView);
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
        accountCondition.setMinAge(1);
        accountCondition.setMaxAge(10);
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

    /**
     * 测试校验删除
     */
    @Test
    public void testDeleteValidated() {
        ValidatedAccountView validatedAccountView = new ValidatedAccountView();
        validatedAccountView.setId(13);
        validatedAccountView.setName("123456");
        templateMapper.delete(validatedAccountView);
    }

    @Test
    public void testBatchInsert() {
        List<AccountView> accountViews = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AccountView accountView = new AccountView();
            accountView.setId(700 + i);
            accountView.setName("name" + i);
            accountViews.add(accountView);
        }
        templateMapper.batchInsert(accountViews);
    }

    @Test
    public void testDeleteById() {
        templateMapper.deleteById(AccountView.class, 1);
    }

    @Test
    public void testDeleteByIds() {
        templateMapper.deleteByIds(AccountView.class, Arrays.asList(1, 2, 3, 4));
    }

    @Test
    public void testUpdateNonById() {
        AccountView accountView = new AccountView();
        accountView.setId(1);
        accountView.setName("哈哈");
        accountView.setAge(11);
        templateMapper.updateNonById(accountView);
    }

    @Test
    public void testBatchUpdate() {
        List<AccountView> accountViews = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AccountView accountView = new AccountView();
            accountView.setId(700 + i);
            accountView.setName("name" + i);
            accountViews.add(accountView);
        }
        templateMapper.batchUpdateById(accountViews);
    }

}
