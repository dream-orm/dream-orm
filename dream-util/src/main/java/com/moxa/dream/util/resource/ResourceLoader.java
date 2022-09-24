package com.moxa.dream.util.resource;

import java.io.InputStream;
import java.util.List;

public interface ResourceLoader {
    InputStream getResourceAsStream(String name);

    List<Class> getResourceAsClass(String name) throws Exception;

    ClassLoader getClassLoader();
}
