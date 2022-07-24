package com.moxa.dream.driver.resource;

import com.moxa.dream.util.common.ObjectUtil;

import java.io.File;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

public class DefaultResourceLoader implements ResourceLoader {


    @Override
    public InputStream getResourceAsStream(String name) {
        return getClassLoader().getResourceAsStream(name);
    }

    @Override
    public List<Class> getResourceAsClass(String name) throws Exception {
        List<Class> classList = new ArrayList<>();
        ClassLoader classLoader = getClassLoader();
        Enumeration<URL> resources = classLoader.getResources(name);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            switch (protocol) {
                case "file":
                    classList.addAll(getClassFromFile(name, url));
                    break;
                case "jar":
                    classList.addAll(getClassFromJar(url));
                    break;
            }
        }
        return classList;
    }

    private List<Class> getClassFromJar(URL url) throws Exception {
        List<Class> classList = new ArrayList<>();
        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof JarURLConnection) {
            JarURLConnection jarURLConnection = (JarURLConnection) urlConnection;
            String name = jarURLConnection.getJarEntry().getName();
            Enumeration<JarEntry> entries = jarURLConnection.getJarFile().entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String spec = jarEntry.getName();
                if (spec.startsWith(name) && spec.endsWith(".class")) {
                    classList.add(getClassLoader().loadClass(spec.substring(0, spec.length() - 6).replace("/", ".")));
                }
            }
        }
        return classList;
    }

    public List<Class> getClassFromFile(String pkg, URL url) throws Exception {
        List<Class> classList = new ArrayList<>();
        File file = new File(url.toURI());
        File[] files = file.listFiles();
        if (!ObjectUtil.isNull(files)) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    classList.addAll(getClassFromFile(pkg + "/" + files[i].getName(), files[i].toURI().toURL()));
                } else if (files[i].isFile()) {
                    String name = files[i].getName();
                    if (name.endsWith(".class")) {
                        classList.add(getClassLoader().loadClass(pkg.replace("/", ".") + "." + name.substring(0, name.length() - 6)));
                    }
                }
            }
        }
        return classList;
    }

    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
