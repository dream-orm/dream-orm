package com.moxa.dream.template.validate;


import java.util.Map;

public class MaxValidator implements Validator<Double> {
    @Override
    public void validate(Double value, Map<String, Object> paramMap) {
        if (value != null) {
            Double max = (Double) paramMap.get("value");
            if (value > max) {
                throw new ValidateDreamRunTimeException((String) paramMap.get("msg"));
            }
        }
    }
}
