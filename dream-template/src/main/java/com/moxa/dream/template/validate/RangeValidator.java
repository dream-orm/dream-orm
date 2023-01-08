package com.moxa.dream.template.validate;


import java.util.Map;

public class RangeValidator implements Validator<Double> {
    @Override
    public void validate(Double value, Map<String, Object> paramMap) {
        if (value != null) {
            Double min = (Double) paramMap.get("min");
            Double max = (Double) paramMap.get("max");
            if (value < min || value > max) {
                throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
            }
        }
    }
}
