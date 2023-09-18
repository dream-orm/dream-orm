package com.dream.template.validate;

import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.template.annotation.validate.NotExist;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Field;
import java.util.Map;

public class NotExistValidator implements Validator<Object> {
    private MethodInfo methodInfo;
    private Session session;

    @Override
    public boolean isValid(Session session, Class type, Field field, Command command) {
        if (command == Command.INSERT || command == Command.DELETE) {
            Configuration configuration = session.getConfiguration();
            TableFactory tableFactory = configuration.getTableFactory();
            NotExist notExist = field.getAnnotation(NotExist.class);
            String tableName = notExist.table();
            if (ObjectUtil.isNull(tableName)) {
                tableName = SystemUtil.getTableName(type);
                if (ObjectUtil.isNull(tableName)) {
                    throw new ValidatedRunTimeException("请指定表名，所在类：" + type.getName() + "，所在字段：" + field.getName());
                }
            }
            String column = notExist.column();
            if (ObjectUtil.isNull(column)) {
                column = field.getName();
            }
            TableInfo tableInfo = tableFactory.getTableInfo(tableName);
            if (tableInfo != null) {
                ColumnInfo columnInfo = tableInfo.getColumnInfo(column);
                if (columnInfo != null) {
                    column = columnInfo.getColumn();
                } else {
                    throw new DreamRunTimeException("数据表：" + tableName + "不存在字段" + column + "，所在类：" + type.getName());
                }
            }
            methodInfo = new MethodInfo();
            methodInfo.setConfiguration(configuration);
            methodInfo.setSql("select 1 from " + tableName + " where " + column + "=@?(v) limit 1");
            methodInfo.setRowType(NonCollection.class);
            methodInfo.setColType(Integer.class);
            this.session = session;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String validate(Object value, Map<String, Object> paramMap) {
        Object result = session.execute(methodInfo, new ObjectMap(value));
        if (result != null) {
            return (String) paramMap.get("msg");
        }
        return null;
    }
}
