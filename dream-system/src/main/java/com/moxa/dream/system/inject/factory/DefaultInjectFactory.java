package com.moxa.dream.system.inject.factory;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.AnnotationInject;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.inject.PageInject;
import com.moxa.dream.system.inject.ScanInject;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class DefaultInjectFactory implements InjectFactory {
    private Map<Class<? extends Inject>, Inject> injectMap = new LinkedHashMap<>();

    public DefaultInjectFactory() {
        injectMap.put(AnnotationInject.class, new AnnotationInject());
        injectMap.put(PageInject.class, new PageInject(false));
        injectMap.put(ScanInject.class, new ScanInject());
    }

    @Override
    public void injects(Inject... injects) {
        if (!ObjectUtil.isNull(injects)) {
            for (Inject inject : injects) {
                injectMap.put(inject.getClass(), inject);
            }
            Inject pageInject = injectMap.remove(PageInject.class);
            Inject scanInject = injectMap.remove(ScanInject.class);
            injectMap.put(PageInject.class, pageInject);
            injectMap.put(ScanInject.class, scanInject);
        }
    }

    @Override
    public void inject(MethodInfo methodInfo, Predicate<Inject> predicate) {
        for (Inject inject : injectMap.values()) {
            if (predicate == null || predicate.test(inject)) {
                inject.inject(methodInfo);
            }
        }
    }

    @Override
    public <T extends Inject> T getInject(Class<T> inject) {
        return (T) injectMap.get(inject);
    }
}
