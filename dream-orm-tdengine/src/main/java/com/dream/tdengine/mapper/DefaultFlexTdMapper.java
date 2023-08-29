package com.dream.tdengine.mapper;

import com.dream.antlr.sql.ToSQL;
import com.dream.system.annotation.Column;
import com.dream.system.annotation.Table;
import com.dream.system.core.session.Session;
import com.dream.system.util.SystemUtil;
import com.dream.tdengine.annotation.Tag;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DefaultFlexTdMapper extends DefaultFlexTdChainMapper implements FlexTdMapper{
    public DefaultFlexTdMapper(Session session) {
        super(session);
    }

    public DefaultFlexTdMapper(Session session, ToSQL toSQL) {
        super(session, toSQL);
    }

    @Override
    public int insert(String subTableName, Object entity) {
        if(ObjectUtil.isNull(subTableName)){
            throw new DreamRunTimeException("参数'subTableName'不能为空");
        }
        if(entity==null){
            throw new DreamRunTimeException("参数'entity'不能为空");
        }
        Class<?> tableClass = entity.getClass();
        String tableName = getTable(tableClass);
        List<Field> fieldList = ReflectUtil.findField(entity.getClass());
        List<Object>tagList=new ArrayList<>();
        List<Object>valueList=new ArrayList<>();
        for(Field field:fieldList){
            if(isTag(field)){
                if(ObjectUtil.isNull(tableName)){
                    throw new DreamRunTimeException("存在'tag'超级表不能为空");
                }
                tagList.add(getValue(field,entity));
            }else if(isColumn(field)){
                valueList.add(getValue(field,entity));
            }
        }
        if (ObjectUtil.isNull(tableName)) {
            return insertInto(subTableName).values(valueList.toArray()).execute();
        }else{
            return insertInto(subTableName).using(tableName).tags(tagList.toArray()).values(valueList.toArray()).execute();
        }
    }

    protected String getTable(Class<?> tableClass) {
        Table tableAnnotation = tableClass.getDeclaredAnnotation(Table.class);
        if (tableAnnotation == null) {
            return null;
        }
        String table = tableAnnotation.value();
        if(ObjectUtil.isNull(table)){
            table=SystemUtil.camelToUnderline(tableClass.getSimpleName());
        }
        return table;
    }

    protected boolean isTag(Field field){
        return field.isAnnotationPresent(Tag.class);
    }

    protected boolean isColumn(Field field){
        return field.isAnnotationPresent(Column.class);
    }

    protected Object getValue(Field field,Object entity){
        field.setAccessible(true);
        try {
            return field.get(entity);
        }catch (Exception e){
            throw new DreamRunTimeException(e);
        }
    }
}
