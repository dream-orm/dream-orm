package com.moxa.dream.system.config;

import java.util.Set;

public class MappedSql {
    private final Command command;
    private final String sql;
    private final Set<String> tableSet;

    public MappedSql(String command, String sql, Set<String> tableSet) {
        this.command = Enum.valueOf(Command.class, command);
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
