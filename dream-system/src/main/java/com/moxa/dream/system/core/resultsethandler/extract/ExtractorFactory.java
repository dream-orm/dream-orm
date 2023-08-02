package com.moxa.dream.system.core.resultsethandler.extract;

import com.moxa.dream.system.table.ColumnInfo;

import java.lang.reflect.Field;

public interface ExtractorFactory {
    Extractor getExtractor(ColumnInfo columnInfo, Field field);
}
