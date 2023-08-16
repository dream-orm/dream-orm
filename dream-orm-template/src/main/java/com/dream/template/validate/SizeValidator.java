package com.dream.template.validate;


import java.util.Collection;
import java.util.Map;

public class SizeValidator implements Validator<Object> {
    @Override
    public String validate(Object value, Map<String, Object> paramMap) {
        if (value != null) {
            Integer min = (Integer) paramMap.get("min");
            Integer max = (Integer) paramMap.get("max");
            int size;
            if (value instanceof Collection) {
                size = ((Collection<?>) value).size();
            } else if (value instanceof Map) {
                size = ((Map<?, ?>) value).size();
            } else {
                return value.getClass().getName() + "必须是集合或者map";
            }
            boolean error = min >= 0 && size < min || max >= 0 && size > max;
            if (error) {
                return (String) paramMap.get("msg");
            }
        }
        return null;
    }
}
