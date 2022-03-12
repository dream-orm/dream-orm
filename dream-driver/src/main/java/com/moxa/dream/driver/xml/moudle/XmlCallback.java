package com.moxa.dream.driver.xml.moudle;

import com.moxa.dream.driver.xml.builder.XMLBuilder;

public interface XmlCallback {

    XMLBuilder startDocument(XmlHandler xmlHandler);

    void endDocument(Object value);
}
