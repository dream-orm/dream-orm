package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class ArgListBuilder extends XMLBuilder {
    private List<ArgBuilder.Arg> argList;

    public ArgListBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.ARGLIST:
                argList = new ArrayList<>();
                break;
            case XmlConstant.ARG:
                ArgBuilder argBuilder = new ArgBuilder(workHandler);
                argBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.ARGLIST);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return argList;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case "arg":
                argList.add((ArgBuilder.Arg) obj);
                break;
        }
    }
}
