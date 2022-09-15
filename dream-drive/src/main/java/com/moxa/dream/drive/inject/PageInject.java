package com.moxa.dream.drive.inject;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.drive.antlr.factory.DriveInvokerFactory;
import com.moxa.dream.drive.page.PageQuery;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

public class PageInject implements Inject {
    private final String START_ROW = "startRow";
    private final String PAGE_SIZE = "pageSize";

    @Override
    public void inject(MethodInfo methodInfo) {
        PageQuery pageQuery = methodInfo.get(PageQuery.class);
        if (pageQuery != null) {
            PackageStatement statement = methodInfo.getStatement();
            String pageNamespace;
            String pageFunction;
            pageNamespace = DriveInvokerFactory.NAMESPACE;
            if (pageQuery.offset()) {
                pageFunction = DriveInvokerFactory.$OFFSET;
            } else {
                pageFunction = DriveInvokerFactory.$LIMIT;
            }
            String value = pageQuery.value();
            String prefix = ObjectUtil.isNull(value) ? "" : (value + ".");
            String startRow = prefix + START_ROW;
            String pageSize = prefix + PAGE_SIZE;
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
}
