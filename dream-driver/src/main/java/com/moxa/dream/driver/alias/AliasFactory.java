package com.moxa.dream.driver.alias;

import java.util.Properties;

public interface AliasFactory {

    void setProperties(Properties properties);

    String getValue(String value);
}
