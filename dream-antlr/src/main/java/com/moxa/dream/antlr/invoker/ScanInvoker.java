package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.bind.Command;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.scan.*;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.LowHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScanInvoker extends AbstractInvoker {

    private QueryScanHandler queryScanHandler;

    private InsertScanHandler insertScanHandler;

    private UpdateScanHandler updateScanHandler;

    private DeleteScanHandler deleteScanHandler;

    private ParamScanHandler paramScanHandler;

    private ScanInfo scanInfo = new ScanInfo();

    public ScanInvoker() {
        queryScanHandler = new QueryScanHandler(scanInfo);
        insertScanHandler = new InsertScanHandler(scanInfo);
        updateScanHandler = new UpdateScanHandler(scanInfo);
        deleteScanHandler = new DeleteScanHandler(scanInfo);
        paramScanHandler = new ParamScanHandler(scanInfo);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList.length != 1)
            throw new InvokerException("参数个数错误,不满足@" + AntlrInvokerFactory.SCAN + ":" + AntlrInvokerFactory.NAMESPACE + "(crud)");
        String sql = toSQL.toStr(columnList[0], assist, invokerList);
        Statement parentStatement = invokerStatement.getParentStatement();
        while (!(parentStatement instanceof PackageStatement)
                || (parentStatement instanceof PackageStatement && parentStatement.getParentStatement() != null)) {
            parentStatement = parentStatement.getParentStatement();
        }
        PackageStatement packageStatement = (PackageStatement) parentStatement;
        packageStatement.setValue(ScanInfo.class, scanInfo);
        invokerStatement.setStatement(columnList[0]);
        return sql;
    }


    @Override
    public Handler[] handler() {
        return new Handler[]{queryScanHandler, insertScanHandler, updateScanHandler, deleteScanHandler, paramScanHandler};
    }

    public ScanInfo getScanInfo() {
        return scanInfo;
    }

    public static class ScanInfo {
        private Command command = Command.NONE;
        private Map<String, TableScanInfo> tableScanInfoMap = new LowHashMap<>();
        private Map<String, ParamScanInfo> paramScanInfoMap = new HashMap<>();

        public boolean contains(TableScanInfo tableScanInfo) {
            if (tableScanInfoMap.containsKey(tableScanInfo.getAlias()))
                return true;
            else
                return false;
        }

        public boolean contains(ParamScanInfo paramScanInfo) {
            if (paramScanInfoMap.containsKey(paramScanInfo.getParam()))
                return true;
            else return false;
        }

        public void add(TableScanInfo tableScanInfo) {
            if (contains(tableScanInfo)) {
                return;
            }
            tableScanInfoMap.put(tableScanInfo.getAlias(), tableScanInfo);
        }

        public void add(ParamScanInfo paramScanInfo) {
            if (contains(paramScanInfo)) {
                return;
            }
            paramScanInfoMap.put(paramScanInfo.getParam(), paramScanInfo);
        }

        public Command getCommand() {
            return command;
        }

        public void setCommand(Command command) {
            this.command = command;
        }

        public Map<String, TableScanInfo> getTableScanInfoMap() {
            return tableScanInfoMap;
        }


        public Map<String, ParamScanInfo> getParamScanInfoMap() {
            return paramScanInfoMap;
        }
    }

    public static class TableScanInfo {
        private String database;
        private String table;
        private String alias;
        private boolean master;

        public TableScanInfo(String database, String table, String alias, boolean master) {
            if (alias == null)
                alias = table;
            this.database = database;
            this.table = table;
            this.alias = alias;
            this.master = master;
        }

        public String getDatabase() {
            return database;
        }

        public String getTable() {
            return table;
        }

        public String getAlias() {
            return alias;
        }

        public boolean isMaster() {
            return master;
        }

    }

    public static class ParamScanInfo {
        private String database;
        private String table;
        private String column;
        private String param;

        public ParamScanInfo(String database, String table, String column, String param) {
            this.database = database;
            this.table = table;
            this.column = column;
            this.param = param;
        }

        public String getDatabase() {
            return database;
        }

        public String getTable() {
            return table;
        }

        public String getColumn() {
            return column;
        }

        public String getParam() {
            return param;
        }

    }
}