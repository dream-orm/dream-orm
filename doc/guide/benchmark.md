# 性能对比

当前性能比较代码来自`Mybatis-Flex`,原本是`Mybatis-Flex`和`Mybaits-Plus`的「性能」对比，在他们的基础上删除了json的typehandler,增加了自己的测试代码。

直接放结论:各项指标`dream-orm`直接超过`Mybatis-Flex`，在当前测试数据量的情况下，用时大约少一倍。

```tex

---------------

>>>>>>>testDreamSelectOne:301
>>>>>>>testFlexSelectOne:711
>>>>>>>testPlusSelectOneWithLambda:1370
>>>>>>>testPlusSelectOne:839

---------------

>>>>>>>testDreamSelectOne:129
>>>>>>>testFlexSelectOne:116
>>>>>>>testPlusSelectOneWithLambda:1165
>>>>>>>testPlusSelectOne:981

---------------

>>>>>>>testDreamSelectOne:69
>>>>>>>testFlexSelectOne:208
>>>>>>>testPlusSelectOneWithLambda:756
>>>>>>>testPlusSelectOne:819

---------------

>>>>>>>testDreamSelectOne:57
>>>>>>>testFlexSelectOne:148
>>>>>>>testPlusSelectOneWithLambda:984
>>>>>>>testPlusSelectOne:805

---------------

>>>>>>>testDreamSelectOne:35
>>>>>>>testFlexSelectOne:52
>>>>>>>testPlusSelectOneWithLambda:701
>>>>>>>testPlusSelectOne:568

---------------

>>>>>>>testDreamSelectOne:34
>>>>>>>testFlexSelectOne:51
>>>>>>>testPlusSelectOneWithLambda:595
>>>>>>>testPlusSelectOne:590

---------------

>>>>>>>testDreamSelectOne:29
>>>>>>>testFlexSelectOne:51
>>>>>>>testPlusSelectOneWithLambda:586
>>>>>>>testPlusSelectOne:563

---------------

>>>>>>>testDreamSelectOne:33
>>>>>>>testFlexSelectOne:55
>>>>>>>testPlusSelectOneWithLambda:727
>>>>>>>testPlusSelectOne:633

---------------

>>>>>>>testDreamSelectOne:30
>>>>>>>testFlexSelectOne:52
>>>>>>>testPlusSelectOneWithLambda:668
>>>>>>>testPlusSelectOne:589

---------------

>>>>>>>testDreamSelectOne:34
>>>>>>>testFlexSelectOne:50
>>>>>>>testPlusSelectOneWithLambda:580
>>>>>>>testPlusSelectOne:564


---------------------selectList:
---------------

>>>>>>>testDreamSelectTop10:59
>>>>>>>testFlexSelectTop10:86
>>>>>>>testPlusSelectTop10WithLambda:662
>>>>>>>testPlusSelectTop10:582

---------------

>>>>>>>testDreamSelectTop10:37
>>>>>>>testFlexSelectTop10:76
>>>>>>>testPlusSelectTop10WithLambda:603
>>>>>>>testPlusSelectTop10:583

---------------

>>>>>>>testDreamSelectTop10:37
>>>>>>>testFlexSelectTop10:73
>>>>>>>testPlusSelectTop10WithLambda:596
>>>>>>>testPlusSelectTop10:571

---------------

>>>>>>>testDreamSelectTop10:35
>>>>>>>testFlexSelectTop10:73
>>>>>>>testPlusSelectTop10WithLambda:582
>>>>>>>testPlusSelectTop10:627

---------------

>>>>>>>testDreamSelectTop10:35
>>>>>>>testFlexSelectTop10:73
>>>>>>>testPlusSelectTop10WithLambda:604
>>>>>>>testPlusSelectTop10:587

---------------

>>>>>>>testDreamSelectTop10:41
>>>>>>>testFlexSelectTop10:71
>>>>>>>testPlusSelectTop10WithLambda:670
>>>>>>>testPlusSelectTop10:569

---------------

>>>>>>>testDreamSelectTop10:35
>>>>>>>testFlexSelectTop10:79
>>>>>>>testPlusSelectTop10WithLambda:599
>>>>>>>testPlusSelectTop10:582

---------------

>>>>>>>testDreamSelectTop10:36
>>>>>>>testFlexSelectTop10:70
>>>>>>>testPlusSelectTop10WithLambda:636
>>>>>>>testPlusSelectTop10:571

---------------

>>>>>>>testDreamSelectTop10:35
>>>>>>>testFlexSelectTop10:74
>>>>>>>testPlusSelectTop10WithLambda:591
>>>>>>>testPlusSelectTop10:568

---------------

>>>>>>>testDreamSelectTop10:35
>>>>>>>testFlexSelectTop10:74
>>>>>>>testPlusSelectTop10WithLambda:589
>>>>>>>testPlusSelectTop10:579


---------------------paginate:
---------------

>>>>>>>testDreamPaginate:38
>>>>>>>testFlexPaginate:77
>>>>>>>testPlusPaginate:593

---------------

>>>>>>>testDreamPaginate:30
>>>>>>>testFlexPaginate:77
>>>>>>>testPlusPaginate:612

---------------

>>>>>>>testDreamPaginate:30
>>>>>>>testFlexPaginate:75
>>>>>>>testPlusPaginate:595

---------------

>>>>>>>testDreamPaginate:25
>>>>>>>testFlexPaginate:74
>>>>>>>testPlusPaginate:579

---------------

>>>>>>>testDreamPaginate:28
>>>>>>>testFlexPaginate:75
>>>>>>>testPlusPaginate:591

---------------

>>>>>>>testDreamPaginate:31
>>>>>>>testFlexPaginate:67
>>>>>>>testPlusPaginate:589

---------------

>>>>>>>testDreamPaginate:28
>>>>>>>testFlexPaginate:74
>>>>>>>testPlusPaginate:576

---------------

>>>>>>>testDreamPaginate:29
>>>>>>>testFlexPaginate:69
>>>>>>>testPlusPaginate:588

---------------

>>>>>>>testDreamPaginate:25
>>>>>>>testFlexPaginate:71
>>>>>>>testPlusPaginate:597

---------------

>>>>>>>testDreamPaginate:28
>>>>>>>testFlexPaginate:65
>>>>>>>testPlusPaginate:580


---------------------updateDef:
---------------

>>>>>>>testDreamUpdate:42
>>>>>>>testFlexUpdate:58
>>>>>>>testPlusUpdate:510

---------------

>>>>>>>testDreamUpdate:39
>>>>>>>testFlexUpdate:51
>>>>>>>testPlusUpdate:498

---------------

>>>>>>>testDreamUpdate:38
>>>>>>>testFlexUpdate:51
>>>>>>>testPlusUpdate:504

---------------

>>>>>>>testDreamUpdate:37
>>>>>>>testFlexUpdate:47
>>>>>>>testPlusUpdate:514

---------------

>>>>>>>testDreamUpdate:37
>>>>>>>testFlexUpdate:46
>>>>>>>testPlusUpdate:484

---------------

>>>>>>>testDreamUpdate:37
>>>>>>>testFlexUpdate:52
>>>>>>>testPlusUpdate:490

---------------

>>>>>>>testDreamUpdate:40
>>>>>>>testFlexUpdate:47
>>>>>>>testPlusUpdate:481

---------------

>>>>>>>testDreamUpdate:35
>>>>>>>testFlexUpdate:46
>>>>>>>testPlusUpdate:507

---------------

>>>>>>>testDreamUpdate:36
>>>>>>>testFlexUpdate:44
>>>>>>>testPlusUpdate:488

---------------

>>>>>>>testDreamUpdate:36
>>>>>>>testFlexUpdate:44
>>>>>>>testPlusUpdate:472
```

当前性能比较代码来自`beetlsql`,原本是`beetlsql`和其他国产数据库框架的「性能」对比，同样增加了自己的测试代码。

直接放结论:各项指标`dream-orm`基本登顶。

```tex
Benchmark                         Mode  Cnt    Score     Error   Units
JMHMain.beetlsqlComplexMapping   thrpt    3  126.916 ± 349.412  ops/ms
JMHMain.beetlsqlExecuteJdbc      thrpt    3   48.781 ± 156.107  ops/ms
JMHMain.beetlsqlExecuteTemplate  thrpt    3   36.777 ± 209.030  ops/ms
JMHMain.beetlsqlFile             thrpt    3   63.208 ±  64.264  ops/ms
JMHMain.beetlsqlGetAll           thrpt    3    4.708 ±   6.234  ops/ms
JMHMain.beetlsqlInsert           thrpt    3   55.011 ±  25.422  ops/ms
JMHMain.beetlsqlLambdaQuery      thrpt    3   29.645 ± 236.440  ops/ms
JMHMain.beetlsqlOne2Many         thrpt    3   56.392 ± 434.440  ops/ms
JMHMain.beetlsqlPageQuery        thrpt    3   43.986 ± 147.096  ops/ms
JMHMain.beetlsqlSelectById       thrpt    3   63.932 ±  17.653  ops/ms
JMHMain.dreamComplexMapping      thrpt    3  248.351 ± 209.782  ops/ms
JMHMain.dreamExecuteJdbc         thrpt    3  209.244 ±  33.469  ops/ms
JMHMain.dreamExecuteTemplate     thrpt    3  209.579 ±   5.884  ops/ms
JMHMain.dreamFile                thrpt    3  196.125 ±  88.131  ops/ms
JMHMain.dreamGetAll              thrpt    3   10.624 ±   1.849  ops/ms
JMHMain.dreamInsert              thrpt    3   55.326 ±  88.457  ops/ms
JMHMain.dreamLambdaQuery         thrpt    3  194.561 ±  23.030  ops/ms
JMHMain.dreamPageQuery           thrpt    3   97.606 ± 135.461  ops/ms
JMHMain.dreamSelectById          thrpt    3  210.446 ±  33.645  ops/ms
JMHMain.easyQueryComplexMapping  thrpt    3   33.183 ± 240.619  ops/ms
JMHMain.easyQueryExecuteJdbc     thrpt    3  168.401 ± 180.110  ops/ms
JMHMain.easyQueryGetAll          thrpt    3   12.783 ±   0.829  ops/ms
JMHMain.easyQueryInsert          thrpt    3   44.397 ±  43.888  ops/ms
JMHMain.easyQueryLambdaQuery     thrpt    3   77.022 ±   6.765  ops/ms
JMHMain.easyQueryOne2Many        thrpt    3   45.555 ± 183.953  ops/ms
JMHMain.easyQueryPageQuery       thrpt    3   45.917 ± 114.439  ops/ms
JMHMain.easyQuerySelectById      thrpt    3   67.607 ± 250.167  ops/ms
JMHMain.flexGetAll               thrpt    3    1.401 ±   0.457  ops/ms
JMHMain.flexInsert               thrpt    3    2.838 ±  39.958  ops/ms
JMHMain.flexPageQuery            thrpt    3   17.563 ± 157.941  ops/ms
JMHMain.flexSelectById           thrpt    3   38.475 ± 118.593  ops/ms
JMHMain.jdbcExecuteJdbc          thrpt    3  410.662 ± 117.016  ops/ms
JMHMain.jdbcGetAll               thrpt    3   21.951 ±   7.408  ops/ms
JMHMain.jdbcInsert               thrpt    3  121.117 ± 361.820  ops/ms
JMHMain.jdbcSelectById           thrpt    3  388.063 ± 770.608  ops/ms
JMHMain.jpaExecuteJdbc           thrpt    3   17.584 ± 116.416  ops/ms
JMHMain.jpaExecuteTemplate       thrpt    3   35.585 ± 247.749  ops/ms
JMHMain.jpaGetAll                thrpt    3    2.620 ±   1.909  ops/ms
JMHMain.jpaInsert                thrpt    3    8.810 ±  60.602  ops/ms
JMHMain.jpaOne2Many              thrpt    3   42.571 ± 432.509  ops/ms
JMHMain.jpaPageQuery             thrpt    3   24.476 ± 196.593  ops/ms
JMHMain.jpaSelectById            thrpt    3  176.292 ± 182.603  ops/ms
JMHMain.mybatisComplexMapping    thrpt    3   62.219 ± 125.885  ops/ms
JMHMain.mybatisExecuteTemplate   thrpt    3   24.156 ± 151.936  ops/ms
JMHMain.mybatisFile              thrpt    3   15.820 ± 162.548  ops/ms
JMHMain.mybatisGetAll            thrpt    3    2.733 ±   3.062  ops/ms
JMHMain.mybatisInsert            thrpt    3   19.955 ±  51.565  ops/ms
JMHMain.mybatisLambdaQuery       thrpt    3    1.887 ±  18.703  ops/ms
JMHMain.mybatisPageQuery         thrpt    3    8.535 ±  33.308  ops/ms
JMHMain.mybatisSelectById        thrpt    3   27.303 ±  95.440  ops/ms
JMHMain.woodExecuteJdbc          thrpt    3   83.237 ±  44.725  ops/ms
JMHMain.woodExecuteTemplate      thrpt    3   77.269 ±  19.345  ops/ms
JMHMain.woodFile                 thrpt    3   82.569 ±  41.053  ops/ms
JMHMain.woodGetAll               thrpt    3    1.235 ±   0.137  ops/ms
JMHMain.woodInsert               thrpt    3   48.096 ±  41.743  ops/ms
JMHMain.woodLambdaQuery          thrpt    3   82.854 ±  39.809  ops/ms
JMHMain.woodPageQuery            thrpt    3   88.134 ± 495.298  ops/ms
JMHMain.woodSelectById           thrpt    3   82.157 ±  14.562  ops/ms

```

