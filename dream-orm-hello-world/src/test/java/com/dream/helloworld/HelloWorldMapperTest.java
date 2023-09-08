package com.dream.helloworld;

import com.dream.helloworld.mapper.AccountMapper;
import com.dream.helloworld.mapper.ProviderAccountMapper;
import com.dream.helloworld.view.AccountView;
import com.dream.system.config.Page;
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
public class HelloWorldMapperTest {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ProviderAccountMapper providerAccountMapper;

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = accountMapper.selectById(1);
        System.out.println("查询结果：" + accountView);
    }

    /**
     * 测试查询多条
     */
    @Test
    public void testSelectList() {
        List<AccountView> accountViews = accountMapper.selectList(3);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试非空条件去除查询
     */
    @Test
    public void testSelectNotList() {
        AccountView accountView = new AccountView();
        accountView.setId(3);
        accountView.setName("");
        List<AccountView> accountViews = accountMapper.selectNotList(accountView);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Page page = new Page(1, 2);
        List<AccountView> accountViews = accountMapper.selectPage(1, page);
        System.out.println("总数：" + page.getTotal() + "\n查询结果：" + accountViews);
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        AccountView accountView = new AccountView();
        accountView.setId(1);
        accountView.setName("accountName");
        accountMapper.updateById(accountView);
    }

    /**
     * 测试非空更新
     */
    @Test
    public void testUpdateNon() {
        AccountView accountView = new AccountView();
        accountView.setId(1);
        accountView.setName("accountName");
        accountMapper.updateNonById(accountView);
    }


    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        AccountView accountView = new AccountView();
        accountView.setId(300);
        accountView.setName("accountName");
        accountMapper.insert(accountView);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        accountMapper.deleteById(1);
    }

    /**
     * 测试主键集合批量删除
     */
    @Test
    public void testDeleteByIds() {
        accountMapper.deleteByIds(Arrays.asList(1, 2, 3));
    }

    /**
     * 测试对象集合批量删除
     */
    @Test
    public void testDeleteByViews() {
        List<AccountView> accountViews = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            AccountView accountView = new AccountView();
            accountView.setId(i);
            accountViews.add(accountView);
        }
        accountMapper.deleteByViews(accountViews);
    }

    /**
     * 测试SQL仅仅写在其他位置
     */
    @Test
    public void testSelectProviderById() {
        AccountView accountView = providerAccountMapper.selectProvideById(1);
        System.out.println("查询结果：" + accountView);
    }

    /**
     * 测试SQL全面增强
     */
    @Test
    public void selectProvideList() {
        AccountView accountView = new AccountView();
        accountView.setId(3);
        List<AccountView> accountViews = providerAccountMapper.selectProvideList(accountView);
        System.out.println("查询结果：" + accountViews);
    }


}
