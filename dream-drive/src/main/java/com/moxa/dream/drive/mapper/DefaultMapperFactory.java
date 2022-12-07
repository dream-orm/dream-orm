package com.moxa.dream.drive.mapper;

import com.moxa.dream.drive.annotation.Mapper;
import com.moxa.dream.drive.provider.ActionProvider;
import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.builder.mapper.MapperInfoBuilder;
import com.moxa.dream.drive.xml.moudle.XmlCallback;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.moudle.XmlParser;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.util.common.NullObject;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.resource.ResourceUtil;
import org.xml.sax.InputSource;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DefaultMapperFactory extends AbstractMapperFactory {
    public String getResource(Class<?> type) {
        Mapper mapperAnnotation = type.getAnnotation(Mapper.class);
        if (mapperAnnotation == null)
            return null;
        return mapperAnnotation.resource();
    }

    @Override
    protected void fillMethodInfoFromResource(Configuration configuration, Class type, Map<String, MethodInfo.Builder> builderMap) {
        String resource = getResource(type);
        if (!ObjectUtil.isNull(resource)) {
            InputSource inputSource = new InputSource(ResourceUtil.getResourceAsStream(resource));
            XmlParser xmlParser = new XmlParser();
            xmlParser.parse(inputSource, new XmlCallback() {
                @Override
                public XMLBuilder startDocument(XmlHandler xmlHandler) {
                    return new MapperInfoBuilder(configuration, xmlHandler, builderMap);
                }

                @Override
                public void endDocument(Object value) {
                }
            });
        }
    }

    public Class<?> getActionType(Class<?> type) {
        Mapper mapperAnnotation = type.getAnnotation(Mapper.class);
        if (mapperAnnotation == null) {
            return null;
        }
        Class<?> value = mapperAnnotation.value();
        if (value == NullObject.class) {
            return null;
        }
        return value;
    }

    @Override
    protected void fillMethodInfoFromAction(Configuration configuration, Class type, Map<String, MethodInfo.Builder> builderMap) {
        Class<?> actionType = getActionType(type);
        List<Method> methodList = ReflectUtil.find(actionType,
                (aType) -> Arrays.stream(aType.getDeclaredMethods())
                        .filter(method -> method.getParameters().length == 0)
                        .collect(Collectors.toList()));
        if (!ObjectUtil.isNull(methodList)) {
            Object actionObject = ReflectUtil.create(actionType);
            for (Method method : methodList) {
                String methodName = method.getName();
                MethodInfo.Builder builder = builderMap.get(methodName);
                if (builder == null) {
                    throw new DreamRunTimeException("类" + type.getName() + "不存在方法" + methodName);
                }
                ActionProvider actionProvider = null;
                try {
                    Class<?> returnType = method.getReturnType();
                    Object value = method.invoke(actionObject);
                    if (ActionProvider.class.isAssignableFrom(returnType)) {
                        actionProvider = (ActionProvider) value;
                    } else if (String.class == returnType) {
                        actionProvider = () -> (String) value;
                    }
                } catch (Exception e) {
                    throw new DreamRunTimeException("调用方法" + actionType.getName() + "." + methodName + "失败，" + e.getMessage(), e);
                }
                if (actionProvider != null) {
                    Action[] initActionList = actionProvider.init();
                    String sql = actionProvider.sql();
                    Action[] loopActionList = actionProvider.loop();
                    Action[] destroyActionList = actionProvider.destroy();
                    if (initActionList != null) {
                        builder.initActionList(initActionList);
                    }
                    if (sql != null) {
                        builder.sql(sql);
                    }
                    if (loopActionList != null) {
                        builder.loopActionList(loopActionList);
                    }
                    if (destroyActionList != null) {
                        builder.destroyActionList(destroyActionList);
                    }
                }
            }
        }
    }
}
