package com.moxa.dream.system.inject;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.annotation.PageQuery;
import com.moxa.dream.system.antlr.invoker.LimitInvoker;
import com.moxa.dream.system.antlr.invoker.MarkInvoker;
import com.moxa.dream.system.antlr.invoker.OffSetInvoker;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

public class PageInject implements Inject {
    private final String START_ROW = "startRow";
    private final String PAGE_SIZE = "pageSize";
    private boolean offset;

    public PageInject(boolean offset) {
        this.offset = offset;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        PageQuery pageQuery = methodInfo.get(PageQuery.class);
        if (pageQuery != null) {
            PackageStatement statement = methodInfo.getStatement();
            String value = pageQuery.value();
            String prefix = ObjectUtil.isNull(value) ? "" : (value + ".");
            String startRow = prefix + START_ROW;
            String pageSize = prefix + PAGE_SIZE;
            InvokerStatement pageStatement;
            if (offset) {
                pageStatement = AntlrUtil.invokerStatement(
                        OffSetInvoker.FUNCTION,
                        Invoker.DEFAULT_NAMESPACE,
                        statement.getStatement(),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(pageSize)),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(startRow)));
            } else {
                pageStatement = AntlrUtil.invokerStatement(
                        LimitInvoker.FUNCTION,
                        Invoker.DEFAULT_NAMESPACE,
                        statement.getStatement(),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(startRow)),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(pageSize)));
            }
            statement.setStatement(pageStatement);
        }
    }

    public boolean isOffset() {
        return offset;
    }
}
