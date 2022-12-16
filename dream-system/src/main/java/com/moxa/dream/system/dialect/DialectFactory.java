package com.moxa.dream.system.dialect;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;

public interface DialectFactory {

    MappedStatement compile(MethodInfo methodInfo, Object arg) throws Exception;
}
