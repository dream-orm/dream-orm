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
        Map<String, Wrapper> wrapObjectMap = new HashMap<>();
        List<Field> fieldList = ReflectUtil.findField(type);
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (!ignore(field)) {
                    if (field.isAnnotationPresent(Wrap.class)) {
                        Wrap wrapAnnotation = field.getDeclaredAnnotation(Wrap.class);
                        InjectType injectType = wrapAnnotation.type();
                        if (accept(injectType)) {
                            Wrapper wrapper = ReflectUtil.create(wrapAnnotation.value());
                            wrapObjectMap.put(param + "." + field.getName(), wrapper);
                        }
                    }
                }
            }
        }
        MethodInfo methodInfo = doGetMethodInfo(configuration, tableInfo, type);
        if (!wrapObjectMap.isEmpty()) {
            methodInfo.set(WrapObjectMap.class, new WrapObjectMap(wrapObjectMap));
        }
        return methodInfo;
    }

    protected abstract MethodInfo doGetMethodInfo(Configuration configuration, TableInfo tableInfo, Class type);

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg) {
        Map<String, Object> argMap = wrapArg(arg);
        WrapObjectMap wrapObjectMap = methodInfo.get(WrapObjectMap.class);
        if (wrapObjectMap != null) {
            Iterator<Map.Entry<String, Wrapper>> iterator = wrapObjectMap.wrapObjectMap.entrySet().iterator();
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

    class WrapObjectMap {
        private Map<String, Wrapper> wrapObjectMap;

        public WrapObjectMap(Map<String, Wrapper> wrapObjectMap) {
            this.wrapObjectMap = wrapObjectMap;
        }

        public Map<String, Wrapper> getInjectObjectMap() {
            return wrapObjectMap;
        }
    }
}
