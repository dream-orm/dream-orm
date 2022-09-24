package com.moxa.dream.mate.tenant.interceptor;

import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.table.TableInfo;

public interface TenantHandler {
    default boolean isTenant(MethodInfo methodInfo, TableInfo tableInfo) {
        return tableInfo.getFieldName(getTenantColumn()) != null;
    }

    default String getTenantColumn() {
        return "tenant_id";
    }

    Object getTenantObject();
}
