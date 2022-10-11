package com.moxa.dream.system.config;

import com.moxa.dream.antlr.config.Command;

import java.util.Set;

public class MappedSql {
    private final Command command;
    private final String sql;
    private final Set<String> tableSet;

    public MappedSql(Command command, String sql, Set<String> tableSet) {
        this.command = command;
        this.sql = sql;
        this.tableSet = tableSet;
    }

    public Command getCommand() {
        return command;
    }

    public String getSql() {
        return sql;
    }

    public Set<String> getTableSet() {
        return tableSet;
    }
}
