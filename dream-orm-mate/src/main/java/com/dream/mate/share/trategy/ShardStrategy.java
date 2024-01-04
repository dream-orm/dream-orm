package com.dream.mate.share.trategy;

import com.dream.system.config.MethodInfo;

public interface ShardStrategy {
    String strategy(MethodInfo methodInfo,Class<?>type);
}
