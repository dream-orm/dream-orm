package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapper.EachInfo;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
import org.xml.sax.Attributes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapperInfoBuilder extends XMLBuilder {
    private List<MethodInfo> resultList;
    private Configuration configuration;
    private Map<String, MethodInfo.Builder> builderMap;

    public MapperInfoBuilder(Configuration configuration, XmlHandler workHandler, Map<String, MethodInfo.Builder> builderMap) {
        super(workHandler);
        this.configuration = configuration;
        this.builderMap = builderMap;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.MAPPER:
                resultList = new ArrayList<>();
                break;
            case XmlConstant.METHOD:
                MethodInfoBuilder methodInfoBuilder = new MethodInfoBuilder(workHandler);
                methodInfoBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.MAPPER);
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return builderMap;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.METHOD:
                MethodInfoBuilder.MethodInfo methodInfo = (MethodInfoBuilder.MethodInfo) obj;
                String name = methodInfo.getName();
                MethodInfo.Builder methodBuilder = builderMap.get(name);
                ObjectUtil.requireNonNull(methodBuilder, "Method '" + name + "' not found in class");
                String timeOut = methodInfo.getTimeOut();
                methodBuilder
                        .name(methodInfo.getName())
                        .timeOut(timeOut == null ? null : Integer.valueOf(timeOut));
                SqlBuilder.Sql sql = methodInfo.getSql();
                if (sql != null) {
                    methodBuilder.sql(sql.getValue());
                }
                EachListBuilder.EachList _eachList = methodInfo.getEachList();
                if (_eachList != null) {
                    List<EachBuilder.Each> eachList = _eachList.getEachList();
                    if (!ObjectUtil.isNull(eachList)) {
                        List<EachInfo> eachInfoList = new ArrayList<>(eachList.size());
                        for (EachBuilder.Each each : eachList) {
                            MethodRefBuilder.MethodRef methodRef = each.getMethodRef();
                            String methodClassName = methodRef.getValue();
                            int index = methodClassName.lastIndexOf(".");
                            ObjectUtil.requireTrue(index > 0, methodClassName + " not class method name");
                            Class type = ReflectUtil.loadClass(methodClassName.substring(0, index));
                            String methodName = methodClassName.substring(index + 1);
                            List<Method> methodList = ReflectUtil.findMethod(type)
                                    .stream()
                                    .filter(method -> method.getName().equals(methodName))
                                    .collect(Collectors.toList());
                            ObjectUtil.requireNonNull(methodList, methodClassName + " not exist");
                            ObjectUtil.requireTrue(methodList.size() == 1, methodClassName + " must exist one");
                            List<ArgBuilder.Arg> argList = each.getArgList();
                            eachInfoList.add(new EachInfo(type, methodList.get(0), methodRef.getField(), getArgMap(argList)));
                        }
                        methodBuilder.eachInfoList(eachInfoList);
                    }
                }
                break;
        }
    }

    Map<String, String> getArgMap(List<ArgBuilder.Arg> argList) {
        Map<String, String> argMap = null;
        if (!ObjectUtil.isNull(argList)) {
            argMap = new HashMap<>();
            for (ArgBuilder.Arg arg : argList) {
                argMap.put(arg.getName(), arg.getValue());
            }
        }
        return argMap;
    }
}
