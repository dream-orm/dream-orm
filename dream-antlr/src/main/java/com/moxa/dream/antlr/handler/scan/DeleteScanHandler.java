package com.moxa.dream.antlr.handler.scan;

import com.moxa.dream.antlr.bind.Command;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class DeleteScanHandler extends AbstractHandler {
    private ScanInvoker.ScanInfo scanInfo;

    public DeleteScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        if (Command.NONE == scanInfo.getCommand())
            scanInfo.setCommand(Command.UPDATE);
        DeleteStatement deleteStatement = (DeleteStatement) statement;
        scanInfo.add(new ScanInvoker.TableScanInfo(null, deleteStatement.getTable().getSymbol(), null, true));
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof DeleteStatement;
    }
}