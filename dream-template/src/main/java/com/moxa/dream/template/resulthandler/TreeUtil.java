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
        Set<String> recordSet = new HashSet<>();
        for (Tree tree : treeList) {
            createTree(tree.getTreeId(), treeMap, parentList, recordSet);
        }
        return parentList;
    }

    private static void createTree(String id, Map<String, Tree> treeMap, List<Tree> parentList, Set<String> recordSet) {
        if (!recordSet.contains(id)) {
            recordSet.add(id);
            Tree tree = treeMap.get(id);
            String parentId = tree.getParentId();
            if (parentId == null) {
                parentList.add(tree);
            } else {
                Tree parentTree = treeMap.get(parentId);
                if (parentTree == null) {
                    parentList.add(tree);
                } else {
                    parentTree.addChild(tree);
                    createTree(parentId, treeMap, parentList, recordSet);
                }
            }
        }
    }

}
