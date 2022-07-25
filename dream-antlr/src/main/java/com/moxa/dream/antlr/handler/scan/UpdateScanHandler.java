package com.moxa.dream.antlr.handler.scan;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class UpdateScanHandler extends AbstractHandler {
    private final ScanInvoker.ScanInfo scanInfo;

    public UpdateScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        if (Command.NONE == scanInfo.getCommand())
            scanInfo.setCommand(Command.UPDATE);
        UpdateStatement updateStatement = (UpdateStatement) statement;
        scanInfo.add(new ScanInvoker.TableScanInfo(null, updateStatement.getTable().getSymbol(), null, true));
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof UpdateStatement;
    }
}
