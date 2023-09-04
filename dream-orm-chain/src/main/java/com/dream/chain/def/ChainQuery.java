package com.dream.chain.def;

import com.dream.system.config.Page;
import com.dream.util.tree.Tree;

import java.util.List;

public interface ChainQuery {
    <T> T one(Class<T> type);

    <T> List<T> list(Class<T> type);

    <T extends Tree> List<T> tree(Class<T> type);

    <T> Page<T> page(Class<T> type, Page page);

    boolean exists();
}
