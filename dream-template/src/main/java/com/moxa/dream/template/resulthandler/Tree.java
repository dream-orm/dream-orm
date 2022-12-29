package com.moxa.dream.template.resulthandler;

import java.util.List;

public interface Tree {
    String getTreeId();

    String getParentId();

    List<Tree> getChildren();
}
