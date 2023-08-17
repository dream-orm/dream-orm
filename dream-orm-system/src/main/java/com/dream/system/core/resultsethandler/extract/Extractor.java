package com.dream.system.core.resultsethandler.extract;

import com.dream.util.reflection.factory.ObjectFactory;

/**
 * 映射拦截
 */
public interface Extractor {
    /**
     * 自定义处理方法
     *
     * @param property      字段名
     * @param value         SQL查询值
     * @param objectFactory 反射工厂
     */
    void extract(String property, Object value, ObjectFactory objectFactory);
}
