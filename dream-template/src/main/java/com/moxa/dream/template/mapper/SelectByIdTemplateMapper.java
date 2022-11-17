package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Collection;

public class SelectByIdTemplateMapper extends SelectTemplateMapper {
    public SelectByIdTemplateMapper(Session session) {
        super(session);
    }

    @Override
    protected String getSuffix(TableInfo tableInfo) {
        ColumnInfo columnInfo = tableInfo.getPrimColumnInfo();
        ObjectUtil.requireNonNull(columnInfo, "表'" + tableInfo.getTable() + "'未注册主键");
        return "where " + tableInfo.getTable() + "." + columnInfo.getColumn() + "=" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", columnInfo.getName());
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return NonCollection.class;
    }

}
