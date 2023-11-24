package com.dream.template.mapper;

import com.dream.system.core.session.Session;
import com.dream.system.table.factory.TableFactory;
import com.dream.util.common.NonCollection;

import java.util.Collection;

public class ExistMapper extends SelectListMapper {
    public ExistMapper(Session session) {
        super(session);
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return NonCollection.class;
    }

    @Override
    protected Class getColType(Class type) {
        return Integer.class;
    }

    @Override
    protected String getSelectColumn(Class<?> type) {
        return "1";
    }

    @Override
    protected String getOrderSql(Class type, String tableName, TableFactory tableFactory) {
        return " limit 1";
    }
}
