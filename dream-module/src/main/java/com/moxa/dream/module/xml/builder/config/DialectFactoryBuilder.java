package com.moxa.dream.module.xml.builder.config;

import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.moudle.XmlConstant;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.module.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class DialectFactoryBuilder extends XMLBuilder {
    private DialectFactory dialectFactory;

    public DialectFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.DIALECTFACTORY:
                dialectFactory = XmlUtil.applyAttributes(DialectFactory.class, attributes);
                break;
            case XmlConstant.PROPERTY:
                PropertyBuilder propertyBuilder = new PropertyBuilder(workHandler);
                propertyBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.DIALECTFACTORY);
                break;
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return dialectFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.PROPERTY:
                dialectFactory.propertyList.add((PropertyBuilder.Property) obj);
        }
    }

    static class DialectFactory {
        private String type;
        private String dialect;
        private List<PropertyBuilder.Property> propertyList = new ArrayList<>();

        public String getType() {
            return type;
        }

        public String getDialect() {
            return dialect;
        }

        public List<PropertyBuilder.Property> getPropertyList() {
            return propertyList;
        }
    }

}
