package com.moxa.dream.template.util;

import com.moxa.dream.template.annotation.Conditional;
import com.moxa.dream.template.annotation.Sort;
import com.moxa.dream.template.condition.Condition;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ConditionUtil {
    public List<ConditionObject> getCondition(Class<?> type) {
        List<Field> fieldList = ReflectUtil.findField(type);
        if (!ObjectUtil.isNull(fieldList)) {
            List<ConditionObject> conditionObjectList = new ArrayList<>();
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(Conditional.class)) {
                    Conditional conditionalAnnotation = field.getAnnotation(Conditional.class);
                    String table = conditionalAnnotation.table();
                    boolean filterNull = conditionalAnnotation.filterNull();
                    boolean or = conditionalAnnotation.or();
                    Class<? extends Condition> conditionType = conditionalAnnotation.value();
                    conditionObjectList.add(new ConditionObject(table, field.getName(), filterNull,or, ReflectUtil.create(conditionType)));
                }
            }
            return conditionObjectList;
        }
        return null;
    }

    public List<SortObject> getSort(Class<?> type) {
        List<Field> fieldList = ReflectUtil.findField(type);
        if (!ObjectUtil.isNull(fieldList)) {
            List<SortObject> sortObjectList = new ArrayList<>();
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(Sort.class)) {
                    Sort sortAnnotation = field.getAnnotation(Sort.class);
                    sortObjectList.add(
                            new SortObject(
                                    sortAnnotation.table(),
                                    field.getName(),
                                    sortAnnotation.value().getOrderType(),
                                    sortAnnotation.order()
                            )
                    );
                }
            }
            if (!sortObjectList.isEmpty()) {
                Collections.sort(sortObjectList);
            }
            return sortObjectList;
        }
        return null;
    }

}
