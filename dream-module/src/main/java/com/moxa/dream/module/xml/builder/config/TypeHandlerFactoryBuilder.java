package com.moxa.dream.module.xml.builder.config;

import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.moudle.XmlConstant;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.module.xml.util.XmlUtil;
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
        private List<TypeHandlerWrapperBuilder.TypeHandlerWrapper> typeHandlerWrapperList = new ArrayList<>();

        public String getType() {
            return type;
        }

        public List<TypeHandlerWrapperBuilder.TypeHandlerWrapper> getTypeHandlerWrapperList() {
            return typeHandlerWrapperList;
        }
    }

}
