package com.dream.template.validate;

import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectMap;

import java.lang.reflect.Field;
import java.util.Map;

public class UniqueValidator implements Validator<Object> {
    private MethodInfo methodInfo;
    private Session session;

    @Override
    public boolean isValid(Session session, Class type, Field field, Command command) {
        if (command == Command.INSERT) {
            Configuration configuration = session.getConfiguration();
            TableFactory tableFactory = configuration.getTableFactory();
            String tableName = SystemUtil.getTableName(type);
            String fieldName = field.getName();
            TableInfo tableInfo = tableFactory.getTableInfo(tableName);
            ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
            String column = columnInfo.getColumn();
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
    public void validate(Object value, Map<String, Object> paramMap) {
        Object result = session.execute(methodInfo, new ObjectMap(value));
        if (result != null) {
            throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
        }
    }
}
