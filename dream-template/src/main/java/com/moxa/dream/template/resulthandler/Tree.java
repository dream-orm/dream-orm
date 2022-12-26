package com.moxa.dream.template.resulthandler;

public interface Tree<T extends Tree> {
    String getTreeId();

    String getParentId();

    void addChild(T tree);
}
