package com.moxa.dream.antlr.handler.scan;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToNativeSQL;
import com.moxa.dream.antlr.sql.ToSQL;


public class QueryScanHandler extends AbstractHandler {
    private final FromScanHandler fromScanHandler = new FromScanHandler(this);
    private final JoinScanHandler joinScanHandler = new JoinScanHandler(this);
    private final ScanInvoker.ScanInfo scanInfo;
    private final ToSQL toSQL = new ToNativeSQL();

    public QueryScanHandler(ScanInvoker.ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws InvokerException {
        scanInfo.setCommand(Command.QUERY);
        return super.handlerAfter(statement, assist, sql, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{fromScanHandler, joinScanHandler};
    }

    public ScanInvoker.TableScanInfo getTableScanInfo(Statement statement, boolean master) throws InvokerException {
        String database = null;
        String table = null;
        String alias = null;
        if (statement instanceof AliasStatement) {
            AliasStatement aliasStatement = (AliasStatement) statement;
            statement = aliasStatement.getColumn();
            alias = toSQL.toStr(aliasStatement.getAlias(), null, null);
        }
        if (statement instanceof ListColumnStatement) {
            Statement[] columnList = ((ListColumnStatement) statement).getColumnList();
            database = ((SymbolStatement) columnList[columnList.length - 2]).getValue();
            table = ((SymbolStatement) columnList[columnList.length - 1]).getValue();
        } else if (statement instanceof SymbolStatement) {
            table = ((SymbolStatement) statement).getValue();
        }
        if (table != null) {
            return new ScanInvoker.TableScanInfo(database, table, alias, master);
        }
        return null;
    }

    public void scanStatement(Statement statement, boolean master) throws InvokerException {
        ScanInvoker.TableScanInfo tableScanInfo = getTableScanInfo(statement, master);
        if (tableScanInfo != null) {
            scanInfo.add(tableScanInfo);
        }
    }

    static class FromScanHandler extends AbstractHandler {
        private final QueryScanHandler queryScanHandler;

        public FromScanHandler(QueryScanHandler queryScanHandler) {
            this.queryScanHandler = queryScanHandler;
        }

        @Override
        protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws InvokerException {
            FromStatement fromStatement = (FromStatement) statement;
            queryScanHandler.scanStatement(fromStatement.getMainTable(), true);
            return super.handlerAfter(statement, assist, sql, life);
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof FromStatement;
        }
    }

    static class JoinScanHandler extends AbstractHandler {
        QueryScanHandler queryScanHandler;

        public JoinScanHandler(QueryScanHandler queryScanHandler) {
            this.queryScanHandler = queryScanHandler;
        }

        @Override
        protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws InvokerException {
            JoinStatement joinStatement = (JoinStatement) statement;
            queryScanHandler.scanStatement(joinStatement.getJoinTable(), false);
            return super.handlerAfter(statement, assist, sql, life);
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof JoinStatement;
        }
    }
}
