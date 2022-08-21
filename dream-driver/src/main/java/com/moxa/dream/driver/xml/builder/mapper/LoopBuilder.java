package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class LoopBuilder extends XMLBuilder {
    private Loop loop;

    public LoopBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.LOOP:
                loop = XmlUtil.applyAttributes(Loop.class, attributes);
                break;
            case XmlConstant.ACTION:
                ActionBuilder actionBuilder = new ActionBuilder(workHandler);
                actionBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.LOOP);
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return loop;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.ACTION:
                loop.actionList.add((ActionBuilder.Action)obj);
                break;
        }
    }

    static class Loop {
        private List<ActionBuilder.Action> actionList = new ArrayList<>();

        public List<ActionBuilder.Action> getActionList() {
            return actionList;
        }
    }
}
