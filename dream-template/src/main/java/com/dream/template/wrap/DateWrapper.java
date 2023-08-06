package com.dream.template.wrap;

import java.util.Date;

public class DateWrapper implements Wrapper {
    @Override
    public Object wrap(Object value) {
        if (value == null) {
            return new Date();
        } else {
            return value;
        }
    }
}
