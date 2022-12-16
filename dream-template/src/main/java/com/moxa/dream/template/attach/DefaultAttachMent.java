package com.moxa.dream.template.attach;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.$Invoker;
import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.mapper.AbstractMapper;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.List;

public class DefaultAttachMent implements AttachMent {
    public String attachColumn;

    public DefaultAttachMent(String attachColumn) {
        if (ObjectUtil.isNull(attachColumn)) {
            throw new DreamRunTimeException("attachField不能为空");
        }
        this.attachColumn = attachColumn;
    }

    @Override
    public String attach(Configuration configuration, TableInfo tableInfo, Class<?> type, Command command) {
        if ((command == Command.UPDATE || command == Command.DELETE) && type != null && !ReflectUtil.isBaseClass(type)) {
            String fieldName = tableInfo.getFieldName(attachColumn);
            if (fieldName != null) {
                List<Field> fieldList = ReflectUtil.findField(type);
                if (!ObjectUtil.isNull(fieldList)) {
                    for (Field field : fieldList) {
                        if (field.getName().equals(fieldName)) {
                            ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                            String column = tableInfo.getTable() + "." + columnInfo.getColumn();
                            String invokerSQL = AntlrUtil.invokerSQL($Invoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, AbstractMapper.DREAM_TEMPLATE_PARAM + "." + columnInfo.getName());
                            return "and(" + column + " is null or " + column + "=" + invokerSQL + ")";
                        }
                    }
                }

            }
        }
        return "";
    }
}
