npm run build
ossutil rm oss://dream-orm/ -rf
ossutil cp -rf .vitepress/dist  oss://dream-orm/
