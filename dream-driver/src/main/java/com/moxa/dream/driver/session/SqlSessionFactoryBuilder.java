package com.moxa.dream.driver.session;

import com.moxa.dream.driver.config.DefaultConfig;
import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.builder.config.ConfigurationBuilder;
import com.moxa.dream.driver.xml.moudle.XmlCallback;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.moudle.XmlParser;
import com.moxa.dream.system.config.Configuration;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream) {
        return build(inputStream, null);
    }

    public SqlSessionFactory build(InputStream inputStream, DefaultConfig defaultConfig) {
        return build(new InputSource(inputStream), defaultConfig);
    }

    public SqlSessionFactory build(Reader reader) {
        return build(reader, null);
    }

    public SqlSessionFactory build(Reader reader, DefaultConfig defaultConfig) {
        return build(new InputSource(reader), defaultConfig);
    }

    public SqlSessionFactory build(InputSource inputSource, DefaultConfig defaultConfig) {
        XmlParser xmlParser = new XmlParser();
        final Configuration[] configuration = new Configuration[1];
        xmlParser.parse(inputSource, new XmlCallback() {
            @Override
            public XMLBuilder startDocument(XmlHandler xmlHandler) {
                return new ConfigurationBuilder(xmlHandler, defaultConfig);
            }

            @Override
            public void endDocument(Object value) {
                configuration[0] = (Configuration) value;
            }
        });
        return build(configuration[0]);
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
