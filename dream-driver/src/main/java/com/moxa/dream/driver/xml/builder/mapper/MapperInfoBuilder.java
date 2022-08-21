package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.action.MapperAction;
import com.moxa.dream.driver.action.ServiceAction;
import com.moxa.dream.driver.action.SqlAction;
import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MapperInfoBuilder extends XMLBuilder {
    private final Configuration configuration;
    private final Map<String, MethodInfo.Builder> builderMap;

    public MapperInfoBuilder(Configuration configuration, XmlHandler workHandler, Map<String, MethodInfo.Builder> builderMap) {
        super(workHandler);
        this.configuration = configuration;
        this.builderMap = builderMap;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.MAPPER:
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
                ObjectUtil.requireNonNull(methodBuilder, "方法 '" + name + "'未在类注册");
                String timeOut = methodInfo.getTimeOut();
                methodBuilder
                        .name(methodInfo.getName())
                        .timeOut(timeOut == null ? null : Integer.valueOf(timeOut));
                String sql = methodInfo.getValue();
                if (sql != null && !sql.trim().equals("")) {
                    methodBuilder.sql(sql);
                }
                InitBuilder.Init init = methodInfo.getInit();
                LoopBuilder.Loop loop = methodInfo.getLoop();
                DestroyBuilder.Destroy destroy = methodInfo.getDestroy();
                if (init != null) {
                    methodBuilder.initActionList(getActionList(init.getActionList()));
                }
                if (loop != null) {
                    methodBuilder.initActionList(getActionList(loop.getActionList()));
                }
                if (destroy != null) {
                    methodBuilder.initActionList(getActionList(destroy.getActionList()));
                }
                break;
        }
    }

    public Action[] getActionList(List<ActionBuilder.Action> actionList) {
        Action[] actions = null;
        if (!ObjectUtil.isNull(actionList)) {
            actions = new Action[actionList.size()];
            for (int i = 0; i < actionList.size(); i++) {
                ActionBuilder.Action action = actionList.get(i);
                String type = action.getType();
                switch (type) {
                    case "sql":
                        actions[i] = new SqlAction(configuration, action.getProperty(), action.getRef());
                        break;
                    case "mapper":
                        actions[i] = new MapperAction(configuration, action.getProperty(), action.getRef());
                        break;
                    case "service":
                        actions[i] = new ServiceAction(action.getProperty(), action.getRef());
                        break;
                    default:
                        throw new DreamRunTimeException("action类型限定为：['sql','mapper','service']");
                }
                Properties properties = getProperties(action.getPropertyList());
                if(properties!=null) {
                    actions[i].setProperties(properties);
                }
            }
        }
        return actions;
    }

    Properties getProperties(List<PropertyBuilder.Property> propertyList) {
        Properties properties = null;
        if (propertyList != null && propertyList.size() > 0) {
            properties = new Properties();
            for (PropertyBuilder.Property property : propertyList) {
                properties.put(property.getName(), property.getValue());
            }
        }
        return properties;
    }


}
