package com.dream.wrap.support;

import com.dream.wrap.factory.DefaultWrapQueryFactory;
import com.dream.wrap.wrapper.defaults.DefaultSelectWrapper;

public class Wrappers {
    public static DefaultSelectWrapper query(Class<?> entityType) {
        return new DefaultWrapQueryFactory().newSelectWrapper(entityType);
    }
}
