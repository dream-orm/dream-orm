package com.moxa.dream.template.mapper;

import com.moxa.dream.system.annotation.PageQuery;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.factory.TableFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPageSqlMapper extends SelectListSqlMapper {
    private final String PARAM = "param";
    private final String PAGE = "page";
    private Map<String, MethodInfo> methodInfoMap = new HashMap<>();

    public SelectPageSqlMapper(Session session) {
        super(session);
    }

    @Override
    public Object execute(Class<?> type, Object arg) {
        return execute(type, arg, null);
    }

    public Object execute(Class<?> type, Object arg, Page page) {
        String paramTypeName = null;
        if (arg != null) {
            paramTypeName = arg.getClass().getName();
        }
        String keyName = type.getName() + ":" + paramTypeName;
        MethodInfo methodInfo = methodInfoMap.get(keyName);
        if (methodInfo == null) {
            synchronized (this) {
                methodInfo = methodInfoMap.get(keyName);
                if (methodInfo == null) {
                    Configuration configuration = this.session.getConfiguration();
                    TableFactory tableFactory = configuration.getTableFactory();
                    methodInfo = getMethodInfo(configuration, tableFactory, type, arg);
                    methodInfo.set(PageQuery.class, new PageQuery() {
                        @Override
                        public Class<? extends Annotation> annotationType() {
                            return PageQuery.class;
                        }

                        @Override
                        public boolean offset() {
                            return false;
                        }

                        @Override
                        public String value() {
                            return "page";
                        }
                    });
                    methodInfo.compile();
                    methodInfoMap.put(keyName, methodInfo);
                }
            }
        }
        List<?> result = (List<?>) session.execute(methodInfo, wrapArg(arg, page));
        page.setRows(result);
        return page;
    }

    protected Map<String, Object> wrapArg(Object arg, Page page) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PARAM, arg);
        paramMap.put(PAGE, page);
        return paramMap;
    }
}

