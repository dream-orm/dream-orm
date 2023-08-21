package com.dream.helloworld;

import com.dream.tdengine.mapper.FlexTdChainMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static com.dream.flex.def.FunctionDef.col;
import static com.dream.helloworld.table.table.AccountTableDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldTdSqlTest {
    @Autowired
    private FlexTdChainMapper flexMapper;

    /**
     * 普通插入sql
     */
    @Test
    public void testInsert() {
        flexMapper.insertInto("td1001").using(account).tags(1, 2, 3, 4).columns(account.name, account.age).values("accountName", 12).execute();
    }

    /**
     * 普通查询
     */
    @Test
    public void testQuery() {
        flexMapper.select(col("*")).from("d1001").partitionBy("id", "name").interval("10m").sLimit(10, 20).one(Map.class);
    }
}
