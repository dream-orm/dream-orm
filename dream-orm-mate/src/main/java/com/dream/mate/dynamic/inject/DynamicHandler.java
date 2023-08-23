package com.dream.mate.dynamic.inject;

import com.dream.system.config.MethodInfo;

public interface DynamicHandler {
    /**
     * 判断是否应用动态表名
     *
     * @param methodInfo mapper方法详尽信息
     * @param table      数据表
     * @return
     */
    boolean isDynamic(MethodInfo methodInfo, String table);

    /**
     * 根据当前表名，返回新的表名
     *
     * @param table 当前表名
     * @return 根据当前表名，返回新的表名
     */
    String process(String table);
}
