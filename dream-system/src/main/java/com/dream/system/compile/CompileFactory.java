package com.dream.system.compile;

import com.dream.antlr.smt.PackageStatement;

public interface CompileFactory {

    /**
     * 编译SQL为对应的抽象树
     *
     * @param sql
     * @return
     * @throws Exception
     */
    PackageStatement compile(String sql) throws Exception;
}
