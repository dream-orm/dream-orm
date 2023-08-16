package com.dream.template.validate;


import java.util.Map;

public class LengthValidator implements Validator<Object> {
    @Override
    public String validate(Object value, Map<String, Object> paramMap) {
        if (value != null) {
            Integer min = (Integer) paramMap.get("min");
            Integer max = (Integer) paramMap.get("max");
            int length;
            if (value instanceof String) {
                length = ((String) value).length();
            } else {
                return value.getClass().getName() + "必须是字符串类型";
            }
            boolean error = min >= 0 && length < min || max >= 0 && length > max;
            if (error) {
                return (String) paramMap.get("msg");
            }
        }
        return null;
    }
}
