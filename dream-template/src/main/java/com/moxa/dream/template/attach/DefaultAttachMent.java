package com.moxa.dream.template.attach;

import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.util.InvokerUtil;
import com.moxa.dream.template.mapper.AbstractMapper;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

public class DefaultAttachMent implements AttachMent {
    public String attachField;

    public DefaultAttachMent(String attachField) {
        if (ObjectUtil.isNull(attachField)) {
            throw new DreamRunTimeException("attachField不能为空");
        }
        this.attachField = attachField;
    }

    @Override
    public String attach(Configuration configuration, TableInfo tableInfo, Class<?> type, Command command) {
        if (command == Command.UPDATE || command == Command.DELETE) {
            String fieldName = tableInfo.getFieldName(attachField);
            if (fieldName != null) {
                ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                String column = tableInfo.getTable() + "." + columnInfo.getColumn();
                String invokerSQL = InvokerUtil.wrapperInvokerSQL(SystemInvokerFactory.NAMESPACE, SystemInvokerFactory.$, ",", AbstractMapper.DREAM_TEMPLATE_PARAM + "." + columnInfo.getName());
                return "and(" + column + " is null or " + column + "=" + invokerSQL + ")";
            }
        }
        return "";
    }
}
