package com.dream.system.action;

import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Map;

public class EasyFetchActionProcessor implements ActionProcessor {
    private String fieldName;
    private MethodInfo methodInfo;

    @Override
    public void init(Field field, Map<String, Object> paramMap, Configuration configuration) {
        this.fieldName = field.getName();
        String table = (String) paramMap.get("table");
        String column = (String) paramMap.get("column");
        String[] columns = (String[]) paramMap.get("columns");
        String fieldName = (String) paramMap.get("field");
        if (ObjectUtil.isNull(table)) {
            table = SystemUtil.getTableName(field.getType());
            if (ObjectUtil.isNull(table)) {
                throw new DreamRunTimeException("字段" + field.getName() + "类型没有绑定表名");
            }
        }
        this.methodInfo = new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(ReflectUtil.getRowType(field.getGenericType()))
                .setColType(ReflectUtil.getColType(field.getGenericType()))
                .setSql("select " + (ObjectUtil.isNull(columns) ? "@*()" : String.join(",", columns)) + " from " + table + " where " + column + "=@?(row." + fieldName + ")");
    }

    @Override
    public void loop(Object row, MappedStatement mappedStatement, Session session) {
        if (row != null) {
            Object result = session.execute(methodInfo, new ObjectMap(row));
            if (result != null) {
                ObjectWrapper wrapper = ObjectWrapper.wrapper(row);
                wrapper.set(fieldName, result);
            }
        }
    }
}
