package com.dream.mate.share.strategy;

import com.dream.system.config.MappedStatement;

public interface ShardStrategy {
    String strategy(MappedStatement mappedStatement);
}
