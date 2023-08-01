package com.moxa.dream.system.core.resultsethandler;

import com.moxa.dream.util.reflection.factory.ObjectFactory;

/**
 * 注入java字段值后的操作类
 */
public interface Extractor {
    /**
     * 自定义处理方法
     *
     * @param property      java字段名
     * @param value         SQL查询值
     * @param objectFactory 反射工厂
     */
    void extract(String property, Object value, ObjectFactory objectFactory);
}
