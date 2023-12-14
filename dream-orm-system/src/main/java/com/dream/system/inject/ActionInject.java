package com.dream.system.inject;

import com.dream.system.action.ActionProcessor;
import com.dream.system.annotation.Processor;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.action.LoopAction;
import com.dream.util.common.ObjectUtil;
import com.dream.util.reflect.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行为注入
 */
public class ActionInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        Class colType = methodInfo.getColType();
        if (!ReflectUtil.isBaseClass(colType) && !Map.class.isAssignableFrom(colType)) {
            List<Field> fieldList = ReflectUtil.findField(colType);
            List<LoopAction> loopActionList = new ArrayList<>();
            for (Field field : fieldList) {
                Annotation[] annotations = field.getAnnotations();
                if (!ObjectUtil.isNull(annotations)) {
                    for (Annotation annotation : annotations) {
                        Class<? extends Annotation> annotationType = annotation.annotationType();
                        Processor loopActionAnnotation = annotationType.getAnnotation(Processor.class);
                        if (loopActionAnnotation != null) {
                            Class<? extends ActionProcessor> actionProcessorType = loopActionAnnotation.value();
                            ActionProcessor actionProcessor = ReflectUtil.create(actionProcessorType);
                            Map<String, Object> paramMap = ReflectUtil.getAnnotationMap(annotation);
                            actionProcessor.init(field, paramMap, methodInfo.getConfiguration());
                            loopActionList.add(actionProcessor);
                        }
                    }
                }
            }
            if (!ObjectUtil.isNull(loopActionList)) {
                methodInfo.addLoopAction(loopActionList.toArray(new LoopAction[0]));
            }
        }
    }
}
