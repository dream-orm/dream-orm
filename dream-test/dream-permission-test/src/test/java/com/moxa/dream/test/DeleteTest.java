package com.moxa.dream.test;

import com.moxa.dream.BootApplication;
import com.moxa.dream.base.table.User;
import com.moxa.dream.template.mapper.TemplateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class DeleteTest {
    @Autowired
    private TemplateMapper templateMapper;

    @Test
    public void deleteById() {
        templateMapper.deleteById(User.class, 1);
    }

    @Test
    public void deleteByIds() {
        templateMapper.deleteByIds(User.class, Arrays.asList(1, 2, 3));
    }

    @Test
    public void deleteById2() {
        templateMapper.deleteByIds(User.class, Arrays.asList(1, 2, 3, 4, 5, 6));
    }
}
