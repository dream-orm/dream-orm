package com.dream.system.antlr.handler.scan;

import com.dream.antlr.config.Assist;
import com.dream.antlr.config.Command;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.smt.UpdateStatement;
import com.dream.system.antlr.invoker.ScanInvoker;

public class UpdateScanHandler extends AbstractHandler {
    private final ScanInvoker.ScanInfo scanInfo;

    public UpdateScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        scanInfo.setCommand(Command.UPDATE);
        UpdateStatement updateStatement = (UpdateStatement) statement;
        Statement table = updateStatement.getTable();
        if (table instanceof SymbolStatement) {
            scanInfo.add(new ScanInvoker.TableScanInfo(null, ((SymbolStatement) table).getValue(), null, true));
        }
        return super.handlerAfter(statement, assist, sql, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof UpdateStatement;
    }
}
