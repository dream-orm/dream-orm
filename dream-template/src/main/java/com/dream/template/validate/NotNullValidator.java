package com.dream.template.validate;


import java.util.Map;

public class NotNullValidator implements Validator<Object> {
    @Override
    public void validate(Object value, Map<String, Object> paramMap) {
        if (value == null) {
            throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
        }
    }
}
