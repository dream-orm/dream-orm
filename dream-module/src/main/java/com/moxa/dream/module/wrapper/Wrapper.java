package com.moxa.dream.module.wrapper;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.module.mapper.MethodInfo;

public interface Wrapper {
    void wrapper(PackageStatement statement, MethodInfo methodInfo);
}