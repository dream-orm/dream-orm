package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class SqlActionBuilder extends XMLBuilder {
    private SqlAction sqlAction;

    public SqlActionBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.SQLACTION:
                sqlAction = XmlUtil.applyAttributes(SqlAction.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.SQLACTION);
        }
    }

    @Override
    public void characters(String s) {
        sqlAction.value = sqlAction.value + s;
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return sqlAction;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
    }

    static class SqlAction {
        private String property;
        private String antlr;
        private String command;

        private String value = "";

        public String getProperty() {
            return property;
        }

        public String getAntlr() {
            return antlr;
        }

        public String getCommand() {
            return command;
        }

        public String getValue() {
            return value;
        }
    }
}
