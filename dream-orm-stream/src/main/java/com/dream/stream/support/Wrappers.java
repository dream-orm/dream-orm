package com.dream.stream.support;

import com.dream.stream.factory.DefaultStreamQueryFactory;
import com.dream.stream.wrapper.defaults.DefaultSelectWrapper;

public class Wrappers {
    public static DefaultSelectWrapper query(Class<?> entityType) {
        return new DefaultStreamQueryFactory().newSelectWrapper(entityType);
    }
}
