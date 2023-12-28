package com.dream.helloworld.h2;

import com.dream.flex.config.SqlInfo;
import com.dream.flex.debug.FlexDebug;
import com.dream.flex.def.DeleteDef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dream.flex.def.FunctionDef.delete;
import static com.dream.helloworld.h2.def.AccountDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexDeleteTest {
    FlexDebug flexDebug = new FlexDebug();

    @Test
    public void testDelete() {
        DeleteDef deleteDef = delete(account).where(account.id.eq(1));
        SqlInfo sqlInfo = flexDebug.toSQL(deleteDef);
        System.out.println(sqlInfo.getSql());
    }
}
