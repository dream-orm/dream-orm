package com.moxa.dream.system.antlr.wrapper;

import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationWrapper implements Wrapper {
    @Override
    public void wrapper(MethodInfo methodInfo) {
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
