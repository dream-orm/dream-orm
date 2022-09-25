package com.moxa.dream.mate.permission.interceptor;

import com.moxa.dream.system.plugin.interceptor.Interceptor;
import com.moxa.dream.system.plugin.invocation.Invocation;

import java.lang.reflect.Method;
import java.util.Set;

public class PermissionInterceptor implements Interceptor {
    private PermissionHandler permissionHandler;

    public PermissionInterceptor(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public Object interceptor(Invocation invocation) throws Throwable {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Method> methods() throws Exception {
        return null;
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }
}
