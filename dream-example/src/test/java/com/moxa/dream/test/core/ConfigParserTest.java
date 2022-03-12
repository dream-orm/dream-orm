package com.moxa.dream.test.core;

import com.moxa.dream.driver.alias.AliasFactory;
import com.moxa.dream.driver.alias.DefaultAliasFactory;

import java.util.Properties;

public class ConfigParserTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("a", "a0");
        properties.put("a0c", "b");
        properties.put("b", "a0cb");
        AliasFactory aliasFactory = new DefaultAliasFactory();
        aliasFactory.setProperties(properties);
        String value = aliasFactory.getValue("abc");
        if (!"abc".equals(value))
            throw new RuntimeException("error");
        value = aliasFactory.getValue("${a}");
        if (!"a0".equals(value))
            throw new RuntimeException("error");
        value = aliasFactory.getValue("${${a}c}g${${a}c}g${${a}c}g");
        if (!"bgbgbg".equals(value))
            throw new RuntimeException("error");


    }
}
