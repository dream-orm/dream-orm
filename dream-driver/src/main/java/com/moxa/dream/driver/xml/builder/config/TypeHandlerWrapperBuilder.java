package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class TypeHandlerWrapperBuilder extends XMLBuilder {
    private TypeHandlerWrapper typeHandlerWrapper;

    public TypeHandlerWrapperBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.TYPEHANDLERWRAPPER:
                typeHandlerWrapper = XmlUtil.applyAttributes(TypeHandlerWrapper.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.TYPEHANDLERWRAPPER);
        }

    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return typeHandlerWrapper;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {

    }

    static class TypeHandlerWrapper {
        private String type;

        public String getType() {
            return type;
        }
    }
}
