package com.dream.instruct.factory;

import com.dream.antlr.sql.ToSQL;
import com.dream.mate.logic.inject.LogicHandler;
import com.dream.mate.permission.inject.PermissionHandler;
import com.dream.mate.tenant.inject.TenantHandler;
import com.dream.system.typehandler.factory.TypeHandlerFactory;

public class DefaultCommandDialectFactory extends AbstractCommandDialectFactory {

    public DefaultCommandDialectFactory(ToSQL toSQL) {
        super(toSQL);
    }

    public DefaultCommandDialectFactory(TypeHandlerFactory typeHandlerFactory, ToSQL toSQL) {
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
