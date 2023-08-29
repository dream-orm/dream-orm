package com.dream.tdengine.mapper;

public interface FlexTdMapper extends FlexTdChainMapper {
    int insert(String subTableName, Object entity);
}
