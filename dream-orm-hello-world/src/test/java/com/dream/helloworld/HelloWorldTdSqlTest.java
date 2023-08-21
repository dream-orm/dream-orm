package com.dream.helloworld;

import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.*;
import com.dream.flex.mapper.DefaultFlexMapper;
import com.dream.flex.mapper.FlexMapper;
import com.dream.helloworld.table.Account;
import com.dream.helloworld.table.table.AccountTableDef;
import com.dream.helloworld.view.AccountView;
import com.dream.system.config.Page;
import com.dream.system.core.session.Session;
import com.dream.tdengine.def.TdChainFromDef;
import com.dream.tdengine.def.TdChainHavingDef;
import com.dream.tdengine.def.TdChainSelectDef;
import com.dream.tdengine.def.TdFunctionDef;
import com.dream.tdengine.mapper.DefaultFlexTdChainMapper;
import com.dream.tdengine.mapper.FlexTdChainMapper;
import com.dream.tdengine.sql.ToTdEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dream.flex.def.FunctionDef.*;
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
        flexMapper.insertInto("td1001").using(account).tags(1,2,3,4).columns(account.name,account.age).values("accountName", 12).execute();
    }
    /**
     * 普通查询
     */
    @Test
    public void testQuery() {
        flexMapper.select(col("*")).from("d1001").partitionBy("id","name").interval("10m").sLimit(10,20).one(Map.class);
    }
}
