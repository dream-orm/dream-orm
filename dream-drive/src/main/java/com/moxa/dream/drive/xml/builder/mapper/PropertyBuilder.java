package com.moxa.dream.drive.xml.builder.mapper;

import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.moudle.XmlConstant;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class PropertyBuilder extends XMLBuilder {
    private Property property;

    public PropertyBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.PROPERTY:
                property = XmlUtil.applyAttributes(Property.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.PROPERTY);
                break;
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return property;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {

    }

    static class Property {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
