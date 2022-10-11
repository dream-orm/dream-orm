package com.moxa.dream.system.inject.factory;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.AnnotationInject;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.inject.PageInject;
import com.moxa.dream.system.inject.ScanInject;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

public class DefaultInjectFactory implements InjectFactory {
    private Class<? extends Inject>[] injectClasses;

    @Override
    public void injects(Class<? extends Inject>[] injects) {
        this.injectClasses = injects;
    }

    @Override
    public final void inject(MethodInfo methodInfo) {
        AnnotationInject annotationInject = new AnnotationInject();
        annotationInject.inject(methodInfo);
        PageInject pageInject = new PageInject();
        pageInject.inject(methodInfo);
        if (!ObjectUtil.isNull(injectClasses)) {
            for (int i = 0; i < injectClasses.length; i++) {
                Inject inject = ReflectUtil.create(injectClasses[i]);
                inject.inject(methodInfo);
            }
        }
        ScanInject scanInject = new ScanInject();
        scanInject.inject(methodInfo);
    }

}
