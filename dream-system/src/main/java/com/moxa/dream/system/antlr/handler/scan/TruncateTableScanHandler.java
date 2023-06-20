package com.moxa.dream.system.antlr.handler.scan;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.smt.TruncateTableStatement;
import com.moxa.dream.system.antlr.invoker.ScanInvoker;

public class TruncateTableScanHandler extends AbstractHandler {
    private final ScanInvoker.ScanInfo scanInfo;

    public TruncateTableScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        scanInfo.setCommand(Command.TRUNCATE);
        TruncateTableStatement truncateTableStatement = (TruncateTableStatement) statement;
        scanInfo.add(new ScanInvoker.TableScanInfo(null, ((SymbolStatement) truncateTableStatement.getTable()).getValue(), null, true));
        return super.handlerAfter(statement, assist, sql, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof TruncateTableStatement;
    }
}
