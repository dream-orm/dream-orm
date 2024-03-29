package com.dream.helloworld.h2;

import com.dream.drive.factory.DefaultFlexDialect;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.InsertDef;
import com.dream.flex.dialect.FlexDialect;
import com.dream.helloworld.h2.table.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.dream.flex.def.FunctionDef.insertInto;
import static com.dream.helloworld.h2.def.AccountDef.account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexInsertTest {
    FlexDialect flexDialect = new DefaultFlexDialect();

    /**
     * 普通插入sql
     */
    @Test
    public void testInsert() {
        InsertDef insertDef = insertInto(account).columns(account.name, account.age).values("accountName", 12);
        SqlInfo sqlInfo = flexDialect.toSQL(insertDef);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 不带字段名插入
     */
    @Test
    public void testInsert2() {
        InsertDef insertDef = insertInto(account).values("accountName", 13);
        SqlInfo sqlInfo = flexDialect.toSQL(insertDef);
        System.out.println(sqlInfo.getSql());
    }

    /**
     * 批量插入
     */
    @Test
    public void testInsert3() {
        List<Account> accountList = new ArrayList<Account>();
        for (int i = 14; i < 20; i++) {
            Account account = new Account();
            account.setId(i);
            account.setName("name" + i);
            account.setAge(i * 2);
            accountList.add(account);
        }
        InsertDef insertDef = insertInto(account).columns(account.id, account.name, account.age).valuesList(accountList, acc -> {
            Account account = (Account) acc;
            return new Object[]{account.getId(), account.getName(), account.getAge()};
        });
        SqlInfo sqlInfo = flexDialect.toSQL(insertDef);
        System.out.println(sqlInfo.getSql());
    }

}
