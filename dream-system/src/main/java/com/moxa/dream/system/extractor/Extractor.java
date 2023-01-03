package com.moxa.dream.system.extractor;

import com.moxa.dream.system.config.MappedColumn;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

public interface Extractor {
    void extract(MappedStatement mappedStatement, MappedColumn mappedColumn, Object value, ObjectFactory objectFactory);
}
