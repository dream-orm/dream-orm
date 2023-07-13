package com.moxa.dream.benchmark;

import com.moxa.dream.flex.def.SqlDef;
import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.template.mapper.TemplateMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static com.moxa.dream.benchmark.table.AccountTableDef.account;
import static com.moxa.dream.flex.def.FunctionDef.select;


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
//            SqlDef sqlDef = select(account.id, account.user_name, account.password, account.salt, account.nickname, account.email, account.mobile, account.avatar, account.type, account.status, account.created)
//                    .from(account).where(account.id.geq(100).or(account.user_name.eq("admin" + ThreadLocalRandom.current().nextInt(10000))));
//            Page<Account> page = flexMapper.selectPage(sqlDef, Account.class, new Page(1, 1));
            AccountCondition accountCondition = new AccountCondition();
            accountCondition.setId(100l);
            accountCondition.setUserName("admin1000");
            Page<Account>page = templateMapper.selectPage(Account.class, accountCondition,new Page(1,1));
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
