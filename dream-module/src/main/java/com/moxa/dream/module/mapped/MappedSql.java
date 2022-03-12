package com.moxa.dream.module.mapped;

import com.moxa.dream.antlr.bind.Command;
import com.moxa.dream.antlr.invoker.ScanInvoker;

import java.util.Map;

public class MappedSql {
    private Command command;
    private String sql;
    private Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap;

    public MappedSql(Command command, String sql, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap) {
        this.command = command;
        this.sql = sql;
        this.tableScanInfoMap = tableScanInfoMap;
    }

    public Command getCommand() {
        return command;
    }

    public String getSql() {
        return sql;
    }

    public Map<String, ScanInvoker.TableScanInfo> getTableScanInfoMap() {
        return tableScanInfoMap;
    }
}
