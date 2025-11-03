package com.dream.system.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.LimitInvoker;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.antlr.invoker.OffSetInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.util.common.ObjectUtil;

public class PageInject implements Inject {
    private final boolean offset;

    public PageInject(boolean offset) {
        this.offset = offset;
    }

    @Override
    public void inject(MethodInfo methodInfo) {
        String page = methodInfo.getPage();
        if (page != null) {
            InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
            PackageStatement statement = methodInfo.getStatement();
            String prefix = ObjectUtil.isNull(page) ? "" : (page + ".");
            String startRow = prefix + "startRow";
            String pageSize = prefix + "pageSize";
            InvokerStatement pageStatement;
            if (offset) {
                if (invokerFactory.getInvoker(OffSetInvoker.FUNCTION) == null) {
                    invokerFactory.addInvokers(new OffSetInvoker());
                }
                pageStatement = AntlrUtil.invokerStatement(
                        OffSetInvoker.FUNCTION,
                        statement.getStatement(),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, new SymbolStatement.LetterStatement(pageSize)),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, new SymbolStatement.LetterStatement(startRow)));
            } else {
                if (invokerFactory.getInvoker(LimitInvoker.FUNCTION) == null) {
                    invokerFactory.addInvokers(new LimitInvoker());
                }
                pageStatement = AntlrUtil.invokerStatement(
                        LimitInvoker.FUNCTION,
                        statement.getStatement(),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, new SymbolStatement.LetterStatement(startRow)),
                        AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, new SymbolStatement.LetterStatement(pageSize)));
            }
            statement.setStatement(pageStatement);
        }
    }

    public boolean isOffset() {
        return offset;
    }
}
