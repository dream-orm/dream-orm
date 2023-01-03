package com.moxa.dream.template.resolve;

import com.moxa.dream.system.config.MappedStatement;

public interface MappedResolve {
    ThreadLocal<MappedResolve> threadLocal = new ThreadLocal<>();

    static void set(MappedResolve mappedResolve) {
        threadLocal.set(mappedResolve);
    }

    static MappedResolve get() {
        MappedResolve mappedResolve = threadLocal.get();
        threadLocal.remove();
        return mappedResolve;
    }

    void resolve(MappedStatement mappedStatement);
}
