package com.dream.template.validate;


import java.util.Map;

public class AssertFalseValidator implements Validator<Boolean> {
    @Override
    public void validate(Boolean value, Map<String, Object> paramMap) {
        if (value != null && value) {
            throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
        }
    }
}
