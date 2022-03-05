package com.moxa.dream.util.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ResourceLoader {

    public <T> List<T> loadResourceAsList(String resourcePath, ResourceHandler<T> resourceHandler, ClassLoader classLoader) {
        InputStream resourceAsStream = classLoader.getResourceAsStream(resourcePath);
        List<T> resourceList = new ArrayList<>();
        if (resourceAsStream == null && !resourcePath.startsWith("/")) {
            resourceAsStream = classLoader.getResourceAsStream("/" + resourcePath);
            if (resourceAsStream == null)
                return resourceList;
        }
        T resource = resourceHandler.doHandler(resourcePath);
        if (resource != null)
            resourceList.add(resource);
        List<String> golist = resourceHandler.goHandler(resourcePath, classLoader);
        if (golist != null && !golist.isEmpty()) {
            for (String go : golist) {
                resourceList.addAll(loadResourceAsList(go, resourceHandler, classLoader));
            }
        }
        return resourceList;
    }
}
