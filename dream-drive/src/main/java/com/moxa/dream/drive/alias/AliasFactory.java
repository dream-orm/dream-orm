package com.moxa.dream.drive.alias;

import java.util.Properties;

public interface AliasFactory {

    void setProperties(Properties properties);

    String getValue(String value);
}
