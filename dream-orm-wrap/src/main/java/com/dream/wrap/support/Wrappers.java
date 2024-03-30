package com.dream.wrap.support;

import com.dream.wrap.factory.DefaultQueryCreatorFactory;
import com.dream.wrap.wrapper.defaults.DefaultSelectWrapper;

public class Wrappers {
    public static DefaultSelectWrapper query(Class<?> entityType) {
        return new DefaultQueryCreatorFactory().newSelectWrapper(entityType);
    }
}
