package com.dream.mate.share.holder;

import java.util.function.Supplier;

public final class DataSourceHolder {

    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER = new ThreadLocal<>();

    private DataSourceHolder() {
    }

    public static String get() {
        return LOOKUP_KEY_HOLDER.get();
    }


    public static void set(String dataSourceName) {
        LOOKUP_KEY_HOLDER.set(dataSourceName);
    }


    public static void remove() {
        LOOKUP_KEY_HOLDER.remove();
    }

    public static <T> T use(String dataSourceName, Supplier<T> supplier) {
        try {
            set(dataSourceName);
            return supplier.get();
        } finally {
            remove();
        }
    }
}
