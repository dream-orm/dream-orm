package com.moxa.dream.template.validate;


import com.moxa.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class SizeValidator implements Validator<Object> {
    @Override
    public void validate(Object value, Map<String, Object> paramMap) {
        if (value != null) {
            Integer min = (Integer) paramMap.get("min");
            Integer max = (Integer) paramMap.get("max");
            int size;
            if (value instanceof Collection) {
                size = ((Collection<?>) value).size();
            } else if (value instanceof Map) {
                size = ((Map<?, ?>) value).size();
            } else {
                try {
                    Method sizeMethod = value.getClass().getMethod("size");
                    size = (Integer) sizeMethod.invoke(value);
                } catch (Exception e) {
                    throw new DreamRunTimeException(e);
                }
            }
            if (min >= 0 && size < min || max >= 0 && size > max) {
                throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
            }
        }
    }
}
