package com.moxa.dream.template.resulthandler;

import java.util.List;

/**
 * 树形构建接口
 *
 * @param <T>
 */
public interface Tree<T> {
    /**
     * 唯一标识
     *
     * @return
     */
    T getTreeId();

    /**
     * 父类标识
     *
     * @return
     */
    T getParentId();

    /**
     * 当前子类
     *
     * @return
     */
    List<? extends Tree> getChildren();

    /**
     * 设置当前子类
     *
     * @param children 子类
     */
    void setChildren(List<? extends Tree> children);
}
