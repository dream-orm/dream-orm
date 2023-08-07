package com.dream.template.validate;


import java.util.Map;

public class NotBlankValidator implements Validator<String> {
    @Override
    public void validate(String value, Map<String, Object> paramMap) {
        if (value == null || value.trim().length() == 0) {
            throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
        }
    }
}
