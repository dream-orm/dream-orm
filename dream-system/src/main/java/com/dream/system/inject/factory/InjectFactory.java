package com.dream.system.inject.factory;

import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

import java.util.function.Predicate;

public interface InjectFactory {
    void injects(Inject... injects);

    void inject(MethodInfo methodInfo, Predicate<Inject> predicate);

    <T extends Inject> T getInject(Class<T> inject);
}