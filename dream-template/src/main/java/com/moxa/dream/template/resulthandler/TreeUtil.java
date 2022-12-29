package com.moxa.dream.template.resulthandler;

import com.moxa.dream.util.common.ObjectUtil;

import java.util.*;

public class TreeUtil {
    public static List<? extends Tree> toTree(Collection<? extends Tree> treeList) {
        if (ObjectUtil.isNull(treeList)) {
            return new ArrayList<>();
        }
        Map<String, Tree> treeMap = new HashMap<>();
        for (Tree tree : treeList) {
            treeMap.put(tree.getTreeId(), tree);
        }
        List<Tree> parentList = new ArrayList();
        for (Tree tree : treeList) {
            createTree(tree, treeMap, parentList);
        }
        return parentList;
    }

    private static void createTree(Tree tree, Map<String, Tree> treeMap, List<Tree> parentList) {
        String parentId = tree.getParentId();
        if (parentId == null) {
            parentList.add(tree);
        } else {
            Tree parentTree = treeMap.get(parentId);
            if (parentTree == null) {
                parentList.add(tree);
            } else {
                List children = parentTree.getChildren();
                children.add(tree);
            }
        }
    }

}
