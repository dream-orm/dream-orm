package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class MapperFactoryBuilder extends XMLBuilder {
    private MapperFactory mapperFactory;

    public MapperFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.MAPPERFACTORY:
                mapperFactory = XmlUtil.applyAttributes(MapperFactory.class, attributes);
                break;
            case XmlConstant.MAPPING:
                new MappingBuilder(workHandler).startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.MAPPERFACTORY);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return mapperFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.MAPPING:
                mapperFactory.mappingList.add(((MappingBuilder.Mapping) obj));
                break;
        }
    }

    static class MapperFactory {
        private final List<MappingBuilder.Mapping> mappingList = new ArrayList<>();
        private String type;

        public String getType() {
            return type;
        }

        public List<MappingBuilder.Mapping> getMappingList() {
            return mappingList;
        }
    }
}
