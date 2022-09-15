package com.moxa.dream.drive.xml.builder.config;

import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.moudle.XmlConstant;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class InjectBuilder extends XMLBuilder {
    private Inject inject;

    public InjectBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.INJECT:
                inject = XmlUtil.applyAttributes(Inject.class, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.INJECT);
        }

    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return inject;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {

    }

    static class Inject {
        private String type;

        public String getType() {
            return type;
        }
    }
}
