package com.moxa.dream.util.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LowHashSet extends HashSet<String> {
    public LowHashSet() {
    }

    public LowHashSet(Set<String> set) {
        if (!ObjectUtil.isNull(set)) {
            addAll(set);
        }
    }

    @Override
    public boolean add(String s) {
        ObjectUtil.requireNonNull(s, "Property 's' is required");
        return super.add(s.toLowerCase(Locale.ENGLISH));
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        ObjectUtil.requireNonNull(c, "Property 'c' is required");
        boolean modified = false;
        for (String s : c) {
            modified |= add(s);
        }
        return modified;
    }

    @Override
    public boolean contains(Object o) {
        ObjectUtil.requireNonNull(o, "Property 'o' is required");
        if (o instanceof String)
            return super.contains(((String) o).toLowerCase(Locale.ENGLISH));
        else return false;
    }
}
