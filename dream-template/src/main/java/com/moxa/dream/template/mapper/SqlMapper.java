package com.moxa.dream.template.mapper;

public interface SqlMapper {
    Object execute(Class<?> type, Object arg);
}
