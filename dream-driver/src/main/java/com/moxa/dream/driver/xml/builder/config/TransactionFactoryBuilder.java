package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class TransactionFactoryBuilder extends XMLBuilder {
    private TransactionFactory transactionFactory;

    public TransactionFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.TRANSACTIONFACTORY:
                transactionFactory = XmlUtil.applyAttributes(TransactionFactory.class, attributes);
                break;
            case XmlConstant.PROPERTY:
                PropertyBuilder propertyBuilder = new PropertyBuilder(workHandler);
                propertyBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.TRANSACTIONFACTORY);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return transactionFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.PROPERTY:
                PropertyBuilder.Property property = (PropertyBuilder.Property) obj;
                transactionFactory.propertyList.add(property);
                break;
        }
    }

    static class TransactionFactory {
        private String type;
        private final List<PropertyBuilder.Property> propertyList = new ArrayList<>();

        public String getType() {
            return type;
        }

        public List<PropertyBuilder.Property> getPropertyList() {
            return propertyList;
        }
    }
}
