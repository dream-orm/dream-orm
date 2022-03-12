package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public class EachListBuilder extends XMLBuilder {
    private EachList eachList;

    public EachListBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.EACHLIST:
                eachList = XmlUtil.applyAttributes(EachList.class, attributes);
                break;
            case XmlConstant.EACH:
                EachBuilder eachBuilder = new EachBuilder(workHandler);
                eachBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.EACHLIST);
        }
    }

    @Override
    public void characters(String s) {

    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return eachList;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.EACH:
                EachBuilder.Each each = (EachBuilder.Each) obj;
                eachList.addEachList(each);
                break;
        }
    }

    static class EachList {
        private List<EachBuilder.Each> eachList = new ArrayList<>();

        public void addEachList(EachBuilder.Each each) {
            eachList.add(each);
        }

        public List<EachBuilder.Each> getEachList() {
            return eachList;
        }
    }
}
