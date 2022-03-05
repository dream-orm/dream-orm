package com.moxa.dream.module.xml.builder;

import com.moxa.dream.module.xml.exception.XmlException;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import org.xml.sax.Attributes;

public abstract class XMLBuilder {
    protected XmlHandler workHandler;

    public XMLBuilder(XmlHandler workHandler) {
        this.workHandler = workHandler;
        workHandler.push(this);
    }

    public abstract void startElement(String uri, String localName, String qName, Attributes attributes);

    public abstract void characters(String s);

    public abstract Object endElement(String uri, String localName, String qName);

    public abstract void builder(String uri, String localName, String qName, Object obj);

    protected void throwXmlException(String uri, String localName, String qName, Attributes attributes, String name) {
        throw new XmlException("XML节点'" + name + "'解析'" + qName + "'失败");
    }
}
