package com.dream.mate.share.listener;

import com.dream.mate.share.holder.DataSourceHolder;
import com.dream.mate.share.strategy.DefaultShardStrategy;
import com.dream.mate.share.strategy.ShardStrategy;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.listener.Listener;

public class ShardListener implements Listener {
    private ShardStrategy shardStrategy;

    public ShardListener() {
        this(new DefaultShardStrategy());
    }

    public ShardListener(ShardStrategy shardStrategy) {
        this.shardStrategy = shardStrategy;
    }

    @Override
    public void before(MappedStatement mappedStatement) {
        String strategy = shardStrategy.strategy(mappedStatement);
        if (strategy != null) {
            DataSourceHolder.set(strategy);
        }
    }

    @Override
    public void afterReturn(Object result, MappedStatement mappedStatement) {
        DataSourceHolder.remove();
    }

    @Override
    public void exception(Throwable e, MappedStatement mappedStatement) {
        DataSourceHolder.remove();
    }
}
