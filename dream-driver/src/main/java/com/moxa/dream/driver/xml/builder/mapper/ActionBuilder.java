package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import com.moxa.dream.util.common.ObjectUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class ActionBuilder extends XMLBuilder {
    private Action action;

    public ActionBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.ACTION:
                action = XmlUtil.applyAttributes(Action.class, attributes);
                break;
            case XmlConstant.PROPERTY:
                PropertyBuilder propertyBuilder=new PropertyBuilder(workHandler);
                propertyBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.ACTION);
        }
    }

    @Override
    public void characters(String s) {
        if (ObjectUtil.isNull(action.ref)) {
            action.ref = s;
        } else {
            action.ref = action.ref + s;
        }
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return action;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName){
            case XmlConstant.PROPERTY:
                PropertyBuilder.Property property=(PropertyBuilder.Property)obj;
                action.propertyList.add(property);
                break;
        }
    }

    static class Action {
        private String type;
        private String property;
        private String ref;
        private List<PropertyBuilder.Property> propertyList=new ArrayList<>();

        public String getType() {
            return type;
        }

        public String getProperty() {
            return property;
        }

        public String getRef() {
            return ref;
        }

        public List<PropertyBuilder.Property> getPropertyList() {
            return propertyList;
        }
    }
}
