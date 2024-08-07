package com.dream.helloworld.h2;

import com.dream.chain.mapper.FlexChainMapper;
import com.dream.flex.def.ColumnDef;
import com.dream.helloworld.h2.view.AccountView;
import com.dream.helloworld.h2.view.TreeAccountView;
import com.dream.system.config.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dream.helloworld.h2.def.AccountDef.account;


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
                .select()
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
        List<AccountView> accountViews = flexChainMapper.select().from(account)
                .where(account.id.gt(3)).list(AccountView.class);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Page page = new Page(1, 2);
        page = flexChainMapper.select().from(account).where(account.id.gt(1)).page(AccountView.class, page);
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
        flexChainMapper.insertInto(account).columns(account.name, account.age).values("accountName", 100).execute();
    }


    @Test
    public void testInsertMap() {
        Map<ColumnDef, Object> columnDefMap = new HashMap();
        columnDefMap.put(account.name, "aaa");
        columnDefMap.put(account.age, 22);
        flexChainMapper.insertInto(account).valuesMap(columnDefMap).execute();
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
        List<TreeAccountView> treeAccountViews = flexChainMapper.select().from(account).tree(TreeAccountView.class);
        System.out.println("查询结果：" + treeAccountViews);
    }
}
