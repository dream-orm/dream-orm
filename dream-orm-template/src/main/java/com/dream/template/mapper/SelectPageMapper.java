package com.dream.template.mapper;

import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.config.Page;
import com.dream.system.core.session.Session;
import com.dream.system.table.factory.TableFactory;
import com.dream.util.common.ObjectMap;

import java.util.List;
import java.util.Map;

public class SelectPageMapper extends SelectListMapper {
    private final String PAGE = "page";

    public SelectPageMapper(Session session) {
        super(session);
    }

    @Override
    public Object execute(String id, Class<?> type, Object arg) {
        return execute(id, type, arg, null);
    }

    public Object execute(String id, Class<?> type, Object arg, Page page) {
        if (page == null) {
            page = new Page();
        }

        MethodInfo methodInfo = mapperFactory.getMethodInfo(id);
        if (methodInfo == null) {
            synchronized (this) {
                methodInfo = mapperFactory.getMethodInfo(id);
                if (methodInfo == null) {
                    Configuration configuration = this.session.getConfiguration();
                    TableFactory tableFactory = configuration.getTableFactory();
                    String tableName = getTableName(type);
                    methodInfo = getMethodInfo(configuration, tableFactory.getTableInfo(tableName), type, arg);
                    methodInfo.setId(id);
                    methodInfo.setPage(PAGE);
                    mapperFactory.addMethodInfo(methodInfo);
                }
            }
        }
        List<?> result = (List<?>) super.execute(methodInfo, wrapArg(arg, page));
        page.setRows(result);
        return page;
    }

    protected Map<String, Object> wrapArg(Object arg, Page page) {
        ObjectMap objectMap = new ObjectMap(arg);
        objectMap.put(PAGE, page);
        return objectMap;
    }
}

