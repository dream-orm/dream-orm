import {DefaultTheme} from 'vitepress';

const nav: DefaultTheme.NavItem[] = [
    {
        text: '首页',
        link: '/'
    },
    {
        text: '帮助文档',
        link: '/guide/'
    },
    {
        text: '常见问题',
        link: '/question/'
    },
    {
        text: '生态',
        link: '/ecology/'
    },
    {
        text: '升级日志',
        link: '/change-log/'
    },
    {
        text: '用户',
        link: '/users/'
    },
    {
        text: '获取源码',
        items: [
            {
                text: 'github 源码',
                link: 'https://github.com/dream-orm/dream-orm',
            },
            {
                text: 'gitee 源码',
                link: 'https://gitee.com/moxiaoai/dream-orm',
            }
        ],
    }
];
export default nav;
