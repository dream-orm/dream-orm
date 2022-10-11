package com.moxa.dream.template.util;

import java.util.List;
import java.util.Set;

public class TemplateUtil {
    private static TableUtil tableUtil = new TableUtil();
    private static ConditionUtil conditionUtil = new ConditionUtil();

    public static String getTable(Class<?> type) {
        return tableUtil.getTable(type);
    }

    public static Set<String> getTableSet(Class<?> type) {
        return tableUtil.getTableSet(type);
    }

    public static List<ConditionObject> getCondition(Class<?> type) {
        return conditionUtil.getCondition(type);
    }

    public static List<SortObject> getSort(Class<?> type) {
        return conditionUtil.getSort(type);
    }
}
