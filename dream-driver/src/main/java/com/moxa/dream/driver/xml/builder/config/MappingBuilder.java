package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class MappingBuilder extends XMLBuilder {
    private Mapping mapping;

    public MappingBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.MAPPING:
                mapping = XmlUtil.applyAttributes(Mapping.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.MAPPING);
        }

    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return mapping;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {

    }

    static class Mapping {
        private String type;

        public String getType() {
            return type;
        }
    }
}
