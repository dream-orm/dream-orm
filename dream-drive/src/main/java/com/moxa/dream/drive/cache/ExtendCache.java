package com.moxa.dream.drive.cache;

import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Set;

public class ExtendCache implements com.moxa.dream.system.cache.Cache {
    private Cache indexCache;
    private Cache valueCache;

    public ExtendCache(Cache indexCache, Cache valueCache) {
        this.indexCache = indexCache;
        this.valueCache = valueCache;
    }

    @Override
    public void put(MappedStatement mappedStatement, Object value) {
        CacheKey uniqueKey = mappedStatement.getUniqueKey();
        Set<String> tableSet = mappedStatement.getTableSet();
        if (uniqueKey != null && !ObjectUtil.isNull(tableSet)) {
            String key = uniqueKey.toString();
            for (String table : tableSet) {
                indexCache.push(table, key);
            }
            valueCache.put(key, value);
        }
    }

    @Override
    public Object get(MappedStatement mappedStatement) {
        CacheKey uniqueKey = mappedStatement.getUniqueKey();
        return valueCache.get(uniqueKey.toString());
    }

    @Override
    public void remove(MappedStatement mappedStatement) {
        Set<String> tableSet = mappedStatement.getTableSet();
        if (!ObjectUtil.isNull(tableSet)) {
            for (String table : tableSet) {
                Set<String> set = indexCache.range(table);
                if (set != null) {
                    for (String key : set) {
                        valueCache.remove(key);
                    }
                    indexCache.remove(table);
                }
            }
        }
    }

    @Override
    public void clear() {
        valueCache.clear();
        indexCache.clear();
    }
}
