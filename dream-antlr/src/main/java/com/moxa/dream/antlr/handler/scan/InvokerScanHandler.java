package com.moxa.dream.antlr.handler.scan;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class InvokerScanHandler extends AbstractHandler {
    private ScanInvoker.ScanInfo scanInfo;

    public InvokerScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        scanInfo.add((InvokerStatement) statement);
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof InvokerStatement;
    }
}
