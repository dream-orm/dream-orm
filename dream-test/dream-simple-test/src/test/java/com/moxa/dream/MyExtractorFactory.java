package com.moxa.dream;

import com.moxa.dream.base.RandomExtractor;
import com.moxa.dream.system.core.resultsethandler.extract.Extractor;
import com.moxa.dream.system.core.resultsethandler.extract.ExtractorFactory;

import java.lang.reflect.Field;

public class MyExtractorFactory implements ExtractorFactory {
    @Override
    public Extractor getExtractor(Class viewType, Field field) {
        if ("name".equals(field.getName())) {
            return new RandomExtractor();
        }
        return null;
    }
}
