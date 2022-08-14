package com.moxa.dream.util.common;


import java.util.HashMap;
import java.util.Locale;

public class LowHashMap<T> extends HashMap<String, T> {
    @Override
    public T put(String key, T value) {
        return super.put(key.toLowerCase(Locale.ENGLISH), value);
    }

    @Override
    public T get(Object key) {
        if (!(key instanceof String))
            return null;
        return super.get(((String) key).toLowerCase(Locale.ENGLISH));
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String))
            return false;
        return super.containsKey(((String) key).toLowerCase(Locale.ENGLISH));
    }
}
