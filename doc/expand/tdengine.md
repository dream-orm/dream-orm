# TdEngine时序数据库
支持TdEngine需要额外引入jar，主要原因，该数据库语法和其他数据库语法相差较大，而且存在太多特殊语法，因此专门做了适配。

```xml
<dependency>
    <groupId>io.github.dream-orm</groupId>
    <artifactId>dream-orm-tdengine</artifactId>
    <version>${dream-orm.version}</version>
</dependency>
```

## **必备步骤**

- **创建FlexTdMapper对象**

```java
@Bean
public FlexTdMapper flexTdMapper(Session session) {
    return new DefaultFlexTdMapper(session);
}
```

- **更换ResultSetHandler对象**

```java
@Bean
public ResultSetHandler resultSetHandler() {
    return new SimpleResultSetHandler();
}
```

## 特有函数

| 支持的特有函数      | 函数说明                                                     |
| ------------------- | ------------------------------------------------------------ |
| to_iso8601          | 将 UNIX 时间戳转换成为 ISO8601 标准的日期时间格式            |
| to_json             | 将字符串常量转换为 JSON 类型                                 |
| timediff            | 计算两个时间戳之间的差值                                     |
| timediff_1b         | 计算两个时间戳之间的差值，并近似到时间单位纳秒指定的精度     |
| timediff_1u         | 计算两个时间戳之间的差值，并近似到时间单位微秒指定的精度     |
| timediff_1a         | 计算两个时间戳之间的差值，并近似到时间单位毫秒指定的精度     |
| timediff_1s         | 计算两个时间戳之间的差值，并近似到时间单位秒指定的精度       |
| timediff_1m         | 计算两个时间戳之间的差值，并近似到时间单位分指定的精度       |
| timediff_1h         | 计算两个时间戳之间的差值，并近似到时间单位小时指定的精度     |
| timediff_1d         | 计算两个时间戳之间的差值，并近似到时间单位天指定的精度       |
| timediff_1w         | 计算两个时间戳之间的差值，并近似到时间单位周指定的精度       |
| apercentile         | 统计表/超级表中指定列的值的近似百分比分位数                  |
| apercentile_default | 统计表/超级表中指定列的值的近似百分比分位数并使用基于直方图算法进行计算 |
| apercentile_tDigest | 统计表/超级表中指定列的值的近似百分比分位数并使用t-digest算法计算分位数的近似结果 |
| elapsed             | 统计周期内连续的时间长度                                     |
| elapsed_1b          | 统计周期内连续的时间长度且单位时间是纳秒                     |
| elapsed_1u          | 统计周期内连续的时间长度且单位时间是微秒                     |
| elapsed_1a          | 统计周期内连续的时间长度且单位时间是毫秒                     |
| elapsed_1s          | 统计周期内连续的时间长度且单位时间是秒                       |
| elapsed_1m          | 统计周期内连续的时间长度且单位时间是分                       |
| elapsed_1h          | 统计周期内连续的时间长度且单位时间是小时                     |
| elapsed_1d          | 统计周期内连续的时间长度且单位时间是天                       |
| elapsed_1w          | 统计周期内连续的时间长度且单位时间是周                       |
| leastsquares        | 统计表中某列的值是主键（时间戳）的拟合直线方程               |
| spread              | 统计表中某列的最大值和最小值之差                             |
| stddev              | 统计表中某列的均方差                                         |
| hyperloglog         | 采用 hyperloglog 算法，返回某列的基数                        |
| bottom              | 统计表/超级表中某列的值最小 *k* 个非 NULL 值                 |
| first               | 统计表/超级表中某列的值最先写入的非 NULL 值                  |
| last                | 统计表/超级表中某列的值最后写入的非 NULL 值                  |
| last_row            | 返回表/超级表的最后一条记录                                  |
| mode                | 返回出现频率最高的值                                         |
| sample              | 获取数据的 k 个采样值                                        |
| tail                | 返回跳过最后 offset_val 个，然后取连续 k 个记录，不忽略 NULL 值 |
| top                 | 统计表/超级表中某列的值最大 *k* 个非 NULL 值                 |
| unique              | 返回该列数据首次出现的值                                     |
| csum                | 累加和                                                       |
| database            | 返回当前登录的数据库                                         |
| client_version      | 返回当前登录的数据库                                         |
| server_version      | 返回服务端版本                                               |
| server_status       | 检测服务端是否所有 dnode 都在线，如果是则返回成功，否则返回无法建立连接的错误 |
| today               | 返回客户端当日零时的系统时间                                 |
| timezone            | 返回客户端当前时区信息                                       |
| current_user        | 获取当前用户                                                 |



## 举例一：数据切分查询

**测试**

```java
/**
 * 数据切分查询
 * @throws Exception
 */
    @Test
    public void testPartitionBy() throws Exception {
        List<Map> list = flexTdMapper
            .select(max(col("current"))).from("meters").partitionBy("location").interval("10m").limit(1, 2).list(Map.class);
        System.out.println("查询结果：" + list);
    }
```

**控制台输出**

```tex
方法：null
语句：SELECT MAX(current) FROM meters  PARTITION BY(location) INTERVAL(10m) LIMIT ?,?
参数：[1, 100]
用时：2992ms
查询结果：[{max(current)=10.2}, {max(current)=10.2}]
```

## 举例二：时间窗口查询

**测试**

```java
    /**
     * 时间窗口查询
     * @throws Exception
     */
    @Test
    public void testSliding() {
        List<Map> list = flexTdMapper.select(max(col("current")))
            .from("meters").partitionBy("location").interval("10m").sliding("5m").limit(1, 2).list(Map.class);
        System.out.println("查询结果：" + list);
    }
```

**控制台输出**

```tex
方法：null
语句：SELECT MAX(current) FROM meters  PARTITION BY(location) INTERVAL(10m) SLIDING(5m) LIMIT ?,?
参数：[1, 2]
用时：3171ms
查询结果：[{max(current)=10.16}, {max(current)=10.16}, {max(current)=10.16}, {max(current)=10.2}, {max(current)=10.16}, {max(current)=10.16}, {max(current)=10.16}, {max(current)=10.16}, {max(current)=10.16}, {max(current)=10.16}, {max(current)=10.2}, {max(current)=10.16}]

```

## 举例三：状态窗口查询

**测试**

```java
/**
 * 测试状态窗口
 * @throws Exception
 */
@Test
public void testState(){
    List<Map> list = flexTdMapper.select("voltage").from("meters").state_window("voltage").limit(1, 2).list(Map.class);
    System.out.println("查询结果：" + list);
}
```

**控制台输出**

```tex
方法：null
语句：SELECT voltage FROM meters  STATE_WINDOW(`voltage`) LIMIT ?,?
参数：[1, 2]
用时：40184ms
查询结果：[{voltage=109}, {voltage=108}]
```

## 举例四：会话窗口查询

**测试**

```java
/**
 * 测试会话窗口
 * @throws Exception
 */
@Test
public void testSession(){
    List<Map> list = flexTdMapper.select(col("voltage"),first("ts")).from("meters").session("ts","10s").limit(1, 2).list(Map.class);
    System.out.println("查询结果：" + list);
}
```

**控制台输出**

```tex
方法：null
语句：SELECT voltage,FIRST(ts) FROM meters  SESSION(`ts`,10s) LIMIT ?,?
参数：[1, 2]
用时：40520ms
查询结果：[{voltage=219, ts=2023-08-21 22:48:41.204}, {voltage=219, ts=2023-08-21 22:53:57.372}]
```

## 举例五：数据插入

**测试**

```java
    /**
     * 测试插入
     */
    @Test
    public void testInsert(){
        flexTdMapper.insertInto("d1001").values(new Date(),10.2,219,0.32).execute();
    }
```

**控制台输出**

```
方法：null
语句：INSERT INTO `d1001`  VALUES(?,?,?,?)
参数：[Tue Aug 22 20:23:05 CST 2023, 10.2, 219, 0.32]
用时：473ms
```

## 举例六：批量插入

**测试**

```java
    /**
     * 测试插入多条
     */
    @Test
    public void testInsertMany(){
		List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{new Date(), 10.2, 219, 0.32});
        list.add(new Object[]{new Date(), 11.2, 219, 0.32});
        flexTdMapper.insertInto("d1001").valuesList(list, o -> o).execute();
    }
```

**控制台输出**

```tex
方法：null
语句：INSERT INTO `d1001`  VALUES(?,?,?,?),(?,?,?,?)
参数：[Tue Aug 22 20:25:01 CST 2023, 10.2, 219, 0.32, Tue Aug 22 20:25:01 CST 2023, 11.2, 219, 0.32]
用时：427ms
```

## 举例七：插入并自动创建表

**测试**

```java
    /**
     * 测试插入并自动建表
     */
    @Test
    public void testInsertAndCreate(){
        flexTdMapper.insertInto("d2001").using("meters").tags(2,"abc").values(new Date(),10.2,219,0.32).execute();
    }
```

**控制台输出**

```
方法：null
语句：INSERT INTO `d2001`  USING `meters`  TAGS(?,?) VALUES(?,?,?,?)
参数：[2, abc, Tue Aug 22 20:25:50 CST 2023, 10.2, 219, 0.32]
用时：411ms
```

## 举例八：文件插入

**测试**

```java
    /**
     * 测试插入来自文件
     */
    @Test
    public void testInsertFile(){
        flexTdMapper.insertInto("d2001").using("meters").tags(2, "abc").file("fff.txt").execute();
    }
```

**控制台输出**

```
方法：null
语句：INSERT INTO `d2001`  USING `meters`  TAGS(?,?)  FILE 'fff.txt'
参数：[2, abc]
用时：317ms
```

## 举例九：分页查询

```java
/**
 * 测试分页查询
 */
@Test
public void testPageQuery() {
    Page<Map> page = flexTdMapper.select(col("*")).from("meters").page(Map.class,new Page(1,2));
    System.out.println("总数：" + page.getTotal() + "\n查询结果：" + page.getRows());
}
```

**控制台输出**

```tex
方法：null
语句：SELECT COUNT(1) FROM meters 
参数：[]
用时：1254ms
方法：null
语句：SELECT * FROM meters  LIMIT ?,?
参数：[0, 2]
用时：70ms
总数：100000008
查询结果：[{phase=0.308333, current=10.0, location=California.LosAngles, ts=2017-07-14 10:40:00.0, voltage=110, groupid=9}, {phase=0.291667, current=10.12, location=California.LosAngles, ts=2017-07-14 10:40:00.001, voltage=109, groupid=9}]
```

## **举例十：插入实体**

**创建实体**

```java
@Table
public class Meters {
    @Column
    private Date ts;
    @Column
    private double current;
    @Column
    private int voltage;
    @Column
    private double phase;
    @Tag
    private int groupid;
    @Tag
    private String location;
    }
```

**测试**

```java
/**
 * 测试插入实体
 */
@Test
public void insertEntity() {
    Meters meters=new Meters();
    meters.setTs(new Date());
    meters.setCurrent(1.23);
    meters.setPhase(3.45);
    meters.setVoltage(4);
    meters.setGroupid(1);
    meters.setLocation("a");
    flexTdMapper.insert("d1001", meters);
}
```

**控制台输出**

```tex
方法：null
语句：INSERT INTO `d1001`  USING `meters`  TAGS(?,?) VALUES(?,?,?,?)
参数：[1, a, Tue Aug 29 23:09:40 CST 2023, 1.23, 4, 3.45]
用时：592ms
```

## **举例十一：插入部分字段（1）**

```java
@Test
public void testStrInsert() {
    flexTdMapper.insertInto("d1001").columns("name", "age").values("accountName", 100).execute();
}
```

## **举例十二：插入部分字段（2）**

```java
@Test
public void testInsertMap() {
    Map<String, Object> strMap = new HashMap();
    strMap.put("name", "aaa");
    strMap.put("age", 22);
    flexTdMapper.insertInto("d1001").using("aa").tags("a").valuesStrMap(strMap).execute();
}
```
