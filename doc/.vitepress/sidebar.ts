import {DefaultTheme} from 'vitepress';

const sidebar: DefaultTheme.Sidebar = {
    '/': [
        {
            text: '简介',
            items: [
                {
                    text: 'dream-orm介绍',
                    link: '/guide/',
                },
                {
                    text: '快速开始',
                    link: '/guide/quick-start',
                },
                {
                    text: '性能对比',
                    link: '/guide/benchmark',
                },
                {
                    text: '支持的数据库类型',
                    link: '/guide/database',
                },
                {
                    text: '交流群',
                    link: '/guide/exchange',
                }
            ],
        },
        {
            text: '基础功能',
            items: [
                {
                    text: '模板操作',
                    link: '/basic/template',
                },
                {
                    text: '链式操作',
                    link: '/basic/flex',
                },
                {
                    text: '链式强化操作',
                    link: '/basic/flex-chain',
                },
                {
                    text: 'mapper接口操作',
                    link: '/basic/mapper',
                },
                {
                    text: '不翻译SQL操作',
                    link: '/basic/jdbc',
                },
                {
                    text: '核心注解',
                    link: '/basic/annotation',
                },
                {
                    text: '内置@函数',
                    link: '/basic/function',
                },
                {
                    text: 'IService',
                    link: 'basic/service'
                }
            ],
        },
        {
            text: '高级功能',
            items: [
                {
                    text: '方言',
                    link: '/advance/dialect'
                },
                {
                    text: '监听器',
                    link: '/advance/listener'
                },
                {
                    text: '主键策略',
                    link: '/advance/sequence'
                },
                {
                    text: '数据缓存',
                    link: '/advance/cache'
                },
                {
                    text: '关键字插件',
                    link: '/advance/transform'
                },
                {
                    text: '数据权限插件',
                    link: '/advance/permission'
                },
                {
                    text: '逻辑删除插件',
                    link: '/advance/logic-delete'
                },
                {
                    text: '多租户插件',
                    link: '/advance/tenant'
                },
                {
                    text: '动态表名插件',
                    link: '/advance/dynamic'
                },
                {
                    text: '动态数据源',
                    link: '/advance/datasource'
                },
                {
                    text: '代理插件',
                    link: '/advance/proxy-plugin'
                },
                {
                    text: '映射拦截',
                    link: '/advance/extractor'
                }
            ]
        },
        {
            text: '扩充功能',
            items: [
                {
                text: 'TdEngine时序数据库',
                link: '/expand/tdengine'
            }
            ]
        }
    ],
    '/question': [
        {
            text: '问题',
            link: '/question/'
        }
    ],
    '/neighbor': [
        {
            text: '问题',
            link: '/neighbor/'
        }
    ],
    '/change-log': [
        {
            text: '问题',
            link: '/change-log/'
        }
    ]
};
export default sidebar;
