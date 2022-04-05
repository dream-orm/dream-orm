package com.moxa.dream.driver.factory;

import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.driver.page.decoration.PageDecoration;
import com.moxa.dream.module.antlr.decoration.Decoration;
import com.moxa.dream.module.dialect.AbstractDialectFactory;
import com.moxa.dream.module.mapper.MethodInfo;

import java.util.List;
import java.util.Map;

public class DefaultDialectFactory extends AbstractDialectFactory {

    @Override
    protected <T> Map<Class<? extends T>, T> getCustomMap(MethodInfo methodInfo, Object arg) {
        return null;
    }

    @Override
    protected MyFunctionFactory getMyFunctionFactory() {
        return null;
    }

    @Override
    protected List<Decoration> getWrapList() {
        return List.of(new PageDecoration());
    }
}
