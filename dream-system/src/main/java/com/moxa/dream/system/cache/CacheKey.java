package com.moxa.dream.system.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class CacheKey implements Cloneable {


    private static final int DEFAULT_MULTIPLIER = 37;
    private static final int DEFAULT_HASHCODE = 17;

    private final int multiplier;
    private int hashcode;
    private long checksum;
    private int count;
    private List<Integer> updateList = new ArrayList<>(8);

    public CacheKey() {
        this.hashcode = DEFAULT_HASHCODE;
        this.multiplier = DEFAULT_MULTIPLIER;
        this.count = 0;
    }

    public void update(Object... object) {
        for (int i = 0; i < object.length; i++) {
            int baseHashCode = Objects.hashCode(object[i]);
            count++;
            checksum += baseHashCode;
            baseHashCode *= count;
            hashcode = multiplier * hashcode + baseHashCode;
            updateList.add(baseHashCode);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CacheKey)) {
            return false;
        }

        final CacheKey cacheKey = (CacheKey) object;

        if (hashcode != cacheKey.hashcode || checksum != cacheKey.checksum || count != cacheKey.count) {
            return false;
        }
        for (int i = 0; i < updateList.size(); i++) {
            int thisCode = updateList.get(i);
            int thatCode = cacheKey.updateList.get(i);
            if (thisCode != thatCode) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringJoiner returnValue = new StringJoiner(":");
        returnValue.add(String.valueOf(hashcode));
        returnValue.add(String.valueOf(checksum));
        updateList.stream().map(Objects::toString).forEach(returnValue::add);
        return returnValue.toString();
    }

    @Override
    public int hashCode() {
        return hashcode;
    }


    @Override
    public CacheKey clone() {
        CacheKey clonedCacheKey;
        ArrayList<Integer> cloneUpdateList = new ArrayList<>(updateList);
        try {
            clonedCacheKey = (CacheKey) super.clone();
            clonedCacheKey.updateList = cloneUpdateList;
            return clonedCacheKey;
        } catch (CloneNotSupportedException e) {
            clonedCacheKey = new CacheKey();
            clonedCacheKey.updateList = cloneUpdateList;
            clonedCacheKey.count = count;
            clonedCacheKey.hashcode = hashcode;
            clonedCacheKey.checksum = checksum;
            return clonedCacheKey;
        }
    }
}
