package com.moxa.dream.template.validate;


import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;

import java.lang.reflect.Field;
import java.util.Map;

public interface Validator<T> {
    default boolean isValid(Configuration configuration, Class type, Field field, Command command) {
        return true;
    }
    void validate(T value, Map<String, Object> paramMap);
}
