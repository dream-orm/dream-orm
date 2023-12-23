package com.dream.helloworld.db.tenant;

/**
 * 测试室获取租户id
 */
public class TenantUtil {
    public static String getTenantId() {
        return "tenant" + System.currentTimeMillis() % 3;
    }
}
