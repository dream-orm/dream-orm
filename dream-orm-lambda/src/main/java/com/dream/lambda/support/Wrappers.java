package com.dream.lambda.support;

import com.dream.lambda.factory.DefaultQueryCreatorFactory;
import com.dream.lambda.wrapper.defaults.DefaultSelectWrapper;

public class Wrappers {
    public static DefaultSelectWrapper query() {
        return new DefaultQueryCreatorFactory().newSelectWrapper();
    }
}
