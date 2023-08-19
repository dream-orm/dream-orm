package com.dream.mate.dynamic.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.dynamic.invoker.DynamicGetInvoker;
import com.dream.mate.permission.invoker.PermissionGetInvoker;
import com.dream.system.antlr.handler.scan.QueryScanHandler;
import com.dream.system.antlr.invoker.ScanInvoker;

import java.util.List;

public class DynamicQueryHandler extends AbstractHandler {

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{new DynamicFromHandler(), new DynamicJoinHandler()};
    }

    class DynamicFromHandler extends AbstractHandler {

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
            FromStatement fromStatement = (FromStatement) statement;
            ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(fromStatement.getMainTable(), true);
            if (tableScanInfo != null) {
                InvokerStatement invokerStatement = AntlrUtil.invokerStatement(
                        DynamicGetInvoker.FUNCTION,
                        Invoker.DEFAULT_NAMESPACE,
                        new SymbolStatement.SingleMarkStatement(tableScanInfo.getTable()));
                String alias = tableScanInfo.getAlias();
                AliasStatement aliasStatement = new AliasStatement();
                aliasStatement.setColumn(invokerStatement);
                aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
                fromStatement.setMainTable(aliasStatement);
            }
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof FromStatement;
        }
    }

    class DynamicJoinHandler extends AbstractHandler {
        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
            JoinStatement joinStatement = (JoinStatement) statement;
            ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(joinStatement.getJoinTable(), false);
            if (tableScanInfo != null) {
                InvokerStatement invokerStatement = AntlrUtil.invokerStatement(
                        PermissionGetInvoker.FUNCTION,
                        Invoker.DEFAULT_NAMESPACE,
                        new SymbolStatement.SingleMarkStatement(tableScanInfo.getTable()));
                String alias = tableScanInfo.getAlias();
                AliasStatement aliasStatement = new AliasStatement();
                aliasStatement.setColumn(invokerStatement);
                aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
                joinStatement.setJoinTable(aliasStatement);
            }
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof JoinStatement;
        }
    }
}
