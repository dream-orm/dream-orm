package com.dream.mate.permission.inject;

import com.dream.system.config.MethodInfo;

public interface PermissionHandler {

    /**
     * 判断是否应用数据权限
     *
     * @param methodInfo mapper方法详尽信息
     * @param table      主表
     * @return
     */
    boolean isPermissionInject(MethodInfo methodInfo, String table);

    /**
     * 获取数据权限SQL
     *
     * @param table 主表名称
     * @param alias 主表别名
     * @return
     */
    String getPermission(String table, String alias);
}
