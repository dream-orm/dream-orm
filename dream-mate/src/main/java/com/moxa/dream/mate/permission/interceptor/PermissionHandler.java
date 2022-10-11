package com.moxa.dream.mate.permission.interceptor;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.table.TableInfo;

public interface PermissionHandler {
    boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo, int life);

    String getPermission(MethodInfo methodInfo, TableInfo tableInfo, String alias);

}
