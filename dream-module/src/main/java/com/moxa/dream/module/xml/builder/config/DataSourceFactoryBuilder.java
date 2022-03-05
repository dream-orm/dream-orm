package com.moxa.dream.module.xml.builder.config;

import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.moudle.XmlConstant;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.module.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class DataSourceFactoryBuilder extends XMLBuilder {
    private DataSourceFactory dataSourceFactory;

    public DataSourceFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.DATASOURCEFACTORY:
                dataSourceFactory = XmlUtil.applyAttributes(DataSourceFactory.class, attributes);
                break;
            case XmlConstant.PROPERTY:
                PropertyBuilder propertyBuilder = new PropertyBuilder(workHandler);
                propertyBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.DATASOURCEFACTORY);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return dataSourceFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.PROPERTY:
                PropertyBuilder.Property property = (PropertyBuilder.Property) obj;
                dataSourceFactory.propertyList.add(property);
                break;
        }
    }

    static class DataSourceFactory {
        private String type;
        private List<PropertyBuilder.Property> propertyList = new ArrayList<>(4);

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public List<PropertyBuilder.Property> getPropertyList() {
            return propertyList;
        }
    }
}
