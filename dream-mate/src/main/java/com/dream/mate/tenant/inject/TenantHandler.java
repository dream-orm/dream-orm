package com.dream.mate.tenant.inject;

import com.dream.system.config.MethodInfo;
import com.dream.system.table.TableInfo;

public interface TenantHandler {
    /**
     * 是否应用多租户
     *
     * @param methodInfo mapper方法详尽信息
     * @param tableInfo  主表详尽信息
     * @return
     */
    default boolean isTenant(MethodInfo methodInfo, TableInfo tableInfo) {
        return tableInfo.getFieldName(getTenantColumn()) != null;
    }

    /**
     * 返回应用的多租户字段
     *
     * @return
     */
    default String getTenantColumn() {
        return "tenant_id";
    }

    /**
     * 返回应用的多租户值
     *
     * @return
     */
    Object getTenantObject();
}
