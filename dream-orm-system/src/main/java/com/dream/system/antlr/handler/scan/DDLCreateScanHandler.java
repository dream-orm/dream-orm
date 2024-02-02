package com.dream.system.antlr.handler.scan;

import com.dream.antlr.config.Assist;
import com.dream.antlr.config.Command;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.smt.DDLCreateStatement;
import com.dream.antlr.smt.Statement;
import com.dream.system.antlr.invoker.ScanInvoker;

public class DDLCreateScanHandler extends AbstractHandler {
    private final ScanInvoker.ScanInfo scanInfo;

    public DDLCreateScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        scanInfo.setCommand(Command.CREATE);
        return super.handlerAfter(statement, assist, sql, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof DDLCreateStatement;
    }
}
