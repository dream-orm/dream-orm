package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class ServiceActionBuilder extends XMLBuilder {
    private ServiceAction serviceAction;

    public ServiceActionBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.SERVICERACTION:
                serviceAction = XmlUtil.applyAttributes(ServiceAction.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.SERVICERACTION);
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return serviceAction;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
    }

    static class ServiceAction {
        private String property;
        private String methodRef;

        public String getProperty() {
            return property;
        }

        public String getMethodRef() {
            return methodRef;
        }
    }
}
