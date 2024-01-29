package com.dream.helloworld.h2;

import com.dream.flex.annotation.FlexAPT;
import com.dream.system.inject.Inject;
import com.dream.system.table.ColumnInfo;
import com.dream.template.sequence.AbstractSequence;
import com.dream.template.sequence.Sequence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@FlexAPT
@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

//    /**
//     * 配置监听器方案一
//     * 配置SQL输出
//     *
//     * @return
//     */
//    @Bean
//    public Listener[] listeners() {
//        return new Listener[]{new DebugListener()};
//    }

//    /**
//     * 配置监听器方案二：工厂配置
//     * @return
//     */
//    @Bean
//    public ListenerFactory listenerFactory(){
//        ListenerFactory listenerFactory=new DefaultListenerFactory();
//        listenerFactory.listeners(new DebugListener());
//        return listenerFactory;
//    }


//    /**
//     * 默认使用MySQL方言
//     *
//     * @return
//     */
//    @Bean
//    public ToSQL toSQL() {
//        return new ToMYSQL();
//    }
//
//    /**
//     * 配置方言方案二：工厂配置
//     *
//     * @return
//     */
//    @Bean
//    public DialectFactory dialectFactory() {
//        DefaultDialectFactory defaultDialectFactory = new DefaultDialectFactory();
//        defaultDialectFactory.setToSQL(new ToMYSQL());
//        return defaultDialectFactory;
//    }


//    /**
//     * 开启插件方式一
//     *
//     * @return
//     */
//    @Bean
//    public InjectFactory injectFactory() {
//        InjectFactory injectFactory = new DefaultInjectFactory();
//        /**
//         * 注入插件工厂插件
//         */
//        injectFactory.injects(new Inject[0]);
//        return injectFactory;
//    }

    /**
     * 开启插件方式二
     *
     * @return
     */
    @Bean
    public Inject[] injects() {
        /**
         * 开启插件
         */
        return new Inject[]{
//                /**
//                 * 开启关键字插件
//                */
//                new TransformInject(new InterceptTransformHandler("keyword.txt")),
//                /**
//                 * 开启数据权限插件
//                */
//                new PermissionInject(new PermissionHandler() {
//                    @Override
//                    public boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo) {
//                        return tableInfo.getFieldName("field_id") != null;
//                    }
//
//                    @Override
//                    public String getPermission(String alias) {
//                        return alias + ".dept_id=1";
//                    }
//                }),
//                /**
//                 * 开启多租户插件
//                */
//                new TenantInject(new TenantHandler() {
////                    public boolean isTenant(MethodInfo methodInfo, TableInfo tableInfo) {
////                        return tableInfo.getFieldName(getTenantColumn()) != null;
////                    }
//
////                    @Override
////                    public String getTenantColumn() {
////                        return "tenant_id";
////                    }
//
//                    @Override
//                    public Object getTenantObject() {
//                        return 1;
//                    }
//                }),
//                /**
//                 * 开启逻辑删除插件
//                */
//                new LogicInject(new LogicHandler() {
//                    @Override
//                    public boolean isLogic(MethodInfo methodInfo, TableInfo tableInfo) {
//                        return tableInfo.getFieldName(getLogicColumn()) != null;
//                    }
//
//                    @Override
//                    public String getDeletedValue() {
//                        return "1";
//                    }
//
//                    @Override
//                    public String getLogicColumn() {
//                        return "del_flag";
//                    }
//                }),
//                /**
//                 * 开启动态表名插件
//                */
//                new DynamicInject(new DynamicHandler() {
//                    @Override
//                    public boolean isDynamic(MethodInfo methodInfo, String table) {
//                        return true;
//                    }
//
//                    @Override
//                    public String process(String table) {
//                        System.out.println("修改表名：" + table);
//                        return table;
//                    }
//                }),
        };
    }


//    /**
//     * 开启缓存方式一
//     *
//     * @return
//     */
//    @Bean
//    public Cache cache() {
//        return new MemoryCache();
//    }
//
//    /**
//     * 开启缓存方式二
//     *
//     * @return
//     */
//    @Bean
//    public CacheFactory cacheFactory() {
//        DefaultCacheFactory cacheFactory=new DefaultCacheFactory();
//        cacheFactory.setCache(new MemoryCache());
//        return cacheFactory;
//    }


//    /**
//     * 配置扫描的table和mapper路径
//     *
//     * @return
//     */
//    @Bean
//    public ConfigurationBean configurationBean() {
//        String packageName = this.getClass().getPackage().getName();
//        List<String> pathList = Arrays.asList(packageName);
//        ConfigurationBean configurationBean = new ConfigurationBean(pathList, pathList);
//        return configurationBean;
//    }
//
//    @Bean
//    public FlexDialect flexDialect(ToSQL toSQL) {
//        return new DefaultFlexDialect(toSQL).tenantHandler(new TenantHandler() {
//            @Override
//            public boolean isTenant(MethodInfo methodInfo, String table) {
//                return true;
//            }
//
//            @Override
//            public Object getTenantObject() {
//                return "1";
//            }
//        }).permissionHandler(new PermissionHandler() {
//            @Override
//            public boolean isPermissionInject(MethodInfo methodInfo, String table) {
//                return true;
//            }
//
//            @Override
//            public String getPermission(String alias) {
//                return "1=1";
//            }
//        }).logicHandler(new LogicHandler() {
//            @Override
//            public boolean isLogic(MethodInfo methodInfo, String table) {
//                return true;
//            }
//
//            @Override
//            public String getLogicColumn() {
//                return "del_flag";
//            }
//        });
//    }

    /**
     * 主键序列
     *
     * @return
     */
    @Bean
    public Sequence sequence() {
        return new AbstractSequence() {
            @Override
            protected Object sequence(ColumnInfo columnInfo) {
                return String.valueOf(System.currentTimeMillis()).hashCode();
            }
        };
    }
}
