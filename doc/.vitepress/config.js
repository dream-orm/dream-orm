import nav from './nav';
import sidebar from './sidebar';

module.exports = {
    base: '/dream-orm/',
    title: 'dream-orm',
    description: '一个优雅、无依赖、高性能的orm框架',
    lang: 'zh-CN',
    head: createHead(),
    themeConfig: {
        logo: '/logo.png',
        editLinks: true,
        editLinkText: '为此页提供修改建议',
        lastUpdated: true,
        outline: {
            level: [2, 6],
            label: '章节'
        },
        nav: nav,
        sidebar: sidebar,
        footer: {
            message: '',
            copyright: '<a href="https://beian.miit.gov.cn/" target="_blank">苏ICP备2023027543号</a>'
        }
    }
};

function createHead() {
    return [
        ['meta', {name: 'author', content: 'moxa'}],
        [
            'meta',
            {
                name: 'keywords',
                content: 'dream-orm',
            },
        ],
        ['link', {rel: 'icon', type: 'image/svg+xml', href: '/logo.svg'}],
        [
            'meta',
            {
                name: 'viewport',
                content:
                    'width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no',
            },
        ],
        ['meta', {name: 'keywords', content: 'dream-orm-doc'}],
        ['link', {rel: 'icon', href: '/favicon.ico'}],
    ];
}
