package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public interface Invoker {
    String DEFAULT_NAMESPACE = "default";

    /**
     * @param assist 翻译辅助工具
     * @函数初始化
     */
    default void init(Assist assist) {

    }

    /**
     * 翻译@函数抽象树
     *
     * @param invokerStatement @函数抽象树
     * @param assist           翻译辅助工具
     * @param toSQL            翻译目标SQL的方言
     * @param invokerList      正在执行当前抽象树的所有@函数
     * @return 对应的SQL
     * @throws AntlrException
     */
    String invoke(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException;

    /**
     * 当前@函数关联的处理类
     *
     * @return
     */
    Handler[] handlers();

    /**
     * 决定@函数有效
     *
     * @return
     */
    boolean isAccessible();

    /**
     * 设置@函数是否有效
     *
     * @param accessible
     */
    void setAccessible(boolean accessible);

    /**
     * 出于线程安全考虑，创建新的@函数，一般情况返回this即可
     *
     * @return
     */
    Invoker newInstance();

    /**
     * @return
     * @函数对应的命名空间
     */
    default String namespace() {
        return DEFAULT_NAMESPACE;
    }

    /**
     * 当前@函数对应的方法名
     *
     * @return
     */
    String function();
}
