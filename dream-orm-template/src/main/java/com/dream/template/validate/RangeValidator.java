package com.dream.template.validate;


import java.util.Map;

public class RangeValidator implements Validator<Double> {
    @Override
    public String validate(Double value, Map<String, Object> paramMap) {
        if (value != null) {
            Double min = (Double) paramMap.get("min");
            Double max = (Double) paramMap.get("max");
            if (value < min || value > max) {
                return (String) paramMap.get("msg");
            }
        }
        return null;
    }
}
