package com.moxa.dream.driver.dialect;

import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.driver.page.wrapper.PageWrapper;
import com.moxa.dream.system.antlr.wrapper.Wrapper;
import com.moxa.dream.system.dialect.AbstractDialectFactory;
import com.moxa.dream.system.mapper.MethodInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AbstractPageDialectFactory extends AbstractDialectFactory {

    @Override
    protected <T> Map<Class<? extends T>, T> getCustomMap(MethodInfo methodInfo, Object arg) {
        return null;
    }

    @Override
    protected MyFunctionFactory getMyFunctionFactory() {
        return null;
    }

    @Override
    protected List<Wrapper> getWrapList() {
        return Arrays.asList(new PageWrapper());
    }
}
