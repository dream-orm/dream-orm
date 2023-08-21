package com.dream.tdhelloworld;

import com.dream.tdengine.mapper.FlexTdChainMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.dream.flex.def.FunctionDef.col;
import static com.dream.flex.def.FunctionDef.max;
import static com.dream.tdengine.def.TdFunctionDef.first;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TdHelloWorldApplication.class)
public class HelloWorldTdTest {
    @Autowired
    private FlexTdChainMapper flexTdChainMapper;

    /**
     * 数据切分查询
     * @throws Exception
     */
    @Test
    public void testPartitionBy() throws Exception {
        List<Map> list = flexTdChainMapper.select(max(col("current"))).from("meters").partitionBy("location").interval("10m").limit(1, 100).list(Map.class);
        System.out.println("查询结果：" + list);
    }

    /**
     * 时间窗口查询
     * @throws Exception
     */
    @Test
    public void testSliding() throws Exception {
        List<Map> list = flexTdChainMapper.select(max(col("current"))).from("meters").partitionBy("location").interval("10m").sliding("5m").limit(1, 100).list(Map.class);
        System.out.println("查询结果：" + list);
    }

    /**
     * 测试状态窗口
     * @throws Exception
     */
    @Test
    public void testState() throws Exception {
        List<Map> list = flexTdChainMapper.select("voltage").from("meters").state_window("voltage").limit(1, 10).list(Map.class);
        System.out.println("查询结果：" + list);
    }
    /**
     * 测试会话窗口
     * @throws Exception
     */
    @Test
    public void testSession() throws Exception {
        List<Map> list = flexTdChainMapper.select(col("voltage"),first("ts")).from("meters").session("ts","10s").limit(1, 10).list(Map.class);
        System.out.println("查询结果：" + list);
    }
    /**
     * 测试插入
     */
    @Test
    public void testInsert(){
        flexTdChainMapper.insertInto("d1001").values(new Date(),10.2,219,0.32).execute();
    }
    /**
     * 测试插入多条
     */
    @Test
    public void testInsertMany(){
        List<Object[]>list=new ArrayList<>();
        list.add(new Object[]{new Date(),10.2,219,0.32});
        list.add(new Object[]{new Date(),11.2,219,0.32});
        flexTdChainMapper.insertInto("d1001").valuesList(list,o->(Object[])o ).execute();
    }
    /**
     * 测试插入并自动建表
     */
    @Test
    public void testInsertAndCreate(){
        flexTdChainMapper.insertInto("d2001").using("meters").tags(2,"abc").values(new Date(),10.2,219,0.32).execute();
    }
}
