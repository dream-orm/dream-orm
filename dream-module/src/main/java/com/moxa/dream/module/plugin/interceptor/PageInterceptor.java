package com.moxa.dream.module.plugin.interceptor;

import com.moxa.dream.module.core.executor.Executor;
import com.moxa.dream.module.dialect.DialectFactory;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.plugin.Page;
import com.moxa.dream.module.plugin.PluginException;
import com.moxa.dream.module.plugin.invocation.Invocation;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PageInterceptor extends AbstractInterceptor {
    @Override
    public Object interceptor(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        PageCount pageCount = mappedStatement.get(PageCount.class);
        if (pageCount != null) {
            String pageLink = pageCount.getPageLink();
            if (!ObjectUtil.isNull(pageLink)) {
                Object arg = mappedStatement.getArg();
                if (arg != null) {
                    Object value = ObjectWrapper.wrapper(arg).get(pageLink);
                    if (value != null && value instanceof Page) {
                        Page page = (Page) value;
                        mappedStatement.setRowType(ArrayList.class);
                        if (page.isCount()) {
                            Executor targetExecutor = (Executor) invocation.getTarget();
                            MethodInfo methodInfo = pageCount.getMethodInfo();
                            DialectFactory dialectFactory = mappedStatement.getConfiguration().getDialectFactory();
                            MappedStatement countMappedStatement = dialectFactory.compile(methodInfo, mappedStatement.getArg());
                            Long total = (Long) targetExecutor.query(countMappedStatement);
                            page.setTotal(total);
                        }
                        page.setRow((List) invocation.proceed());
                        return page;
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

    public static class PageCount {
        private String pageLink;
        private MethodInfo methodInfo;

        public String getPageLink() {
            return pageLink;
        }

        public void setPageLink(String pageLink) {
            this.pageLink = pageLink;
        }

        public MethodInfo getMethodInfo() {
            return methodInfo;
        }

        public void setMethodInfo(MethodInfo methodInfo) {
            this.methodInfo = methodInfo;
        }


    }
}
