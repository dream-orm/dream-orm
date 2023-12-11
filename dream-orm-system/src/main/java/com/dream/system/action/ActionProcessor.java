package com.dream.system.action;

import java.util.Map;

public interface ActionProcessor {
    void process(Object row, String fieldName, Map<String, Object> paramMap);
}
