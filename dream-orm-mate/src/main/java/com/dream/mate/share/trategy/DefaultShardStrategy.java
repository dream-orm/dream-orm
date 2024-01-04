package com.dream.mate.share.trategy;

import com.dream.mate.share.annotation.Share;
import com.dream.mate.share.holder.DataSourceHolder;
import com.dream.system.config.MethodInfo;

import java.lang.reflect.Method;

public class DefaultShardStrategy implements ShardStrategy {
    @Override
    public String strategy(MethodInfo methodInfo, Class<?> type) {
        String dataSourceName = DataSourceHolder.get();
        if (dataSourceName == null) {
            Share share = null;
            Method method = methodInfo.getMethod();
            if (method != null) {
                share = method.getAnnotation(Share.class);
            }
            if (share == null) {
                share = type.getAnnotation(Share.class);
            }
            if (share != null) {
                return share.value();
            }
        }
        return null;
    }
}
