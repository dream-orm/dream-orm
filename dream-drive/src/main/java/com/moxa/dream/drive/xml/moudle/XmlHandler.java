package com.moxa.dream.drive.xml.moudle;

import com.moxa.dream.drive.xml.builder.XMLBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayDeque;

public class XmlHandler extends DefaultHandler {
    private final ArrayDeque<XMLBuilder> builderStack = new ArrayDeque<>();
    private final XmlCallback xmlCallback;
    private XMLBuilder xmlBuilder;

    public XmlHandler(XmlCallback xmlCallback) {
        this.xmlCallback = xmlCallback;
    }

    @Override
    public void startDocument() throws SAXException {
        xmlBuilder = xmlCallback.startDocument(this);
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {

        return super.resolveEntity(publicId, systemId);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        builderStack.peek().startElement(uri, localName, qName, attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        builderStack.peek().characters(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Object obj = builderStack.pop().endElement(uri, localName, qName);
        if (!builderStack.isEmpty())
            builderStack.peek().builder(uri, localName, qName, obj);
        else
            xmlCallback.endDocument(obj);
    }

    public void push(XMLBuilder xmlBuilder) {
        builderStack.push(xmlBuilder);
    }
}
