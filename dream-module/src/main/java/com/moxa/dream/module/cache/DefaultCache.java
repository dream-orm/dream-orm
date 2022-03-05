package com.moxa.dream.module.cache;

import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ThreadUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCache implements Cache {
    protected Map<String, Set<CacheKey>> tableMap = new ConcurrentHashMap<>();
    protected Map<CacheKey, Map<CacheKey, Object>> inLineMap = new ConcurrentHashMap<>();
    private int limit;
    private double rate;

    public DefaultCache(int limit, double rate) {
        ObjectUtil.requireTrue(limit > 0, "Property 'limit' must gt 0");
        ObjectUtil.requireTrue(rate > 0 && rate <= 1, "Property 'rate' must gt 0 and leq 1");
        this.limit = limit;
        this.rate = rate;
    }

    @Override
    public void put(MappedStatement mappedStatement, Object value) {
        Set<String> tableSet = mappedStatement.getTableSet();
        CacheKey sqlKey = mappedStatement.getSqlKey();
        CacheKey uniqueKey = mappedStatement.getUniqueKey();
        if (!ObjectUtil.isNull(tableSet)) {
            for (String table : tableSet) {
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
        Map<CacheKey, Object> objectMap = inLineMap.get(sqlKey);
        if (objectMap == null) {
            synchronized (this) {
                objectMap = inLineMap.get(sqlKey);
                if (objectMap == null) {
                    objectMap = new ConcurrentHashMap<>();
                    inLineMap.put(sqlKey, objectMap);
                }
            }
        } else if (objectMap.size() > limit) {
            Iterator<Map.Entry<CacheKey, Object>> iterator = objectMap.entrySet().iterator();
            ThreadUtil.execute(() -> {
                while (iterator.hasNext()) {
                    double random = Math.random();
                    if (random <= rate) {
                        iterator.remove();
                    }
                }
            });
        }
        objectMap.put(uniqueKey, value);

    }

    @Override
    public Object get(MappedStatement mappedStatement) {
        CacheKey sqlKey = mappedStatement.getSqlKey();
        Map<CacheKey, Object> objectMap = inLineMap.get(sqlKey);
        CacheKey uniqueKey = mappedStatement.getUniqueKey();
        if (objectMap != null) {
            return objectMap.get(uniqueKey);
        } else
            return null;
    }

    @Override
    public void remove(MappedStatement mappedStatement) {
        Set<String> tableSet = mappedStatement.getTableSet();
        if (!ObjectUtil.isNull(tableSet)) {
            for (String table : tableSet) {
                Set<CacheKey> cacheKeySet = tableMap.get(table);
                if (!ObjectUtil.isNull(cacheKeySet)) {
                    for (CacheKey cacheKey : cacheKeySet) {
                        Map<CacheKey, Object> objectMap = inLineMap.remove(cacheKey);
                        objectMap.clear();
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
