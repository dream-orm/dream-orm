package com.moxa.dream.system.inject.factory;

import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.mapped.MethodInfo;

public interface InjectFactory {
    void injects(Class<? extends Inject>[] injects);

    void inject(MethodInfo methodInfo);
}
