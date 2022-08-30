package com.moxa.dream.drive.mapper;

import com.moxa.dream.drive.resource.ResourceUtil;
import com.moxa.dream.drive.xml.builder.XMLBuilder;
import com.moxa.dream.drive.xml.builder.mapper.MapperInfoBuilder;
import com.moxa.dream.drive.xml.moudle.XmlCallback;
import com.moxa.dream.drive.xml.moudle.XmlHandler;
import com.moxa.dream.drive.xml.moudle.XmlParser;
import com.moxa.dream.drive.annotation.Mapper;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;
import org.xml.sax.InputSource;

import java.util.Map;


public class DefaultMapperFactory extends AbstractMapperFactory {
    public String getResource(Class mapperClass) {
        Mapper mapperAnnotation = (Mapper) mapperClass.getDeclaredAnnotation(Mapper.class);
        if (mapperAnnotation == null)
            return null;
        String resource = mapperAnnotation.value();
        return resource;
    }

    @Override
    protected void fillMethodInfoFromResource(Configuration configuration, Class type, Map<String, MethodInfo.Builder> builderMap) {
        String resource = getResource(type);
        if (!ObjectUtil.isNull(resource)) {
            InputSource inputSource = new InputSource(ResourceUtil.getResourceAsStream(resource));
            XmlParser xmlParser = new XmlParser();
            xmlParser.parse(inputSource, new XmlCallback() {
                @Override
                public XMLBuilder startDocument(XmlHandler xmlHandler) {
                    return new MapperInfoBuilder(configuration, xmlHandler, builderMap);
                }

                @Override
                public void endDocument(Object value) {
                }
            });
        }
    }
}
