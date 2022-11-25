package com.moxa.dream.sql.mock;


import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;

import java.util.Collection;

public class MockMethodInfo extends MethodInfo {

    public MockMethodInfo(Configuration configuration, String sql, Command command, boolean cache, int timeOut, Class<? extends Collection> rowType, Class<?> colType) {
        this.configuration = configuration;
        this.sql = sql;
        this.methodKey = new MockCacheKey(sql);
        this.command = command;
        this.cache = cache;
        this.timeOut = timeOut;
        this.rowType = rowType;
        this.colType = colType;
        this.compile = true;
    }

    @Override
    public synchronized void compile() {

    }
}
