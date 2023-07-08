package com.moxa.dream.base;

import com.moxa.dream.system.extractor.Extractor;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

import java.util.UUID;

public class RandomExtractor implements Extractor {

    @Override
    public void extract(String property, Object value, ObjectFactory objectFactory) {
        objectFactory.set(property, UUID.randomUUID().toString());
    }
}
