package com.moxa.dream.sql.mock;

import com.moxa.dream.system.cache.CacheKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MockCacheKey extends CacheKey {
    private String sql;
    private List<Object> paramList = new ArrayList<>();

    public MockCacheKey(String sql) {
        this.sql = sql;
    }

    @Override
    public void update(Object[] object) {
        if (object != null) {
            for (Object o : object) {
                paramList.add(o);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sql) + Objects.hash(paramList);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MockCacheKey)) {
            return false;
        }
        final MockCacheKey mockCacheKey = (MockCacheKey) object;
        if (!sql.equals(mockCacheKey.sql)) {
            return false;
        }
        if (paramList.size() != mockCacheKey.paramList.size()) {
            return false;
        }
        for (int i = 0; i < paramList.size(); i++) {
            if (!Objects.equals(paramList.get(i), mockCacheKey.paramList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MockCacheKey clone() {
        MockCacheKey mockCacheKey = new MockCacheKey(sql);
        mockCacheKey.paramList = paramList;
        return mockCacheKey;
    }
}
