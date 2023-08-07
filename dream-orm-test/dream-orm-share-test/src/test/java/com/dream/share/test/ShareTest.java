package com.dream.share.test;

import com.dream.share.ShareApplication;
import com.dream.share.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShareApplication.class)
public class ShareTest {
    @Autowired
    private UserService userService;

    @Test
    public void test() {
        userService.share();
    }

    @Test
    public void test1() {
        userService.share2();
    }

}
