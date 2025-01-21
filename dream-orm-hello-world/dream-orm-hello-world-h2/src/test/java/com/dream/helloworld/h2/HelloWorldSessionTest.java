package com.dream.helloworld.h2;


import com.dream.helloworld.h2.table.Account;
import com.dream.system.config.Page;
import com.dream.system.core.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldSessionTest {

    @Autowired
    private Session session;

    @Test
    public void testSelectOne() {
        Account account = new Account();
        account.setId(1);
        Account _account = session.selectOne("select @*() from account where id=@?(id)", account, Account.class);
        System.out.println(_account);
    }

    @Test
    public void testSelectList() {
        Account account = new Account();
        account.setId(1);
        List<Account> accountList = session.selectList("select @*() from account where id<>@?(id)", account, Account.class);
        System.out.println(accountList);
    }

    @Test
    public void testSelectPage() {
        Account account = new Account();
        account.setId(1);
        Page<Account> page = session.selectPage("select @*() from account where id<>@?(id)", account, new Page<>(1, 2), Account.class);
        System.out.println(page);
    }

    @Test
    public void execute() {
        Account account = new Account();
        account.setId(1);
        int res = session.execute("update account set name='a' where id=@?(id)", account);
        System.out.println(res);
    }
}
