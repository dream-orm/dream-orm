package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
import org.xml.sax.Attributes;

import java.util.List;

public class EachBuilder extends XMLBuilder {

    private Each each;

    public EachBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.EACH:
                each = XmlUtil.applyAttributes(Each.class, attributes);
                break;
            case XmlConstant.METHODREF:
                MethodRefBuilder methodRefXmlBuilder = new MethodRefBuilder(workHandler);
                methodRefXmlBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.ARGLIST:
                ArgListBuilder argsXmlBuilder = new ArgListBuilder(workHandler);
                argsXmlBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.EACH);
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return each;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.METHODREF:
                each.methodRef = (MethodRefBuilder.MethodRef) obj;
                break;
            case XmlConstant.ARGLIST:
                each.argList = (List<ArgBuilder.Arg>) obj;
                break;
        }
    }

    static class Each {
        private MethodRefBuilder.MethodRef methodRef;
        private List<ArgBuilder.Arg> argList;

        public MethodRefBuilder.MethodRef getMethodRef() {
            return methodRef;
        }

        public List<ArgBuilder.Arg> getArgList() {
            return argList;
        }
    }
}
