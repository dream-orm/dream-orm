package com.moxa.dream.drive.action;

import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

public class ServiceAction implements Action {
    private Action action;
    private String property;

    public ServiceAction(String property, String type) {
        Class<? extends Action> actionType = ReflectUtil.loadClass(type);
        Action action = ReflectUtil.create(actionType);
        this.property = property;
        this.action = action;
    }

    public ServiceAction(String property, Action action) {
        this.property = property;
        this.action = action;
    }

    @Override
    public Object doAction(Executor executor, Object arg) throws Exception {
        Object result = action.doAction(executor, arg);
        if (!ObjectUtil.isNull(property)) {
            ObjectWrapper.wrapper(arg).set(property, result);
        }
        return null;
    }
}
