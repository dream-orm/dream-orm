package com.moxa.dream.mate.permission.inject;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.table.TableInfo;

public interface PermissionHandler {
    boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo);

    String getPermission(MethodInfo methodInfo, TableInfo tableInfo, String alias);

}
