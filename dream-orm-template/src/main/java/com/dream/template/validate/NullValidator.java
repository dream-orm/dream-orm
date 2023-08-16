package com.dream.template.validate;


import java.util.Map;

public class NullValidator implements Validator<Object> {
    @Override
    public String validate(Object value, Map<String, Object> paramMap) {
        if (value != null) {
            return (String) paramMap.get("msg");
        }
        return null;
    }
}
