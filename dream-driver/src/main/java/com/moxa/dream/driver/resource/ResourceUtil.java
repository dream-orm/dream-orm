package com.moxa.dream.driver.resource;

import java.io.InputStream;
import java.util.List;

public class ResourceUtil {

    private static final ResourceLoader resourceLoader = new DefaultResourceLoader();

    public static InputStream getResourceAsStream(String resource) {
        return resourceLoader.getResourceAsStream(resource);
    }


    public static List<Class> getResourceAsClass(String name) {
        try {
            return resourceLoader.getResourceAsClass(name);
        } catch (Exception e) {
            throw new ResourceException(e);
        }
    }
}
