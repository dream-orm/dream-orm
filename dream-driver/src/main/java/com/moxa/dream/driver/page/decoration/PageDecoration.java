package com.moxa.dream.driver.page.decoration;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.driver.page.annotation.PageQuery;
import com.moxa.dream.system.antlr.decoration.Decoration;
import com.moxa.dream.system.antlr.factory.ModuleInvokerFactory;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

public class PageDecoration implements Decoration {
    public static final String START_ROW = "startRow";
    public static final String PAGE_SIZE = "pageSize";

    @Override
    public void decorate(MethodInfo methodInfo) {
        PageQuery pageQuery = methodInfo.get(PageQuery.class);
        if (pageQuery != null) {
            String pageNamespace;
            String pageFunction;
            pageNamespace = ModuleInvokerFactory.NAMESPACE;
            if (pageQuery.offset()) {
                pageFunction = ModuleInvokerFactory.$OFFSET;
            } else {
                pageFunction = ModuleInvokerFactory.$LIMIT;
            }
            String value = pageQuery.value();
            String prefix = ObjectUtil.isNull(value) ? "" : (value + ".");
            String startRow = prefix + START_ROW;
            String pageSize = prefix + PAGE_SIZE;
            PackageStatement statement = methodInfo.getStatement();
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
