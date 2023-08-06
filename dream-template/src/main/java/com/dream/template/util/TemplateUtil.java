package com.dream.template.util;

import java.util.List;
import java.util.Set;

public class TemplateUtil {
    private static TableUtil tableUtil = new TableUtil();
    private static ConditionUtil conditionUtil = new ConditionUtil();

    public static Set<String> getTableNameSet(Class<?> type) {
        return tableUtil.getTableNameSet(type);
    }

    public static List<ConditionObject> getCondition(Class<?> type) {
        return conditionUtil.getCondition(type);
    }

    public static List<SortObject> getSort(Class<?> type) {
        return conditionUtil.getSort(type);
    }
}
