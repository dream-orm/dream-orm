package com.dream.benchmark;

import com.dream.benchmark.table.AccountTableDef;
import com.dream.flex.def.Query;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Page;
import com.dream.template.mapper.TemplateMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static com.dream.flex.def.FunctionDef.select;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class QueryTest {
    int count = 1000;
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private FlexMapper flexMapper;
    @Autowired
    private DataSource dataSource;

    @Before
    public void before() {
        DataSourceFactory.initData(dataSource);
    }

    @Test
    public void test() {
        costTime((t) -> {
            Query query = select(AccountTableDef.account.id, AccountTableDef.account.user_name, AccountTableDef.account.password, AccountTableDef.account.salt, AccountTableDef.account.nickname, AccountTableDef.account.email, AccountTableDef.account.mobile, AccountTableDef.account.avatar, AccountTableDef.account.type, AccountTableDef.account.status, AccountTableDef.account.created)
                    .from(AccountTableDef.account).where(AccountTableDef.account.id.geq(100).or(AccountTableDef.account.user_name.eq("admin" + ThreadLocalRandom.current().nextInt(10000))));
            Page<Account> page = flexMapper.selectPage(query, Account.class, new Page(1, 1));
//            AccountCondition accountCondition = new AccountCondition();
//            accountCondition.setId(100l);
//            accountCondition.setUserName("admin"+ThreadLocalRandom.current().nextInt(10000));
//            Page<Account>page = templateMapper.selectPage(Account.class, accountCondition,new Page(1,1));
            return null;
        });
    }

    public void costTime(Function function) {
        function.apply(null);
        long l = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            function.apply(null);
        }
        System.out.println(System.currentTimeMillis() - l);
    }
}
