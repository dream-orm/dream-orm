package com.dream.system.antlr.handler.scan;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.Statement;
import com.dream.system.antlr.invoker.ScanInvoker;

public class InvokerScanHandler extends AbstractHandler {
    private final ScanInvoker.ScanInfo scanInfo;

    public InvokerScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        if (statement.getParentStatement() != null) {
            scanInfo.add((InvokerStatement) statement);
        }
        return super.handlerAfter(statement, assist, sql, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof InvokerStatement;
    }
}
