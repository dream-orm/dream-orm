package com.dream.template.validate;


import java.util.Map;

public class MinValidator implements Validator<Double> {
    @Override
    public String validate(Double value, Map<String, Object> paramMap) {
        if (value != null) {
            Double min = (Double) paramMap.get("value");
            if (value < min) {
                return (String) paramMap.get("msg");
            }
        }
        return null;
    }
}
