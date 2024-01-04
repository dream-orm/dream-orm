package com.dream.mate.share.strategy;

import com.dream.mate.share.annotation.Share;
import com.dream.mate.share.holder.DataSourceHolder;
import com.dream.system.config.MappedStatement;

import java.lang.reflect.Method;

public class DefaultShardStrategy implements ShardStrategy {
    @Override
    public String strategy(MappedStatement mappedStatement) {
        String dataSourceName = DataSourceHolder.get();
        if (dataSourceName == null) {
            Share share;
            Method method = mappedStatement.getMethod();
            if (method != null) {
                share = method.getAnnotation(Share.class);
                if (share == null) {
                    share = method.getDeclaringClass().getAnnotation(Share.class);
                }
                if (share != null) {
                    return share.value();
                }
            }
        }
        return null;
    }
}
