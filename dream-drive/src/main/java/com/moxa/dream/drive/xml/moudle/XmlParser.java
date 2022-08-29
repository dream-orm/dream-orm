package com.moxa.dream.drive.xml.moudle;

import com.moxa.dream.util.exception.DreamRunTimeException;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParser {
    private final SAXParser saxParser;

    public XmlParser() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }

    public void parse(InputSource inputSource, XmlCallback xmlCallback) {
        try {
            saxParser.parse(inputSource, new XmlHandler(xmlCallback));
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }
}
