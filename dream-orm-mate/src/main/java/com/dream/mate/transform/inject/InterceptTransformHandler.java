package com.dream.mate.transform.inject;

import com.dream.antlr.invoker.Invoker;
import com.dream.system.antlr.invoker.ForEachInvoker;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.antlr.invoker.RepInvoker;
import com.dream.util.common.LowHashSet;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.resource.ResourceUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class InterceptTransformHandler implements TransformHandler {
    private Set<String> filterSet;

    public InterceptTransformHandler(String resource) {
        InputStream inputStream = ResourceUtil.getResourceAsStream(resource);
        Set<String> filterSet = new LowHashSet();
        if (inputStream != null) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            try {
                while ((length = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                String keyword = result.toString();
                StringTokenizer tokenizer = new StringTokenizer(keyword);
                while (tokenizer.hasMoreTokens()) {
                    filterSet.add(tokenizer.nextToken());
                }
            } catch (Exception e) {
                throw new DreamRunTimeException("读取" + resource + "失败", e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
        } else {
            throw new DreamRunTimeException(resource + "不存在");
        }
        this.filterSet = filterSet;
    }

    public InterceptTransformHandler(Set<String> filterSet) {
        this.filterSet = filterSet;
    }

    @Override
    public boolean intercept(String column, List<Invoker> invokerList) {
        if (filterSet.contains(column)) {
            for (int i = invokerList.size() - 1; i >= 0; i--) {
                Invoker invoker = invokerList.get(i);
                if (invoker instanceof MarkInvoker || invoker instanceof RepInvoker || invoker instanceof ForEachInvoker) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
