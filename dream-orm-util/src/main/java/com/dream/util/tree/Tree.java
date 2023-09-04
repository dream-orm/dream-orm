package com.dream.util.tree;

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
    List<? extends Tree> getChildrenOrNew();
}
