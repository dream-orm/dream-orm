package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.util.SystemUtil;
import com.moxa.dream.template.annotation.InjectType;
import com.moxa.dream.template.annotation.Wrap;
import com.moxa.dream.template.wrap.Wrapper;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class InjectSqlMapper extends AbstractSqlMapper {
    private String param = "param";

    public InjectSqlMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type) {
        Map<String, Wrapper> injectObjectMap = new HashMap<>();
        List<Field> fieldList = ReflectUtil.findField(type);
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (!ignore(field)) {
                    if (field.isAnnotationPresent(Wrap.class)) {
                        Wrap wrapAnnotation = field.getDeclaredAnnotation(Wrap.class);
                        InjectType injectType = wrapAnnotation.type();
                        if (accept(injectType)) {
                            Wrapper wrapper = ReflectUtil.create(wrapAnnotation.value());
                            injectObjectMap.put(param + "." + field.getName(), wrapper);
                        }
                    }
                }
            }
        }
        MethodInfo methodInfo = doGetMethodInfo(configuration, tableInfo, type);
        if (!injectObjectMap.isEmpty()) {
            methodInfo.set(InjectObjectMap.class, new InjectObjectMap(injectObjectMap));
        }
        return methodInfo;
    }

    protected abstract MethodInfo doGetMethodInfo(Configuration configuration, TableInfo tableInfo, Class type);

    @Override
    protected Object execute(MethodInfo methodInfo, Map<String, Object> argMap) {
        InjectObjectMap injectObjectMap = methodInfo.get(InjectObjectMap.class);
        if (injectObjectMap != null) {
            Iterator<Map.Entry<String, Wrapper>> iterator = injectObjectMap.injectObjectMap.entrySet().iterator();
            ObjectWrapper objectWrapper = ObjectWrapper.wrapper(argMap);
            while (iterator.hasNext()) {
                Map.Entry<String, Wrapper> entry = iterator.next();
                String key = entry.getKey();
                Object value = objectWrapper.get(key);
                Wrapper wrapper = entry.getValue();
                objectWrapper.set(key, wrapper.wrap(value));
            }
        }
        return super.execute(methodInfo, argMap);
    }

    protected boolean ignore(Field field) {
        return SystemUtil.ignoreField(field);
    }

    protected abstract boolean accept(InjectType injectType);

    class InjectObjectMap {
        private Map<String, Wrapper> injectObjectMap;

        public InjectObjectMap(Map<String, Wrapper> injectObjectMap) {
            this.injectObjectMap = injectObjectMap;
        }

        public Map<String, Wrapper> getInjectObjectMap() {
            return injectObjectMap;
        }
    }
}
