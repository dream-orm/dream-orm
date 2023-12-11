package com.dream.helloworld.h2;

import com.dream.system.action.ActionProcessor;
import com.dream.util.common.ObjectWrapper;

import java.util.Map;

public class MaskProcessor implements ActionProcessor {
    @Override
    public void process(Object row, String fieldName, Map<String, Object> paramMap) {
        ObjectWrapper.wrapper(row).set(fieldName,"hello world");
    }
}
