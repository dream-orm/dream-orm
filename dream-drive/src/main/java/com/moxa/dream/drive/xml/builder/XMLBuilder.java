package com.moxa.dream.drive.xml.builder;

import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.util.exception.DreamRunTimeException;
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
        throw new DreamRunTimeException("XML节点'" + name + "'解析'" + qName + "'失败");
    }
}
