package com.dream.template.wrap;

public class ZeroWrapper implements Wrapper {
    @Override
    public Object wrap(Object value) {
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }
}
