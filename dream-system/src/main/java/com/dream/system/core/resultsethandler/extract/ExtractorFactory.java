package com.dream.system.core.resultsethandler.extract;

import com.dream.system.table.ColumnInfo;

import java.lang.reflect.Field;

public interface ExtractorFactory {

    /**
     * 获取数据处理器
     *
     * @param columnInfo 表字段信息
     * @param field      java属性字段
     * @param property   映射的属性名
     * @return 数据处理器
     */
    Extractor getExtractor(ColumnInfo columnInfo, Field field, String property);
}
