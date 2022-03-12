package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class SqlBuilder extends XMLBuilder {
    private Sql sql;

    public SqlBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.SQL:
                sql = XmlUtil.applyAttributes(Sql.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.SQL);
        }
    }

    @Override
    public void characters(String value) {
        String trimValue;
        if (value == null || (trimValue = value.trim()).equals(""))
            return;
        sql.setValue(trimValue);
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return sql;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {

    }

    static class Sql {
        String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
