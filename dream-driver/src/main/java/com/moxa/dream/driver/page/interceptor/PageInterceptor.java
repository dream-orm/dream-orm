package com.moxa.dream.driver.page.interceptor;

import com.moxa.dream.driver.page.Page;
import com.moxa.dream.driver.page.annotation.PageQuery;
import com.moxa.dream.system.antlr.handler.PageHandler;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.plugin.interceptor.AbstractInterceptor;
import com.moxa.dream.system.plugin.invocation.Invocation;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
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
                            long total = ((Number) targetExecutor.query(countMappedStatement)).longValue();
                            page.setTotal(total);
                        }
                        return invocation.proceed();
                    } else {
                        throw new RuntimeException(Page.class.getName() + " not found");
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
            return new HashSet<>(Arrays.asList(method));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
