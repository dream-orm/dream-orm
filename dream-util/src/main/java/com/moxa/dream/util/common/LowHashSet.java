package com.moxa.dream.util.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

public class LowHashSet extends HashSet<String> {
    public LowHashSet() {
    }

    public LowHashSet(Collection<String> collection) {
        if (!ObjectUtil.isNull(collection)) {
            addAll(collection);
        }
    }

    @Override
    public boolean add(String s) {
        return super.add(s.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean modified = false;
        for (String s : c) {
            modified |= add(s);
        }
        return modified;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof String)
            return super.contains(((String) o).toLowerCase(Locale.ENGLISH));
        else return false;
    }
}
