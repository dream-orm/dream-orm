package com.moxa.dream.driver.dialect;

import com.moxa.dream.driver.page.wrapper.PageWrapper;
import com.moxa.dream.system.antlr.wrapper.Wrapper;
import com.moxa.dream.system.dialect.AbstractDialectFactory;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractPageDialectFactory extends AbstractDialectFactory {
    @Override
    protected List<Wrapper> getWrapList() {
        return Arrays.asList(new PageWrapper());
    }
}
