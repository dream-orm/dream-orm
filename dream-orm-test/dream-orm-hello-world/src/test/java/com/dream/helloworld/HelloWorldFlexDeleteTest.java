package com.dream.helloworld;

import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.Delete;
import com.dream.flex.def.FunctionDef;
import static com.dream.flex.def.FunctionDef.delete;
import com.dream.helloworld.debug.FlexDebug;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dream.helloworld.table.table.AccountTableDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexDeleteTest {
    FlexDebug flexDebug = new FlexDebug();

    @Test
    public void testInsert() {
        Delete delete= delete(account).where(account.id.eq(1));
        SqlInfo sqlInfo = flexDebug.toSQL(delete);
        System.out.println(sqlInfo.getSql());
    }
}
