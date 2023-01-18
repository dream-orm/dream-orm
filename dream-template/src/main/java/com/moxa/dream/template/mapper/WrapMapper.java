package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.annotation.Wrap;
import com.moxa.dream.template.annotation.WrapType;
import com.moxa.dream.template.wrap.Wrapper;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public abstract class WrapMapper extends ValidateMapper {

    public WrapMapper(Session session) {
        super(session);
    }

    @Override
    protected final MethodInfo getValidateMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        Map<String, Wrapper> wrapObjectMap = new HashMap<>(4);
        List<Field> fieldList = ReflectUtil.findField(type);
        List<Field> acceptList = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(Wrap.class)) {
                    Wrap wrapAnnotation = field.getDeclaredAnnotation(Wrap.class);
                    WrapType wrapType = wrapAnnotation.type();
                    if (accept(wrapType)) {
                        acceptList.add(field);
                        Wrapper wrapper = ReflectUtil.create(wrapAnnotation.value());
                        wrapObjectMap.put(field.getName(), wrapper);
                    }
                } else {
                    acceptList.add(field);
                }
            }
        }
        MethodInfo methodInfo = getWrapMethodInfo(configuration, tableInfo, acceptList, arg);
        if (!wrapObjectMap.isEmpty()) {
            methodInfo.set(WrapObjectMap.class, new WrapObjectMap(wrapObjectMap));
        }
        return methodInfo;
    }

    protected abstract MethodInfo getWrapMethodInfo(Configuration configuration, TableInfo tableInfo, List<Field> fieldList, Object arg);

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg, Consumer<MappedStatement> mappedStatementConsumer) {
        WrapObjectMap wrapObjectMap = methodInfo.get(WrapObjectMap.class);
        Command command = getCommand();
        if (command == Command.BATCH) {
            List<?> argList = (List<?>) arg;
            if (wrapObjectMap != null) {
                for (int i = 0; i < argList.size(); i++) {
                    wrap(wrapObjectMap, argList.get(i));
                }
            }
        } else {
            if (wrapObjectMap != null) {
                wrap(wrapObjectMap, arg);
            }
        }
        return super.execute(methodInfo, arg, mappedStatementConsumer);
    }

    protected void wrap(WrapObjectMap wrapObjectMap, Object arg) {
        Iterator<Map.Entry<String, Wrapper>> iterator = wrapObjectMap.wrapObjectMap.entrySet().iterator();
        ObjectWrapper objectWrapper = ObjectWrapper.wrapper(arg);
        while (iterator.hasNext()) {
            Map.Entry<String, Wrapper> entry = iterator.next();
            String key = entry.getKey();
            Object value = objectWrapper.get(key);
            Wrapper wrapper = entry.getValue();
            objectWrapper.set(key, wrapper.wrap(value));
        }
    }

    protected abstract boolean accept(WrapType wrapType);

    class WrapObjectMap {
        private Map<String, Wrapper> wrapObjectMap;

        public WrapObjectMap(Map<String, Wrapper> wrapObjectMap) {
            this.wrapObjectMap = wrapObjectMap;
        }
    }
}
