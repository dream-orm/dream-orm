package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class MethodRefBuilder extends XMLBuilder {
    private MethodRef methodRef;

    public MethodRefBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.METHODREF:
                methodRef = XmlUtil.applyAttributes(MethodRef.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.METHODREF);
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return methodRef;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {

    }

    static class MethodRef {
        private String value;
        private String field;

        public String getValue() {
            return value;
        }

        public String getField() {
            return field;
        }
    }
}
