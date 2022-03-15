package com.moxa.dream.test.core;

import com.moxa.dream.module.core.listener.DeleteListener;
import com.moxa.dream.module.core.listener.InsertListener;
import com.moxa.dream.module.core.listener.QueryListener;
import com.moxa.dream.module.core.listener.UpdateListener;
import com.moxa.dream.module.mapped.MappedStatement;

public class DebugListener implements QueryListener, InsertListener, UpdateListener, DeleteListener {
    @Override
    public void before(MappedStatement mappedStatement) {
        System.out.println("before");
    }

    @Override
    public void afterReturn(Object result, MappedStatement mappedStatement) {
        System.out.println("afterReturn");
    }

    @Override
    public void exception(Exception e, MappedStatement mappedStatement) {
        System.out.println("exception");
    }

    @Override
    public void after(MappedStatement mappedStatement) {
        System.out.println("after");
    }
}
