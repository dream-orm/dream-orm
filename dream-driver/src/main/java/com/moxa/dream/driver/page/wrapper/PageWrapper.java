package com.moxa.dream.driver.page.wrapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.driver.page.annotation.PageQuery;
import com.moxa.dream.module.antlr.factory.DreamInvokerFactory;
import com.moxa.dream.module.antlr.wrapper.Wrapper;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Method;

public class PageWrapper implements Wrapper {
    private PageQuery pageQueryAnnotation;

    @Override
    public void wrapper(PackageStatement statement, MethodInfo methodInfo) {
        if (isPage(methodInfo)) {
            String pageNamespace;
            String pageFunction;
            pageNamespace = DreamInvokerFactory.NAMESPACE;
            if (isOffSet(methodInfo)) {
                pageFunction = DreamInvokerFactory.$OFFSET;
            } else {
                pageFunction = DreamInvokerFactory.$LIMIT;
            }
            String pageLink = getPageLink(methodInfo);
            String startRow = getStartRow(pageLink, methodInfo);
            String pageSize = getPageSize(pageLink, methodInfo);
            ObjectUtil.requireNonNull(startRow, "Property 'startRow' is required");
            ObjectUtil.requireNonNull(pageSize, "Property 'pageSize' is required");
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
            methodInfo.set(PageLink.class, new PageLink(pageLink));
        }
    }

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

    protected boolean isPage(MethodInfo methodInfo) {
        Method method = methodInfo.getMethod();
        if (method == null)
            return false;
        pageQueryAnnotation = method.getDeclaredAnnotation(PageQuery.class);
        return pageQueryAnnotation != null;
    }

    protected String getPageLink(MethodInfo methodInfo) {
        return pageQueryAnnotation.value();
    }

    protected boolean isOffSet(MethodInfo methodInfo) {
        return pageQueryAnnotation.offset();
    }

    public static class PageLink {
        String pageLink;

        public PageLink(String pageLink) {
            this.pageLink = pageLink;
        }

        public String getPageLink() {
            return pageLink;
        }
    }
}
