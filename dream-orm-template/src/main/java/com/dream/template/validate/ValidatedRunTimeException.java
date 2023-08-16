package com.dream.template.validate;

import com.dream.util.exception.DreamRunTimeException;

public class ValidatedRunTimeException extends DreamRunTimeException {
    public ValidatedRunTimeException(String msg) {
        super(msg);
    }
}
