# IService

**`dream-orm`提供了一个名为 `IService` 的接口，及其默认实现类 `ServiceImpl` ，用于简化在 `Service` 层重复定义 `TemplateMapper` 。**

```java
/**
 * 顶级Service接口
 *
 * @param <ListView> 列表页返回数据
 * @param <EditView> 编辑页返回数据
 */
public interface IService<ListView, EditView> {
    /**
     * 根据主键查询
     *
     * @param id 主键值
     * @return
     */
    EditView selectById(Object id);

    /**
     * 根据主键集合查询
     *
     * @param idList 主键值集合
     * @return
     */
    List<EditView> selectByIds(Collection<?> idList);

    /**
     * 根据对象生成注解条件，并查询一条
     *
     * @param conditionObject 对象注解条件
     * @return
     */
    EditView selectOne(Object conditionObject);

    /**
     * 根据对象生成注解条件，并查询集合
     *
     * @param conditionObject 对象注解条件
     * @return
     */
    List<ListView> selectList(Object conditionObject);

    /**
     * 根据对象生成注解条件，并分页查询
     *
     * @param conditionObject 对象注解条件
     * @param page            分页
     * @return
     */
    Page<ListView> selectPage(Object conditionObject, Page page);

    /**
     * 根据主键更新
     *
     * @param view 更新对象
     * @return
     */
    int updateById(EditView view);

    /**
     * 根据主键非空更新
     *
     * @param view 更新对象
     * @return
     */
    int updateNonById(EditView view);

    /**
     * 插入数据
     *
     * @param view 插入对象
     * @return
     */
    int insertDef(EditView view);

    /**
     * 插入数据并返回主键值
     *
     * @param view 插入对象
     * @return
     */
    Object insertFetchKey(EditView view);

    /**
     * 根据主键删除
     *
     * @param id 主键值
     * @return
     */
    int deleteById(Object id);

    /**
     * 根据主键集合删除
     *
     * @param idList 主键值集合
     * @return
     */
    int deleteByIds(Collection<?> idList);

    /**
     * 判断主键是否存在
     *
     * @param id 主键值
     * @return
     */
    boolean existById(Object id);

    /**
     * 根据注解对象生成的条件判断是否存在
     *
     * @param conditionObject 注解对象条件
     * @return
     */
    boolean exist(Object conditionObject);

    /**
     * 批量插入
     *
     * @param viewList 数据集合
     * @return
     */
    List<Object> batchInsert(Collection<EditView> viewList);

    /**
     * 批量更新
     *
     * @param viewList 数据集合
     * @return
     */
    List<Object> batchUpdateById(Collection<EditView> viewList);
}
```
