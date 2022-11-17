package com.moxa.dream.system.extractor;

import com.moxa.dream.system.config.MappedColumn;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

public interface Extractor {
    void extract(MappedColumn mappedColumn, Object value, ObjectFactory objectFactory);
}
