package com.moxa.dream.module.antlr.wrapper;

import com.moxa.dream.module.annotation.AutoPage;
import com.moxa.dream.module.mapper.MethodInfo;

import java.lang.reflect.Method;

public class DefaultPageWrapper extends AbstractPageWrapper {
    private AutoPage autoPageAnnotation;

    @Override
    protected boolean isPage(MethodInfo methodInfo) {
        Method method = methodInfo.getMethod();
        if (method == null)
            return false;
        autoPageAnnotation = method.getDeclaredAnnotation(AutoPage.class);
        return autoPageAnnotation != null;
    }

    @Override
    protected String getPageLink(MethodInfo methodInfo) {
        return autoPageAnnotation.value();
    }

    @Override
    protected boolean isOffSet(MethodInfo methodInfo) {
        return autoPageAnnotation.offset();
    }

    @Override
    protected boolean isOptim(MethodInfo methodInfo) {
        return autoPageAnnotation.optim();
    }
}
