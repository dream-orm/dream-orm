package com.moxa.dream.util.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ObjectMap implements Map<String, Object> {
    private Object defaultValue;
    private Map<String, Object> builtMap;

    public ObjectMap(Object defaultValue) {
        this(defaultValue, new HashMap<>());
    }

    public ObjectMap(Object defaultValue, Map<String, Object> builtMap) {
        this.defaultValue = defaultValue;
        this.builtMap = builtMap;
    }

    @Override
    public int size() {
        return builtMap.size();
    }

    @Override
    public boolean isEmpty() {
        return builtMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return builtMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return builtMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        if (builtMap.containsKey(key)) {
            return builtMap.get(key);
        } else {
            return defaultValue;
        }
    }

    @Override
    public Object put(String key, Object value) {
        return builtMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return builtMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        builtMap.putAll(m);
    }

    @Override
    public void clear() {
        builtMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return builtMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return builtMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return builtMap.entrySet();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return builtMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        builtMap.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super Object, ?> function) {
        builtMap.replaceAll(function);
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return builtMap.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return builtMap.remove(key, value);
    }

    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        return builtMap.replace(key, oldValue, newValue);
    }

    @Override
    public Object replace(String key, Object value) {
        return builtMap.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(String key, Function<? super String, ?> mappingFunction) {
        return builtMap.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return builtMap.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return builtMap.compute(key, remappingFunction);
    }

    @Override
    public Object merge(String key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return builtMap.merge(key, value, remappingFunction);
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Map<String, Object> getBuiltMap() {
        return builtMap;
    }
}
