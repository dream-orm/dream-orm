# 主键策略

**`dream-orm`支持多主键。但只有模板插入时有效。**

**配置主键序列，一般继承[AbstractSequence](https://gitee.com/moxiaoai/dream-orm/blob/master/dream-orm-template/src/main/java/com/dream/template/sequence/AbstractSequence.java)接口。可根据字段信息自定义主键策略。**

```java
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
```

**配置好主键序列后，会看到日志输出依旧显示主键对应参数位置为空。这是由于监听器的执行顺序高于主键策略（SQL执行前行为）。其实主键已经生成啦。**
