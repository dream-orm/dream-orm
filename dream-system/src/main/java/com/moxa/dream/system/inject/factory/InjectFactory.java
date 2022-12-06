package com.moxa.dream.system.inject.factory;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;

public interface InjectFactory {
    void injects(Inject... injects);

    void inject(MethodInfo methodInfo);

    <T extends Inject> T getInject(Class<T> inject);
}
