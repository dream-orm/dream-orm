package com.moxa.dream.module.xml.moudle;

import com.moxa.dream.module.xml.builder.XMLBuilder;

public interface XmlCallback {

    XMLBuilder startDocument(XmlHandler xmlHandler);

    void endDocument(Object value);
}
