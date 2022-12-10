package com.moxa.dream.drive.cache;

import java.util.Set;

public interface Cache {
    void put(String key, Object value);

    Object get(String key);

    void push(String key, String value);

    Set<String> range(String key);

    boolean remove(String key);

    void clear();

}
