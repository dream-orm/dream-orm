package com.dream.system.inject.factory;

import com.dream.system.config.MethodInfo;
import com.dream.system.inject.*;
import com.dream.util.common.ObjectUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultInjectFactory implements InjectFactory {
    private Map<Class<? extends Inject>, Inject> injectMap = new LinkedHashMap<>();

    public DefaultInjectFactory() {
        injectMap.put(ActionInject.class, new ActionInject());
        injectMap.put(AnnotationInject.class, new AnnotationInject());
        injectMap.put(PageInject.class, new PageInject(true));
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
    public void inject(MethodInfo methodInfo) {
        for (Inject inject : injectMap.values()) {
            inject.inject(methodInfo);
        }
    }

    @Override
    public <T extends Inject> T getInject(Class<T> inject) {
        return (T) injectMap.get(inject);
    }
}
