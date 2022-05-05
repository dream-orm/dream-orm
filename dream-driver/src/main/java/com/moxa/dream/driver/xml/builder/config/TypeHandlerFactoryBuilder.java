package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class TypeHandlerFactoryBuilder extends XMLBuilder {
    private TypeHandlerFactory typeHandlerFactory;

    public TypeHandlerFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.TYPEHANDLERFACTORY:
                typeHandlerFactory = XmlUtil.applyAttributes(TypeHandlerFactory.class, attributes);
                break;
            case XmlConstant.TYPEHANDLERWRAPPER:
                TypeHandlerWrapperBuilder typeHandlerWrapperBuilder = new TypeHandlerWrapperBuilder(workHandler);
                typeHandlerWrapperBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.TYPEHANDLERFACTORY);
                break;
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return typeHandlerFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.TYPEHANDLERWRAPPER:
                typeHandlerFactory.typeHandlerWrapperList.add((TypeHandlerWrapperBuilder.TypeHandlerWrapper) obj);
                break;
        }
    }

    static class TypeHandlerFactory {
        private String type;
        private final List<TypeHandlerWrapperBuilder.TypeHandlerWrapper> typeHandlerWrapperList = new ArrayList<>();

        public String getType() {
            return type;
        }

        public List<TypeHandlerWrapperBuilder.TypeHandlerWrapper> getTypeHandlerWrapperList() {
            return typeHandlerWrapperList;
        }
    }

}
