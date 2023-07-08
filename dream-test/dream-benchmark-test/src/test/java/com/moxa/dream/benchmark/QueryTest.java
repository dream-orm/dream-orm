package com.moxa.dream.benchmark;

import com.moxa.dream.BootApplication;
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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class QueryTest {
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private DataSource dataSource;
    int count = 1000;

    @Before
    public void before() {
        DataSourceFactory.initData(dataSource);
    }

    @Test
    public void test() {
        costTime((t) -> {
            AccountCondition accountCondition = new AccountCondition();
            accountCondition.setId(100l);
            accountCondition.setUserName("admin" + ThreadLocalRandom.current().nextInt(10000));
//            运行时间大约3秒，而mybatis-flex，大约200毫秒，是由于mybatis-flex没有做统计计算
            templateMapper.selectPage(Account.class, accountCondition,new Page(1,1));
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
