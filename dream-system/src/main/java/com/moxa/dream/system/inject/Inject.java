package com.moxa.dream.system.inject;

import com.moxa.dream.system.config.MethodInfo;

public interface Inject {
    /**
     * 功能扩展核心，对methodInfo进行自定义注入
     *
     * @param methodInfo mapper方法详尽信息
     */
    void inject(MethodInfo methodInfo);
}
