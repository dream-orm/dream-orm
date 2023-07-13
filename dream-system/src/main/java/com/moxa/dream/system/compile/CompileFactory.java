package com.moxa.dream.system.compile;

import com.moxa.dream.antlr.smt.PackageStatement;

public interface CompileFactory {

    PackageStatement compile(String sql) throws Exception;
}
