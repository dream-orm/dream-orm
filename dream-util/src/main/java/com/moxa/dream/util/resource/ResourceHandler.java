package com.moxa.dream.util.resource;


import java.util.List;

public interface ResourceHandler<T> {

    T doHandler(String resourcePath);

    List<String> goHandler(String resourcePath, ClassLoader classLoader);
}
