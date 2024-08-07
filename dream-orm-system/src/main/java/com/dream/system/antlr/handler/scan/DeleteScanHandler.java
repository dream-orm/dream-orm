package com.dream.system.antlr.handler.scan;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.smt.DeleteStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.system.antlr.invoker.ScanInvoker;

public class DeleteScanHandler extends AbstractHandler {
    private final ScanInvoker.ScanInfo scanInfo;

    public DeleteScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        scanInfo.setCommand("DELETE");
        DeleteStatement deleteStatement = (DeleteStatement) statement;
        Statement table = deleteStatement.getTable();
        if (table instanceof SymbolStatement) {
            scanInfo.add(new ScanInvoker.TableScanInfo(null, ((SymbolStatement) table).getValue(), null, true));
        }
        return super.handlerAfter(statement, assist, sql, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof DeleteStatement;
    }
}
