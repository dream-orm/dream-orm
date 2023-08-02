package com.moxa.dream.system.core.resultsethandler.extract;

import java.lang.reflect.Field;

public interface ExtractorFactory {
    Extractor getExtractor(Class viewType, Field field);
}
