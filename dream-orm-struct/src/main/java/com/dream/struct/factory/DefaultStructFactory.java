package com.dream.struct.factory;

import com.dream.antlr.sql.ToSQL;
import com.dream.mate.logic.inject.LogicHandler;
import com.dream.mate.permission.inject.PermissionHandler;
import com.dream.mate.tenant.inject.TenantHandler;
import com.dream.system.typehandler.factory.TypeHandlerFactory;

public class DefaultStructFactory extends AbstractStructFactory {

    public DefaultStructFactory(ToSQL toSQL) {
        super(toSQL);
    }

    public DefaultStructFactory(TypeHandlerFactory typeHandlerFactory, ToSQL toSQL) {
        super(typeHandlerFactory, toSQL);
    }

    @Override
    protected TenantHandler tenantHandler() {
        return null;
    }

    @Override
    protected PermissionHandler permissionHandler() {
        return null;
    }

    @Override
    protected LogicHandler logicHandler() {
        return null;
    }
}
