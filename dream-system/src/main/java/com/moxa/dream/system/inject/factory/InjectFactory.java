package com.moxa.dream.system.inject.factory;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;

public interface InjectFactory {
    void injects(Class<? extends Inject>[] injects);

    void inject(MethodInfo methodInfo);
}
