package com.moxa.dream.system.extractor;

import com.moxa.dream.util.reflection.factory.ObjectFactory;

import java.lang.reflect.Field;

/**
 * 注入java字段值后的操作类
 */
public interface Extractor {
    /**
     * 初始化，可根据字段属性获取自定义注解参数，完成逻辑开发
     * @param field 字段属性
     */
    default void init(Field field){

    }

    /**
     * 自定义处理方法
     *
     * @param property      java字段名
     * @param value         SQL查询值
     * @param objectFactory 反射工厂
     */
    void extract(String property, Object value, ObjectFactory objectFactory);
}
