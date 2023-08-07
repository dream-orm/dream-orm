package com.dream.system.inject;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.annotation.PageQuery;
import com.dream.system.antlr.invoker.LimitInvoker;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.antlr.invoker.OffSetInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.util.common.ObjectUtil;

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
