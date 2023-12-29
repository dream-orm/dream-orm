package com.dream.helloworld.h2;

import com.dream.drive.factory.DefaultFlexDialect;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.dialect.FlexDialect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.dream.flex.def.FunctionDef.update;
import static com.dream.helloworld.h2.def.AccountDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexUpdateTest {
    FlexDialect flexDialect = new DefaultFlexDialect();

    @Test
    public void testUpdate() {
        UpdateDef updateDef = update(account).set(account.age, account.age.add(1)).set(account.name, "accountName").where(account.id.eq(1));
        SqlInfo sqlInfo = flexDialect.toSQL(updateDef);
        System.out.println(sqlInfo.getSql());
    }
}
