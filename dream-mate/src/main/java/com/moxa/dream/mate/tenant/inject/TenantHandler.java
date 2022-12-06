package com.moxa.dream.mate.tenant.inject;

import com.moxa.dream.system.config.MethodInfo;
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
