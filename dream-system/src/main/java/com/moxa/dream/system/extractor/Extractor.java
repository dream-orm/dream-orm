package com.moxa.dream.system.extractor;

import com.moxa.dream.util.reflection.factory.ObjectFactory;

public interface Extractor {
    void extract(String property, Object value, ObjectFactory objectFactory);
}
