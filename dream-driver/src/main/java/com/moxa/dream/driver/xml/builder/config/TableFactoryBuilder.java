package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class TableFactoryBuilder extends XMLBuilder {
    private TableFactory tableFactory;

    public TableFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.TABLEFACTORY:
                tableFactory = XmlUtil.applyAttributes(TableFactory.class, attributes);
                break;
            case XmlConstant.MAPPING:
                new MappingBuilder(workHandler).startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.TABLEFACTORY);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return tableFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.MAPPING:
                tableFactory.mappingList.add(((MappingBuilder.Mapping) obj));
                break;
        }
    }

    static class TableFactory {
        private String type;
        private final List<MappingBuilder.Mapping> mappingList = new ArrayList<>();

        public String getType() {
            return type;
        }

        public List<MappingBuilder.Mapping> getMappingList() {
            return mappingList;
        }
    }
}
