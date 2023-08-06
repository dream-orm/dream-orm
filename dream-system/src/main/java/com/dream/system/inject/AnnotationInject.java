package com.dream.system.inject;

import com.dream.system.config.MethodInfo;
import com.dream.util.common.ObjectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        Method method = methodInfo.getMethod();
        if (method != null) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (!ObjectUtil.isNull(annotations)) {
                for (Annotation annotation : annotations) {
                    Class<Annotation> annotationType = (Class<Annotation>) annotation.annotationType();
                    methodInfo.set(annotationType, annotation);
                }
            }
        }
    }
}
