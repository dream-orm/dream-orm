package com.moxa.dream.driver.xml.util;

import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

public class XmlUtil {
    static Map<String, String> map = new HashMap<>();

    static {
        map.put("package", "type");
    }

    public static <T> T applyAttributes(Class<T> typeClass, Attributes attributes) {
        T target = ReflectUtil.create(typeClass);
        ObjectWrapper wrapper = ObjectWrapper.wrapper(target);
        for (int i = 0; i < attributes.getLength(); i++) {
            String qName = attributes.getQName(i);
            String name = map.getOrDefault(qName, qName);
            String value = attributes.getValue(i);
            wrapper.set(name, value);
        }
        return target;
    }
}
