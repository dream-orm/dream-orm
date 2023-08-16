package com.dream.template.validate;


import java.util.Map;

public class NotBlankValidator implements Validator<String> {
    @Override
    public String validate(String value, Map<String, Object> paramMap) {
        if (value == null || value.trim().length() == 0) {
            return (String) paramMap.get("msg");
        }
        return null;
    }
}
