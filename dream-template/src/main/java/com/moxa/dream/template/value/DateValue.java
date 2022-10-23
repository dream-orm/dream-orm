package com.moxa.dream.template.value;

import java.util.Date;

public class DateValue implements Value {
    @Override
    public Object getValue() {
        return new Date();
    }
}
