package com.moxa.dream;

import com.moxa.dream.base.RandomExtractor;
import com.moxa.dream.system.core.resultsethandler.extract.Extractor;
import com.moxa.dream.system.core.resultsethandler.extract.ExtractorFactory;
import com.moxa.dream.system.table.ColumnInfo;

import java.lang.reflect.Field;

public class MyExtractorFactory implements ExtractorFactory {

    @Override
    public Extractor getExtractor(ColumnInfo columnInfo, Field field) {
        if(columnInfo!=null){
        }
        if(field!=null) {
            if ("name".equals(field.getName())) {
                return new RandomExtractor();
            }
        }
        return null;
    }
}
