package com.dream.mate.share.session;

import com.dream.mate.share.trategy.ShardStrategy;
import com.dream.system.core.session.Session;
import com.dream.system.mapper.MapperInvoke;
import com.dream.system.mapper.MapperInvokeFactory;

public class ShareMapperInvokerFactory implements MapperInvokeFactory {
    private ShardStrategy shardStrategy;

    public ShareMapperInvokerFactory(ShardStrategy shardStrategy) {
        this.shardStrategy = shardStrategy;
    }

    @Override
    public MapperInvoke getMapperInvoke(Session session) {
        return new ShareMapperInvoke(session, shardStrategy);
    }
}
