package com.moxa.dream.util.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ResourceUtil {

    private static ResourceLoader resourceLoader = new ResourceLoader();

    public static InputStream getResourceAsStream(String resource) {
        return getResourceAsStream(Thread.currentThread().getContextClassLoader(), resource);
    }

    public static InputStream getResourceAsStream(ClassLoader loader, String resource) {
        List<InputStream> inputStreamList = resourceLoader.loadResourceAsList(resource, new ResourceHandler<InputStream>() {

            @Override
            public InputStream doHandler(String resourcePath) {
                return loader.getResourceAsStream(resourcePath);
            }

            @Override
            public List<String> goHandler(String resourcePath, ClassLoader classLoader) {
                return new ArrayList<>();
            }
        }, loader);
        if (inputStreamList.size() != 1)
            throw new ResourceException("The path address '" + resource + "' is not a file");
        return inputStreamList.get(0);
    }

    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getResourceAsStream(resource)) {
            props.load(in);
        }
        return props;
    }

    public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getResourceAsStream(loader, resource)) {
            props.load(in);
        }
        return props;
    }

    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getUrlAsStream(urlString)) {
            props.load(in);
        }
        return props;
    }

    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }


    public static List<Class> getResourceAsClass(String resource) {
        return getResourceAsClass(resource, Thread.currentThread().getContextClassLoader());
    }

    public static List<Class> getResourceAsClass(String resource, ClassLoader classLoader) {
        return resourceLoader.loadResourceAsList(resource, new ResourceHandler<Class>() {

            @Override
            public Class doHandler(String resourcePath) {
                try {
                    if (resourcePath.endsWith(".class")) {
                        resourcePath = resourcePath.substring(0, resourcePath.lastIndexOf(".")).replace("/", ".");
                        return classLoader.loadClass(resourcePath);
                    } else
                        return null;
                } catch (ClassNotFoundException e) {
                    return null;
                }
            }

            @Override
            public List<String> goHandler(String resourcePath, ClassLoader classLoader) {
                InputStream resourceAsStream = classLoader.getResourceAsStream(resourcePath);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
                String resourceLine;
                List<String> resourceList = new ArrayList<>();
                try {
                    while ((resourceLine = bufferedReader.readLine()) != null) {
                        InputStream stream = classLoader.getResourceAsStream(resourcePath + "/" + resourceLine);
                        if (stream == null) {
                            resourceList.clear();
                            break;
                        } else
                            resourceList.add(resourcePath + "/" + resourceLine);
                    }
                } catch (IOException e) {
                    throw new ResourceException(e);
                }
                return resourceList;
            }
        }, classLoader);
    }

}
