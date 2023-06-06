package com.moxa.dream.template.mapper;

import com.moxa.dream.system.annotation.PageQuery;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.Page;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SelectPageMapper extends SelectListMapper {
    private final String PAGE = "page";

    public SelectPageMapper(Session session) {
        super(session);
    }

    @Override
    public Object execute(Class<?> type, Object arg, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
        return execute(type, arg, null, methodInfoConsumer, mappedStatementConsumer);
    }

    public Object execute(Class<?> type, Object arg, Page page, Consumer<MethodInfo> methodInfoConsumer, Consumer<MappedStatement> mappedStatementConsumer) {
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
                        public boolean offset() {
                            return false;
                        }

                        @Override
                        public String value() {
                            return PAGE;
                        }
                    });
                    if (methodInfoConsumer != null) {
                        methodInfoConsumer.accept(methodInfo);
                    }
                    methodInfoMap.put(key, methodInfo);
                }
            }
        }
        List<?> result = (List<?>) super.execute(methodInfo, wrapArg(arg, page), mappedStatementConsumer);
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

