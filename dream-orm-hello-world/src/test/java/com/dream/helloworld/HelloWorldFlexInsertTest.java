package com.dream.helloworld;

import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.Insert;
import com.dream.helloworld.debug.FlexDebug;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dream.flex.def.FunctionDef.insertInto;
import static com.dream.helloworld.table.table.AccountTableDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexInsertTest {
    FlexDebug flexDebug = new FlexDebug();

    @Test
    public void testInsert() {
        Insert insert = insertInto(account).columns(account.name, account.age).values("accountName", 12);
        SqlInfo sqlInfo = flexDebug.toSQL(insert);
        System.out.println(sqlInfo.getSql());
    }
}
