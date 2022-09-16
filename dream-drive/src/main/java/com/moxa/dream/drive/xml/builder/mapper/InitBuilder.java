package com.moxa.dream.drive.xml.builder.mapper;

import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.moudle.XmlConstant;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class InitBuilder extends XMLBuilder {
    private Init init;

    public InitBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.INIT:
                init = XmlUtil.applyAttributes(Init.class, attributes);
                break;
            case XmlConstant.ACTION:
                ActionBuilder actionBuilder = new ActionBuilder(workHandler);
                actionBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.INIT);
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return init;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.ACTION:
                init.actionList.add((ActionBuilder.Action) obj);
                break;
        }
    }

    static class Init {
        private final List<ActionBuilder.Action> actionList = new ArrayList<>();

        public List<ActionBuilder.Action> getActionList() {
            return actionList;
        }
    }
}
