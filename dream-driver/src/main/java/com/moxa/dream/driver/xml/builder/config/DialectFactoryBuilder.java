package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
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

    }

    static class DialectFactory {
        private final List<PropertyBuilder.Property> propertyList = new ArrayList<>();
        private String type;

        public String getType() {
            return type;
        }

        public List<PropertyBuilder.Property> getPropertyList() {
            return propertyList;
        }
    }

}
