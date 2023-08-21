package com.dream.tdhelloworld;

import com.dream.tdengine.mapper.FlexTdChainMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TdHelloWorldApplication.class)
public class HelloWorldTdTest {
    @Autowired
    private FlexTdChainMapper flexTdChainMapper;

    @Test
    public void test() throws Exception {
        List<Map> list = flexTdChainMapper.select("ts").from("d25").limit(1, 100).list(Map.class);
        System.out.println();
    }

}
