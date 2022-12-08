package com.moxa.dream.template.attach;

import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.table.TableInfo;

public interface AttachMent {
    public String attach(Configuration configuration, TableInfo tableInfo, Class<?> type, Command command);
}
