package com.moxa.dream.module.config;

import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.builder.config.ConfigBuilder;
import com.moxa.dream.module.xml.builder.config.ConfigurationBuilder;
import com.moxa.dream.module.xml.moudle.XmlCallback;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.module.xml.moudle.XmlParser;
import com.moxa.dream.util.common.ObjectUtil;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;

public class DefaultConfigurationFactory implements ConfigurationFactory {
    private DefaultConfig defaultConfig;

    @Override
    public void setDefaultConfig(DefaultConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    @Override
    public Configuration getConfiguration() {
        return new ConfigBuilder(defaultConfig).builder();
    }

    @Override
    public Configuration getConfiguration(InputStream inputStream) {
        ObjectUtil.requireNonNull(inputStream, "Property 'inputStream' is required");
        return getConfiguration(new InputSource(inputStream));
    }

    @Override
    public Configuration getConfiguration(Reader reader) {
        ObjectUtil.requireNonNull(reader, "Property 'reader' is required");
        return getConfiguration(new InputSource(reader));
    }

    public Configuration getConfiguration(InputSource inputSource) {
        ObjectUtil.requireNonNull(inputSource, "Property 'inputSource' is required");
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
        return configuration[0];
    }
}
