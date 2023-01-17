package com.moxa.dream.template.resulthandler;

import com.moxa.dream.util.common.ObjectUtil;

import java.util.*;

public class TreeUtil {
    public static List<? extends Tree> toTree(Collection<? extends Tree> treeList) {
        if (ObjectUtil.isNull(treeList)) {
            return new ArrayList<>();
        }
        Map<Object, Tree> treeMap = new HashMap<>();
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
                List<Tree> children = parentTree.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parentTree.setChildren(children);
                }
                children.add(tree);
            }
        }
    }

    public static List<? extends Tree> deepTree(List<? extends Tree> treeList, List<?> treeIdList) {
        List<Tree> resultTreeList = new ArrayList<>();
        for (Tree tree : treeList) {
            Object treeId = tree.getTreeId();
            if (treeIdList.contains(treeId)) {
                resultTreeList.add(tree);
            } else {
                List<? extends Tree> children = tree.getChildren();
                if (!ObjectUtil.isNull(children)) {
                    resultTreeList.addAll(deepTree(children, treeIdList));
                }
            }
        }
        return resultTreeList;
    }
}
