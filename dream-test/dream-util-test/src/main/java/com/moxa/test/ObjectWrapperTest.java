package com.moxa.test;

import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectWrapperTest {
    @Test
    public void testBaseicTest() {
        int a = 10;
        ObjectWrapper wrapper = ObjectWrapper.wrapper(a);
        Object a1 = wrapper.get(null);
        if ((int) a1 != a) {
            throw new DreamRunTimeException("error");
        }
        if (null != wrapper.get("a")) {
            throw new DreamRunTimeException("error");
        }
    }

    @Test
    public void testCollectionTest() {
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Map map = new HashMap<>(1);
        map.put("list", list);
        ObjectWrapper wrapper = ObjectWrapper.wrapper(map);
        Object o = wrapper.get("list.1");
        if ((int) o != 2) {
            throw new DreamRunTimeException("error");
        }
        if (null != wrapper.get("list1.1")) {
            throw new DreamRunTimeException("error");
        }
    }

}
