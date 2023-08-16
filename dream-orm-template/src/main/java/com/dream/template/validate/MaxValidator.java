package com.dream.template.validate;


import java.util.Map;

public class MaxValidator implements Validator<Double> {
    @Override
    public String validate(Double value, Map<String, Object> paramMap) {
        if (value != null) {
            Double max = (Double) paramMap.get("value");
            if (value > max) {
                return (String) paramMap.get("msg");
            }
        }
        return null;
    }
}
