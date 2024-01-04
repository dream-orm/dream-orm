package com.dream.mate.share.session;

import com.dream.mate.share.holder.DataSourceHolder;
import com.dream.mate.share.trategy.DefaultShardStrategy;
import com.dream.mate.share.trategy.ShardStrategy;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.mapper.MapperInvoke;

import java.util.Map;

public class ShareMapperInvoke implements MapperInvoke {
    private Session session;
    private ShardStrategy shardStrategy;

    public ShareMapperInvoke(Session session) {
        this(session, new DefaultShardStrategy());
    }

    public ShareMapperInvoke(Session session, ShardStrategy shardStrategy) {
        this.session = session;
        this.shardStrategy = shardStrategy;
    }

    @Override
    public Object invoke(MethodInfo methodInfo, Map<String, Object> argMap, Class<?> type) {
        String dataSourceName = shardStrategy.strategy(methodInfo, type);
        if (dataSourceName != null) {
            return DataSourceHolder.use(dataSourceName, () -> session.execute(methodInfo, argMap));
        } else {
            return session.execute(methodInfo, argMap);
        }
    }
}
