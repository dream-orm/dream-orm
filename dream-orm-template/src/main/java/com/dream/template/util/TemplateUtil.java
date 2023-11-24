package com.dream.template.util;

import java.util.List;

public class TemplateUtil {
    private static ConditionUtil conditionUtil = new ConditionUtil();

    public static List<ConditionObject> getCondition(Class<?> type) {
        return conditionUtil.getCondition(type);
    }

    public static List<SortObject> getSort(Class<?> type) {
        return conditionUtil.getSort(type);
    }
}
