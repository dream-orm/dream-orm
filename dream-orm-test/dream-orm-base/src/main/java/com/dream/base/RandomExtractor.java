package com.dream.base;

import com.dream.system.core.resultsethandler.extract.Extractor;
import com.dream.util.reflection.factory.ObjectFactory;

import java.util.UUID;

public class RandomExtractor implements Extractor {

    @Override
    public void extract(String property, Object value, ObjectFactory objectFactory) {
        objectFactory.set(property, UUID.randomUUID().toString());
    }
}
