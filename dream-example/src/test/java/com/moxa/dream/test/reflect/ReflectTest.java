package com.moxa.dream.test.reflect;

import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ReflectTest {

    //动态增加参数
    @Test
    public void testAddParam() {
        Map<String, Object> map = new HashMap<>();
        MyObject myObject = new MyObject();
        myObject.setId(1);
        myObject.setName("object");
        map.put(null, myObject);
        ObjectWrapper wrapper = ObjectWrapper.wrapper(map);
        Object id = wrapper.get("id");
        Object name = wrapper.get("name");
        ObjectUtil.requireTrue(id.equals(1) && name.equals("object"), "");
    }
}
