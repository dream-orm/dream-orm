package com.moxa.dream.template.resulthandler;

import java.util.List;

public interface Tree<T> {
    T getTreeId();

    T getParentId();

    List<? extends Tree> getChildren();

    void setChildren(List<? extends Tree> children);
}
