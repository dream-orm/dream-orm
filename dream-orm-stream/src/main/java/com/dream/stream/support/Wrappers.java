package com.dream.stream.support;

import com.dream.stream.factory.DefaultStreamQueryFactory;
import com.dream.stream.wrapper.defaults.DefaultSelectWrapper;

public class Wrappers {
    public static <T> DefaultSelectWrapper<T> query(Class<T> entityType) {
        return new DefaultStreamQueryFactory().newSelectWrapper(entityType);
    }
}
