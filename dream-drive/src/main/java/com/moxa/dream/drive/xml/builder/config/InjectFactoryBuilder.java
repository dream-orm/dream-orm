package com.moxa.dream.drive.xml.builder.config;

import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.moudle.XmlConstant;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class InjectFactoryBuilder extends XMLBuilder {
    private InjectFactory injectFactory;

    public InjectFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.INJECTFACTORY:
                injectFactory = XmlUtil.applyAttributes(InjectFactory.class, attributes);
                break;
            case XmlConstant.INJECT:
                InjectBuilder injectBuilder = new InjectBuilder(workHandler);
                injectBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.INJECTFACTORY);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return injectFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.INTERCEPTOR:
                InjectBuilder.Inject inject = (InjectBuilder.Inject) obj;
                injectFactory.injectList.add(inject);
                break;
        }
    }

    static class InjectFactory {
        private final List<InjectBuilder.Inject> injectList = new ArrayList<>();
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<InjectBuilder.Inject> getInjectList() {
            return injectList;
        }
    }
}
