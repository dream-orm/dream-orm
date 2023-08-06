package com.dream.template.mapper;

import com.dream.system.annotation.PageQuery;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.config.Page;
import com.dream.system.core.session.Session;
import com.dream.system.table.factory.TableFactory;
import com.dream.util.common.ObjectUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPageMapper extends SelectListMapper {
    private final String PAGE = "page";

    public SelectPageMapper(Session session) {
        super(session);
    }

    @Override
    public Object execute(Class<?> type, Object arg) {
        return execute(type, arg, null);
    }

    public Object execute(Class<?> type, Object arg, Page page) {
        if (page == null) {
            page = new Page();
        }
        String key = getKey(type, arg);
        MethodInfo methodInfo = methodInfoMap.get(key);
        if (methodInfo == null) {
            synchronized (this) {
                methodInfo = methodInfoMap.get(key);
                if (methodInfo == null) {
                    Configuration configuration = this.session.getConfiguration();
                    TableFactory tableFactory = configuration.getTableFactory();
                    String tableName = getTableName(type);
                    methodInfo = getMethodInfo(configuration, tableFactory.getTableInfo(tableName), type, arg);
                    String id = getId();
                    if (!ObjectUtil.isNull(id)) {
                        methodInfo.setId(id);
                    }
                    methodInfo.set(PageQuery.class, new PageQuery() {
                        @Override
                        public Class<? extends Annotation> annotationType() {
                            return PageQuery.class;
                        }

                        @Override
                        public String value() {
                            return PAGE;
                        }
                    });
                    methodInfoMap.put(key, methodInfo);
                }
            }
        }
        List<?> result = (List<?>) super.execute(methodInfo, wrapArg(arg, page));
        page.setRows(result);
        return page;
    }

    protected Map<String, Object> wrapArg(Object arg, Page page) {
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put(DREAM_TEMPLATE_PARAM, arg);
        paramMap.put(PAGE, page);
        return paramMap;
    }
}

