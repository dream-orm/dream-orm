package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.util.SystemUtil;
import com.moxa.dream.template.annotation.Wrap;
import com.moxa.dream.template.annotation.WrapType;
import com.moxa.dream.template.wrap.Wrapper;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public abstract class WrapMapper extends AbstractMapper {

    public WrapMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        Map<String, Wrapper> wrapObjectMap = new HashMap<>();
        List<Field> fieldList = ReflectUtil.findField(type);
        List<Field> acceptList = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (!ignore(field)) {
                    if (field.isAnnotationPresent(Wrap.class)) {
                        Wrap wrapAnnotation = field.getDeclaredAnnotation(Wrap.class);
                        WrapType wrapType = wrapAnnotation.type();
                        if (accept(wrapType)) {
                            acceptList.add(field);
                            Wrapper wrapper = ReflectUtil.create(wrapAnnotation.value());
                            wrapObjectMap.put(DREAM_TEMPLATE_PARAM + "." + field.getName(), wrapper);
                        }
                    } else {
                        acceptList.add(field);
                    }
                }
            }
        }
        MethodInfo methodInfo = doGetMethodInfo(configuration, tableInfo, acceptList, arg);
        if (!wrapObjectMap.isEmpty()) {
            methodInfo.set(WrapObjectMap.class, new WrapObjectMap(wrapObjectMap));
        }
        return methodInfo;
    }

    protected abstract MethodInfo doGetMethodInfo(Configuration configuration, TableInfo tableInfo, List<Field> fieldList, Object arg);

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg, Consumer<MappedStatement> mappedStatementConsumer) {
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
        return super.execute(methodInfo, argMap, mappedStatementConsumer);
    }

    protected boolean ignore(Field field) {
        return SystemUtil.ignoreField(field);
    }

    protected abstract boolean accept(WrapType wrapType);

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
