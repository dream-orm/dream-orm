package com.dream.template.validate;


import java.util.Map;
import java.util.regex.Pattern;

public class PatternValidator implements Validator<String> {
    @Override
    public String validate(String value, Map<String, Object> paramMap) {
        if (value != null) {
            String regex = (String) paramMap.get("regex");
            if (!Pattern.compile(regex).matcher(value).matches()) {
                return (String) paramMap.get("msg");
            }
        }
        return null;
    }
}
