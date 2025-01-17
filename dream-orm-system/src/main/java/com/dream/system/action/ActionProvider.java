package com.dream.system.action;

import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.action.LoopAction;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.statementhandler.StatementHandler;

import java.util.Collection;

/**
 * 映射接口方法与SQL
 */
public interface ActionProvider {

    /**
     * SQL语句
     *
     * @return
     */
    String sql();

    /**
     * SQL执行前行为
     *
     * @return
     */
    default InitAction initAction() {
        return null;
    }

    /**
     * SQL结果遍历行为
     *
     * @return
     */
    default LoopAction loopAction() {
        return null;
    }

    /**
     * SQL执行后行为
     *
     * @return
     */
    default DestroyAction destroyAction() {
        return null;
    }

    /**
     * 返回的类型
     *
     * @return
     */
    default Class<? extends Collection> rowType() {
        return null;
    }

    /**
     * 返回的类型
     *
     * @return
     */
    default Class<?> colType() {
        return null;
    }

    /**
     * 查询超时设置
     *
     * @return
     */
    default Integer timeOut() {
        return null;
    }

    /**
     * Page地址
     *
     * @return
     */
    default String page() {
        return null;
    }

    /**
     * SQL操作最终类
     *
     * @return
     */
    default StatementHandler statementHandler() {
        return null;
    }

    /**
     * 映射数据库查询数据与java对象
     *
     * @return
     */
    default ResultSetHandler resultSetHandler() {
        return null;
    }
}
