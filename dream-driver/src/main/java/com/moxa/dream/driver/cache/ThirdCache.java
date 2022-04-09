package com.moxa.dream.driver.cache;

import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ThreadUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThirdCache implements com.moxa.dream.system.cache.Cache {
    protected Map<String, Set<CacheKey>> tableMap = new ConcurrentHashMap<>();
    protected Map<CacheKey, Set<CacheKey>> indexMap = new ConcurrentHashMap<>();
    protected Cache<CacheKey, Object> pointCache;
    private int limit;
    private double rate;
    private AtomicBoolean canDelete = new AtomicBoolean(true);

    public ThirdCache(Cache<CacheKey, Object> pointCache) {
        this(pointCache, 1000, 0.25);
    }

    public ThirdCache(com.moxa.dream.driver.cache.Cache<CacheKey, Object> pointCache, int limit, double rate) {
        ObjectUtil.requireNonNull(pointCache, "Property 'pointCache' is required");
        ObjectUtil.requireTrue(limit > 0, "Property 'limit' must gt 0");
        ObjectUtil.requireTrue(rate > 0 && rate <= 1, "Property 'rate' must gt 0 and leq 1");
        this.pointCache = pointCache;
        this.limit = limit;
        this.rate = rate;
    }

    @Override
    public void put(MappedStatement mappedStatement, Object value) {
        Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = mappedStatement.getTableScanInfoMap();
        CacheKey sqlKey = mappedStatement.getSqlKey();
        CacheKey uniqueKey = mappedStatement.getUniqueKey();
        if (!ObjectUtil.isNull(tableScanInfoMap)) {
            for (String table : tableScanInfoMap.keySet()) {
                Set<CacheKey> cacheKeySet = tableMap.get(table);
                if (cacheKeySet == null) {
                    synchronized (this) {
                        cacheKeySet = tableMap.get(table);
                        if (cacheKeySet == null) {
                            cacheKeySet = Collections.newSetFromMap(new ConcurrentHashMap<>());
                            tableMap.put(table, cacheKeySet);
                        }
                    }
                }
                cacheKeySet.add(sqlKey);
            }
        }
        Set<CacheKey> cacheKeySet = indexMap.get(sqlKey);
        if (cacheKeySet == null) {
            synchronized (this) {
                cacheKeySet = indexMap.get(sqlKey);
                if (cacheKeySet == null) {
                    cacheKeySet = Collections.newSetFromMap(new ConcurrentHashMap<>());
                    indexMap.put(sqlKey, cacheKeySet);
                }
                indexMap.put(sqlKey, cacheKeySet);
            }
        } else if (cacheKeySet.size() > limit) {
            if (canDelete.compareAndSet(true, false)) {
                Set<CacheKey> finalCacheKeySet = cacheKeySet;
                ThreadUtil.execute(() -> {
                    for (CacheKey cacheKey : finalCacheKeySet) {
                        double random = Math.random();
                        if (random <= rate) {
                            finalCacheKeySet.remove(cacheKey);
                        }
                    }
                });
            }
        }
        cacheKeySet.add(uniqueKey);
        pointCache.put(uniqueKey, value);
    }

    @Override
    public Object get(MappedStatement mappedStatement) {
        CacheKey sqlKey = mappedStatement.getSqlKey();
        CacheKey uniqueKey = mappedStatement.getUniqueKey();
        Set<CacheKey> cacheKeySet = indexMap.get(sqlKey);
        if (!ObjectUtil.isNull(cacheKeySet) && cacheKeySet.contains(uniqueKey)) {
            return pointCache.get(uniqueKey);
        } else
            return null;
    }

    @Override
    public void remove(MappedStatement mappedStatement) {
        Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = mappedStatement.getTableScanInfoMap();
        if (!ObjectUtil.isNull(tableScanInfoMap)) {
            for (String table : tableScanInfoMap.keySet()) {
                Set<CacheKey> cacheKeySet = tableMap.get(table);
                if (!ObjectUtil.isNull(cacheKeySet)) {
                    for (CacheKey cacheKey : cacheKeySet) {
                        Set<CacheKey> uniqueCacheKeySet = indexMap.remove(cacheKey);
                        if (!ObjectUtil.isNull(uniqueCacheKeySet)) {
                            ThreadUtil.execute(() -> {
                                for (CacheKey uniqueCacheKey : uniqueCacheKeySet) {
                                    pointCache.remove(uniqueCacheKey);
                                }
                            });
                            uniqueCacheKeySet.clear();
                        }
                    }
                    cacheKeySet.clear();
                }
            }
        }
    }

    @Override
    public void clear() {

    }
}
