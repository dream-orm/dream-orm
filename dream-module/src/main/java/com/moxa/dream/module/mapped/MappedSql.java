package com.moxa.dream.module.mapped;

import com.moxa.dream.antlr.bind.Command;

import java.util.Set;

public class MappedSql {
    private Command command;
    private String sql;
    private Set<String> tableSet;

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
