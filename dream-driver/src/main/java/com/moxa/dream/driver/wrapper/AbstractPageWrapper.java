package com.moxa.dream.driver.wrapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.driver.factory.DefaultInvokerFactory;
import com.moxa.dream.driver.interceptor.PageInterceptor;
import com.moxa.dream.module.wrapper.Wrapper;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

public abstract class AbstractPageWrapper implements Wrapper {
    @Override
    public void wrapper(PackageStatement statement, MethodInfo methodInfo) {
        if (isPage(methodInfo)) {
            String pageNamespace;
            String pageFunction;
            if (isOptim(methodInfo)) {
                pageNamespace = DefaultInvokerFactory.NAMESPACE;
                if (isOffSet(methodInfo)) {
                    pageFunction = DefaultInvokerFactory.$OFFSET;
                } else {
                    pageFunction = DefaultInvokerFactory.$LIMIT;
                }
            } else {
                pageNamespace = AntlrInvokerFactory.NAMESPACE;
                if (isOffSet(methodInfo)) {
                    pageFunction = AntlrInvokerFactory.OFFSET;
                } else {
                    pageFunction = AntlrInvokerFactory.LIMIT;
                }
            }
            String pageLink = getPageLink(methodInfo);
            String startRow = getStartRow(pageLink, methodInfo);
            String pageSize = getPageSize(pageLink, methodInfo);
            ObjectUtil.requireNonNull(startRow, "Property 'startRow' is required");
            ObjectUtil.requireNonNull(pageSize, "Property 'pageSize' is required");
            PageInterceptor.PageCount pageCount = new PageInterceptor.PageCount();
            pageCount.setPageLink(pageLink);
            methodInfo.set(PageInterceptor.PageCount.class, pageCount);
            InvokerStatement pageStatement = InvokerUtil.wrapperInvoker(pageNamespace,
                    pageFunction, ",",
                    statement.getStatement(),
                    InvokerUtil.wrapperInvoker(AntlrInvokerFactory.NAMESPACE,
                            AntlrInvokerFactory.$, ",",
                            new SymbolStatement.LetterStatement(startRow)),
                    InvokerUtil.wrapperInvoker(AntlrInvokerFactory.NAMESPACE,
                            AntlrInvokerFactory.$, ",",
                            new SymbolStatement.LetterStatement(pageSize)));
            statement.setStatement(pageStatement);
        }
    }

    protected abstract boolean isPage(MethodInfo methodInfo);

    protected String getStartRow(String pageLink, MethodInfo methodInfo) {
        String startRow = "startRow";
        if (!ObjectUtil.isNull(pageLink)) {
            startRow = pageLink + "." + startRow;
        }
        return startRow;
    }

    protected String getPageSize(String pageLink, MethodInfo methodInfo) {
        String pageSize = "pageSize";
        if (!ObjectUtil.isNull(pageLink)) {
            pageSize = pageLink + "." + pageSize;
        }
        return pageSize;
    }

    protected abstract String getPageLink(MethodInfo methodInfo);

    protected abstract boolean isOffSet(MethodInfo methodInfo);

    protected abstract boolean isOptim(MethodInfo methodInfo);
}
