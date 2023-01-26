package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.annotation.Fetch;
import com.moxa.dream.template.fetch.Fetcher;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FetchMapper extends ValidateMapper {
    public FetchMapper(Session session) {
        super(session);
    }

    @Override
    protected final MethodInfo getValidateMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        MethodInfo methodInfo = getFetchMethodInfo(configuration, tableInfo, type, arg);
        List<Field> fieldList = ReflectUtil.findField(type);
        if (!ObjectUtil.isNull(fieldList)) {
            List<Action> actionList = new ArrayList<>();
            for (Field field : fieldList) {
                Annotation[] annotations = field.getAnnotations();
                if (!ObjectUtil.isNull(annotations)) {
                    for (Annotation annotation : annotations) {
                        Class<? extends Annotation> annotationType = annotation.annotationType();
                        Fetch fetchAnnotation = annotationType.getAnnotation(Fetch.class);
                        if (fetchAnnotation != null) {
                            Map<String, Object> paramMap = getParamMap(annotation);
                            Class<? extends Fetcher> fetchType = fetchAnnotation.value();
                            Fetcher fetcher = ReflectUtil.create(fetchType);
                            actionList.add(new FetchAction(fetcher, field.getName(), paramMap));
                        }
                    }
                }
            }
            if (!actionList.isEmpty()) {
                methodInfo.addLoopAction(actionList.toArray(new Action[0]));
            }
        }
        return methodInfo;
    }

    protected abstract MethodInfo getFetchMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg);

    class FetchAction implements Action {
        private Fetcher fetcher;
        private String property;
        private Map<String, Object> paramMap;

        public FetchAction(Fetcher fetcher, String property, Map<String, Object> paramMap) {
            this.fetcher = fetcher;
            this.property = property;
            this.paramMap = paramMap;
        }

        @Override
        public void doAction(Session session, MappedStatement mappedStatement, Object arg) {
            fetcher.fetch(arg, property, paramMap);
        }
    }

}
