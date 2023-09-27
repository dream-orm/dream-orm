package com.dream.system.core.resultsethandler.extract;

import java.lang.reflect.Field;

public interface ExtractorFactory {

    /**
     * 获取数据处理器
     *
     * @param type     java类
     * @param field    java属性字段
     * @param property 映射的属性名
     * @return 数据处理器
     */
    Extractor getExtractor(Class type, Field field, String property);
}
