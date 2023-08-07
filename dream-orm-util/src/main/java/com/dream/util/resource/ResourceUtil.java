package com.dream.util.resource;

import com.dream.util.exception.DreamRunTimeException;

import java.io.InputStream;
import java.util.List;

public class ResourceUtil {

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    public static InputStream getResourceAsStream(String resource) {
        return resourceLoader.getResourceAsStream(resource);
    }


    public static List<Class> getResourceAsClass(String name) {
        try {
            return resourceLoader.getResourceAsClass(name);
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }
}
