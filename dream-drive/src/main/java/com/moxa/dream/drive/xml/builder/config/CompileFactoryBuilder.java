package com.moxa.dream.drive.xml.builder.config;

import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.moudle.XmlConstant;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class CompileFactoryBuilder extends XMLBuilder {
    private CompileFactory compileFactory;

    public CompileFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.COMPILEFACTORY:
                compileFactory = XmlUtil.applyAttributes(CompileFactory.class, attributes);
                break;
            case XmlConstant.PROPERTY:
                PropertyBuilder propertyBuilder = new PropertyBuilder(workHandler);
                propertyBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.COMPILEFACTORY);
                break;
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return compileFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.PROPERTY:
                PropertyBuilder.Property property = (PropertyBuilder.Property) obj;
                compileFactory.propertyList.add(property);
                break;
        }
    }

    static class CompileFactory {
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
