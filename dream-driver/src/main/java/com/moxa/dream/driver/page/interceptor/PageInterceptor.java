package com.moxa.dream.driver.page.interceptor;

import com.moxa.dream.driver.page.Page;
import com.moxa.dream.driver.page.annotation.PageQuery;
import com.moxa.dream.module.antlr.handler.PageHandler;
import com.moxa.dream.module.core.executor.Executor;
import com.moxa.dream.module.dialect.DialectFactory;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.plugin.PluginException;
import com.moxa.dream.module.plugin.interceptor.AbstractInterceptor;
import com.moxa.dream.module.plugin.invocation.Invocation;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PageInterceptor extends AbstractInterceptor {
    @Override
    public Object interceptor(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        PageQuery pageQuery = mappedStatement.get(PageQuery.class);
        if (pageQuery != null) {
            String link = pageQuery.value();
            PageHandler.PageCount pageCount = mappedStatement.get(PageHandler.PageCount.class);
            if (pageCount != null) {
                Object arg = mappedStatement.getArg();
                if (arg != null) {
                    Object target;
                    if (ObjectUtil.isNull(link)) {
                        target = arg;
                    } else {
                        target = ObjectWrapper.wrapper(arg).get(link);
                    }
                    if (target != null && target instanceof Page) {
                        Page page = (Page) target;
                        if (page.isCount()) {
                            Executor targetExecutor = (Executor) invocation.getTarget();
                            MethodInfo methodInfo = pageCount.getMethodInfo();
                            DialectFactory dialectFactory = mappedStatement.getConfiguration().getDialectFactory();
                            MappedStatement countMappedStatement = dialectFactory.compile(methodInfo, mappedStatement.getArg());
                            Long total = (Long) targetExecutor.query(countMappedStatement);
                            page.setTotal(total);
                        }
                        Class<? extends Collection> rowType = mappedStatement.getRowType();
                        if (Page.class == rowType) {
                            mappedStatement.setRowType(List.class);
                            page.setRow((Collection) invocation.proceed());
                            return page;
                        } else {
                            return invocation.proceed();
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Set<Method> methodSet() {
        try {
            Method method = Executor.class.getDeclaredMethod("query", MappedStatement.class);
            return Set.of(method);
        } catch (Exception e) {
            throw new PluginException(e);
        }
    }
}
