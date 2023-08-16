package com.dream.template.validate;


import java.util.Map;

public class AssertFalseValidator implements Validator<Boolean> {
    @Override
    public String validate(Boolean value, Map<String, Object> paramMap) {
        if (value != null && value) {
            return (String) paramMap.get("msg");
        }
        return null;
    }
}
