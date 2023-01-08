package com.moxa.dream.template.validate;


import java.util.Map;

public class MinValidator implements Validator<Double> {
    @Override
    public void validate(Double value, Map<String, Object> paramMap) {
        if (value != null) {
            Double min = (Double) paramMap.get("value");
            if (value < min) {
                throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
            }
        }
    }
}
