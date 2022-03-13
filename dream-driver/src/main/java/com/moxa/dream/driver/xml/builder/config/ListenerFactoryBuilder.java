package com.moxa.dream.driver.xml.builder.config;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class ListenerFactoryBuilder extends XMLBuilder {
    private ListenerFactory listenerFactory;

    public ListenerFactoryBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.LISTENERFACTORY:
                listenerFactory = XmlUtil.applyAttributes(ListenerFactory.class, attributes);
                break;
            case XmlConstant.LISTENER:
                ListenerBuilder interceptorBuilder = new ListenerBuilder(workHandler);
                interceptorBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.LISTENERFACTORY);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return listenerFactory;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.LISTENER:
                ListenerBuilder.Listener listener = (ListenerBuilder.Listener) obj;
                listenerFactory.listenerList.add(listener);
                break;
        }
    }

    static class ListenerFactory {
        private String type;
        private List<ListenerBuilder.Listener> listenerList = new ArrayList<>();

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ListenerBuilder.Listener> getListenerList() {
            return listenerList;
        }
    }
}
