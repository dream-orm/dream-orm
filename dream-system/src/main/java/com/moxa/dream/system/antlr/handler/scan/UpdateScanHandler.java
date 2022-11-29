package com.moxa.dream.system.antlr.handler.scan;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.system.antlr.invoker.ScanInvoker;

public class UpdateScanHandler extends AbstractHandler {
    private final ScanInvoker.ScanInfo scanInfo;

    public UpdateScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        scanInfo.setCommand(Command.UPDATE);
        UpdateStatement updateStatement = (UpdateStatement) statement;
        scanInfo.add(new ScanInvoker.TableScanInfo(null, ((SymbolStatement) updateStatement.getTable()).getValue(), null, true));
        return super.handlerAfter(statement, assist, sql, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof UpdateStatement;
    }
}
