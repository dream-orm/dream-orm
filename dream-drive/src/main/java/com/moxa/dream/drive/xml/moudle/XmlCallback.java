package com.moxa.dream.drive.xml.moudle;

import com.moxa.dream.drive.xml.builder.XMLBuilder;

public interface XmlCallback {

    XMLBuilder startDocument(XmlHandler xmlHandler);

    void endDocument(Object value);
}
