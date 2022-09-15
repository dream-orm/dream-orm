package com.moxa.dream.drive.inject;

import com.moxa.dream.system.inject.factory.AbstractInjectFactory;
import com.moxa.dream.system.mapped.MethodInfo;

public class PageInjectFactory extends AbstractInjectFactory {

    @Override
    protected void beforeInject(MethodInfo methodInfo) {
        PageInject pageInject = new PageInject();
        pageInject.inject(methodInfo);
    }
}
