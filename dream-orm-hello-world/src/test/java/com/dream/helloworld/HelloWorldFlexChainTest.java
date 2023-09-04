package com.dream.helloworld;

import com.dream.chain.mapper.FlexChainMapper;
import com.dream.flex.def.QueryDef;
import com.dream.helloworld.view.AccountView;
import com.dream.helloworld.view.TreeAccountView;
import com.dream.system.config.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.dream.flex.def.FunctionDef.select;
import static com.dream.helloworld.table.table.AccountTableDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexChainTest {
    @Autowired
    private FlexChainMapper flexChainMapper;

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = flexChainMapper
                .select(account.accountView)
                .from(account)
                .where(account.id.eq(1))
                .one(AccountView.class);
        System.out.println("查询结果：" + accountView);
    }

    /**
     * 测试查询多条
     */
    @Test
    public void testSelectList() {
        List<AccountView> accountViews = flexChainMapper.select(account.accountView).from(account)
                .where(account.id.gt(3)).list(AccountView.class);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Page page = new Page(1, 2);
        page = flexChainMapper.select(account.accountView).from(account).where(account.id.gt(1)).page(AccountView.class, page);
        System.out.println("总数：" + page.getTotal() + "\n查询结果：" + page.getRows());
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        flexChainMapper.update(account).set(account.age, account.age.add(1))
                .set(account.name, "accountName")
                .where(account.id.eq(1)).execute();
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        flexChainMapper.insertInto(account).columns(account.name, account.age).values("accountName", 12).execute();
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        flexChainMapper.delete(account).where(account.id.eq(1)).execute();
    }

    /**
     * 测试存在
     */
    @Test
    public void testExist() {
        boolean exists = flexChainMapper.select().from(account).where(account.id.gt(3)).exists();
        System.out.println("查询结果：" + exists);
    }

    /**
     * 测试树查询
     */
    @Test
    public void testTree() {
        List<TreeAccountView> treeAccountViews = flexChainMapper.select(account.treeAccountView).from(account).tree(TreeAccountView.class);
        System.out.println("查询结果：" + treeAccountViews);
    }
}
