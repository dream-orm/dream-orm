package com.moxa.dream.system.cache;

import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryCache implements Cache {
    private final int limit;
    private final double rate;
    private final AtomicBoolean canDelete = new AtomicBoolean(true);
    protected Map<String, Set<CacheKey>> tableMap = new ConcurrentHashMap<>();
    protected Map<CacheKey, Map<CacheKey, Object>> indexMap = new ConcurrentHashMap<>();

    public MemoryCache(int limit, double rate) {
        ObjectUtil.requireTrue(limit > 0, "Property 'limit' must gt 0");
        ObjectUtil.requireTrue(rate > 0 && rate <= 1, "Property 'rate' must gt 0 and leq 1");
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
        Map<CacheKey, Object> keyMap = indexMap.get(sqlKey);
        if (keyMap == null) {
            synchronized (this) {
                keyMap = indexMap.get(sqlKey);
                if (keyMap == null) {
                    keyMap = new ConcurrentHashMap<>();
                    indexMap.put(sqlKey, keyMap);
                }
            }
        } else if (keyMap.size() > limit) {
            if (canDelete.compareAndSet(true, false)) {
                Map<CacheKey, Object> finalMap = keyMap;
                Iterator<Map.Entry<CacheKey, Object>> iterator = finalMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    double random = Math.random();
                    if (random <= rate) {
                        iterator.remove();
                    }
                }
                canDelete.set(true);
            }
        }
        keyMap.put(uniqueKey, value);
    }

    @Override
    public Object get(MappedStatement mappedStatement) {
        CacheKey sqlKey = mappedStatement.getSqlKey();
        Map<CacheKey, Object> keyMap = indexMap.get(sqlKey);
        CacheKey uniqueKey = mappedStatement.getUniqueKey();
        if (keyMap != null) {
            return keyMap.get(uniqueKey);
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
                        Map<CacheKey, Object> keyMap = indexMap.remove(cacheKey);
                        if (!ObjectUtil.isNull(keyMap)) {
                            keyMap.clear();
                        }
                    }
                    cacheKeySet.clear();
                }
            }
        }
    }

    @Override
    public void clear() {
        for (Set<CacheKey> cacheKeySet : tableMap.values()) {
            cacheKeySet.clear();
        }
        tableMap.clear();
        for (Map<CacheKey, Object> cacheKeyMap : indexMap.values()) {
            cacheKeyMap.clear();
        }
        indexMap.clear();
    }
}
