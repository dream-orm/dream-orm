package com.dream.mate.share.strategy;

import com.dream.system.config.MappedStatement;

public interface ShardStrategy {
    /**
     * 数据源分片
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @return 分片的数据源名称
     */
    String strategy(MappedStatement mappedStatement);
}
