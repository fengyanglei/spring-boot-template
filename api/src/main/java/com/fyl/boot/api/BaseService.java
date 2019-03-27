package com.fyl.boot.api;

import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Base Service
 */
public interface BaseService<T> {


    /**
     * 查找对象并排序
     * @param entity 查询参数
     * @param parameter 排序参数
     * @param isDesc 是否倒序
     * @return
     */
    List<T> findAllOrderBy(T entity, String parameter, Boolean isDesc);

    /**
     * 新增
     * @param entity 实体
     * @return
     */
    int insert(T entity);

    /**
     * 删除数据
     * @param entity 表内符合实体属性值的都会被删除
     * @return
     */
    int delete(T entity);

    /**
     * 通过主键ID删除数据
     * @param id 主键ID
     * @return
     */
    int deleteById(Object id);


    /**
     * 通过Example条件删除数据
     * @param example 条件封装
     * @return
     */
    int deleteByExample(Example example);

    /**
     * 通过主键ID,逻辑删除数据
     * @param id 主键ID
     * @return
     */
    int updateDeletedById(Object id);

    /**
     * 更新数据
     * @param entity 需要被更新的数据,实体内一定要有主键ID
     * @return
     */
    int updata(T entity);

    /**
     * 根据条件更新数据
     * @param entity 需要更新的数据
     * @param example 查询条件
     * @return
     */
    int updataByExample(T entity, Example example);

    /**
     * 更新符合要求的数据,实体内空值不会被更新
     * @param entity 数据
     * @param example 条件
     * @return
     */
    int updateByExampleSelective(T entity, Example example);

    /**
     * 根据主键更新属性不为null的值
     * @param entity
     * @return
     */
    int updateByPrimaryKeySelective(T entity);

    /**
     * 根据主键更新
     * @param entity
     * @return
     */
    int updateByPrimaryKey(T entity);

    /**
     * 根据主键ID获取数据
     * @param id 主键ID
     * @return
     */
    T findById(Object id);

    /**
     * 根据实体条件获取一条数据
     * @param entity 实体内封装需要查询的条件
     * @return
     */
    T findOne(T entity);

    /**
     * 获取所有符合条件的数据
     * @param entity 实体内封装需要查询的条件
     * @return
     */
    List<T> findAll(T entity);

    /**
     * 获取所有数据
     * @return
     */
    List<T> findAll();

    /**
     * 分页获取所有数据
     * @param index 页数
     * @param count 每页条数
     * @return
     */
    PageInfo findAll(int index, int count);


    /**
     * 根据条件分页获取数据
     * @param entity 实体内封装需要查询的条件
     * @param index 页数
     * @param count 每页多少条数据
     * @return
     */
    PageInfo<T> findAllPage(T entity, int index, int count);

    /**
     * 根据Example查询数据
     * @param example 封装查询条件
     * @return
     */
    List<T> selectByExample(Example example);

    /**
     * 根据Example分页查询数据
     * @param example 封装查询条件
     * @param index 页数
     * @param count 每页多少条数据
     * @return
     */
    PageInfo<T> selectByExamplePage(Example example, int index, int count);

    int count(T entity);

    int countByExample(Example example);


}
