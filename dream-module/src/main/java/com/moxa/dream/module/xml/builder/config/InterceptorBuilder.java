package com.moxa.dream.module.xml.builder.config;

import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.moudle.XmlConstant;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.module.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class InterceptorBuilder extends XMLBuilder {
    private Interceptor interceptor;

    public InterceptorBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.INTERCEPTOR:
                interceptor = XmlUtil.applyAttributes(Interceptor.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.INTERCEPTOR);
        }

    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return interceptor;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {

    }

    static class Interceptor {
        private String type;

        public String getType() {
            return type;
        }
    }
}
