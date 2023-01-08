package com.moxa.dream.template.validate;


import com.moxa.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map;

public class LengthValidator implements Validator<Object> {
    @Override
    public void validate(Object value, Map<String, Object> paramMap) {
        if (value != null) {
            Integer min = (Integer) paramMap.get("min");
            Integer max = (Integer) paramMap.get("max");
            int length;
            if (value instanceof String) {
                length = ((String) value).length();
            } else if (value.getClass().isArray()) {
                length = Array.getLength(value);
            } else {
                try {
                    Method sizeMethod = value.getClass().getMethod("length");
                    length = (Integer) sizeMethod.invoke(value);
                } catch (Exception e) {
                    throw new DreamRunTimeException(e);
                }
            }
            if (min >= 0 && length < min || max >= 0 && length > max) {
                throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
            }
        }
    }
}
