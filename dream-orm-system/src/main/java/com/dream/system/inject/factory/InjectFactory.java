package com.dream.system.inject.factory;

import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

public interface InjectFactory {
    void addInjects(Inject... injects);

    void inject(MethodInfo methodInfo);
}
