package com.moxa.dream.template.wrap;

public class OneWrapper implements Wrapper {
    @Override
    public Object wrap(Object value) {
        if (value == null) {
            return 1;
        } else {
            return value;
        }
    }
}
