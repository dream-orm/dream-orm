package com.dream.util.tree;

import com.dream.util.common.ObjectUtil;

import java.util.*;

public class TreeUtil {
    public static List<? extends Tree> toTree(Collection<? extends Tree> treeList) {
        if (ObjectUtil.isNull(treeList)) {
            return new ArrayList<>();
        }
        Map<Object, Tree> treeMap = new HashMap<>(treeList.size());
        for (Tree tree : treeList) {
            treeMap.put(tree.getTreeId(), tree);
        }
        List<Tree> parentList = new ArrayList();
        for (Tree tree : treeList) {
            createTree(tree, treeMap, parentList);
        }
        return parentList;
    }

    private static void createTree(Tree tree, Map<Object, Tree> treeMap, List<Tree> parentList) {
        Object parentId = tree.getParentId();
        if (parentId == null) {
            parentList.add(tree);
        } else {
            Tree parentTree = treeMap.get(parentId);
            if (parentTree == null) {
                parentList.add(tree);
            } else {
                List<Tree> children = parentTree.getChildrenOrNew();
                children.add(tree);
            }
        }
    }
}
