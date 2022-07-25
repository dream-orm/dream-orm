package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.scan.*;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.LowHashMap;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ScanInvoker extends AbstractInvoker {

    private final QueryScanHandler queryScanHandler;

    private final InsertScanHandler insertScanHandler;

    private final UpdateScanHandler updateScanHandler;

    private final DeleteScanHandler deleteScanHandler;

    private final ParamScanHandler paramScanHandler;

    private final InvokerScanHandler invokerScanHandler;

    private final ScanInfo scanInfo = new ScanInfo();

    public ScanInvoker() {
        queryScanHandler = new QueryScanHandler(scanInfo);
        insertScanHandler = new InsertScanHandler(scanInfo);
        updateScanHandler = new UpdateScanHandler(scanInfo);
        deleteScanHandler = new DeleteScanHandler(scanInfo);
        paramScanHandler = new ParamScanHandler(scanInfo);
        invokerScanHandler = new InvokerScanHandler(scanInfo);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
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
        List<InvokerStatement> invokerStatementList = scanInfo.getInvokerStatementList()
                .stream()
                .filter(invoker -> invoker.getParentStatement() != null)
                .collect(Collectors.toList());
        if (!ObjectUtil.isNull(invokerStatementList)) {
            int i = 0;
            for (; i < invokerStatementList.size(); i++) {
                InvokerStatement invoker = invokerStatementList.get(i);
                if (!invoker.getFunction().equals(AntlrInvokerFactory.$)) {
                    break;
                }
            }
            if (i == invokerStatementList.size()) {
                for (InvokerStatement invoker : invokerStatementList) {
                    invoker.setStatement(new SymbolStatement.MarkStatement());
                }
                $Invoker $invoker = ($Invoker) assist.getInvoker(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$);
                scanInfo.setParamInfoList($invoker.getParamInfoList());
            }
        }
        scanInfo.sql = sql;
        return sql;
    }


    @Override
    public Handler[] handler() {
        return new Handler[]{queryScanHandler, insertScanHandler, updateScanHandler, deleteScanHandler, paramScanHandler, invokerScanHandler};
    }

    public ScanInfo getScanInfo() {
        return scanInfo;
    }

    public static class ScanInfo {
        private final Map<String, TableScanInfo> tableScanInfoMap = new LowHashMap<>();
        private final Map<String, ParamScanInfo> paramScanInfoMap = new HashMap<>();
        private final List<InvokerStatement> invokerStatementList = new ArrayList<>();
        private Command command = Command.NONE;
        private List<$Invoker.ParamInfo> paramInfoList;
        private String sql;

        public void add(TableScanInfo tableScanInfo) {
            tableScanInfoMap.put(tableScanInfo.getTable(), tableScanInfo);
        }

        public void add(ParamScanInfo paramScanInfo) {
            paramScanInfoMap.put(paramScanInfo.getParam(), paramScanInfo);
        }

        public void add(InvokerStatement invokerStatement) {
            invokerStatementList.add(invokerStatement);
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

        public List<InvokerStatement> getInvokerStatementList() {
            return invokerStatementList;
        }

        public List<$Invoker.ParamInfo> getParamInfoList() {
            return paramInfoList;
        }

        public void setParamInfoList(List<$Invoker.ParamInfo> paramInfoList) {
            this.paramInfoList = paramInfoList;
        }

        public String getSql() {
            return sql;
        }
    }

    public static class TableScanInfo {
        private final String database;
        private final String table;
        private final String alias;
        private final boolean master;

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
        private final String database;
        private final String table;
        private final String column;
        private final String param;

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
