package com.moxa.dream.drive.alias;

import com.moxa.dream.util.common.ObjectUtil;

import java.util.Properties;

public class DefaultAliasFactory extends AbstractAliasFactory {
    @Override
    public Properties getProp(Properties properties) {
        Properties cloneProperties = (Properties) this.properties.clone();
        if (!ObjectUtil.isNull(properties))
            cloneProperties.putAll(properties);
        return cloneProperties;
    }

}
