package com.fyl.boot.service;

import com.fyl.boot.api.BaseService;
import com.fyl.boot.util.DynamicMethod;
import com.fyl.boot.util.JavaBeansUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Base ServiceImpl
 */
public class BaseServiceImpl<T> implements BaseService<T> {



    @Autowired
    protected Mapper<T> mapper;

    @Override
    public List<T> findAllOrderBy(T entity, String parameter, Boolean isDesc) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class param = (Class) params[0];
        Example example = new Example(param);
        example.createCriteria().andEqualTo(entity);
        if (isDesc) {
            example.setOrderByClause(parameter + " desc");
        }else {
            example.setOrderByClause(parameter + " asc");
        }
        return mapper.selectByExample(example);
    }

    @Override
    public int insert(T entity) {
        JavaBeansUtils.doInitEntity(entity,"setId",null);
        DynamicMethod.invokeMethod(entity, "setCreateTime", new Object[]{new Date()});
        DynamicMethod.invokeMethod(entity, "setDeleted", new Object[]{Boolean.FALSE});
        return mapper.insert(entity);
    }

    @Override
    public int delete(T entity) {
        return mapper.delete(entity);
    }

    @Override
    public int deleteById(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByExample(Example example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int updateDeletedById(Object id) {
        T t = mapper.selectByPrimaryKey(id);
        DynamicMethod.invokeMethod(t, "setDeleted", new Object[]{Boolean.TRUE});
        return this.updateByPrimaryKeySelective(t);
    }

    @Override
    public int updata(T entity) {
        DynamicMethod.invokeMethod(entity, "setUpdateTime", new Object[]{new Date()});
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updataByExample(T entity, Example example) {
        DynamicMethod.invokeMethod(entity, "setUpdateTime", new Object[]{new Date()});
        return mapper.updateByExample(entity, example);
    }

    @Override
    public int updateByExampleSelective(T entity, Example example) {
        DynamicMethod.invokeMethod(entity, "setUpdateTime", new Object[]{new Date()});
        return mapper.updateByExampleSelective(entity, example);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        DynamicMethod.invokeMethod(entity, "setUpdateTime", new Object[]{new Date()});
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        DynamicMethod.invokeMethod(entity, "setUpdateTime", new Object[]{new Date()});
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public T findById(Object id) {
        T t = mapper.selectByPrimaryKey(id);
        return t;
    }

    @Override
    public T findOne(T entity) {
        DynamicMethod.invokeMethod(entity, "setDeleted", new Object[]{Boolean.FALSE});
        return mapper.selectOne(entity);
    }

    @Override
    public List<T> findAll(T entity) {
        DynamicMethod.invokeMethod(entity, "setDeleted", new Object[]{Boolean.FALSE});
        return mapper.select(entity);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public PageInfo findAll(int index, int count) {
        PageHelper.startPage(index, count);
        List<T> list = mapper.selectAll();
        PageInfo page = new PageInfo(list);
        return page;
    }

    @Override
    public PageInfo findAllPage(T entity, int index, int count) {
        PageHelper.startPage(index, count);
        List<T> list = mapper.select(entity);
        PageInfo page = new PageInfo(list);
        return page;
    }

    @Override
    public List<T> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo selectByExamplePage(Example example, int index, int count) {
        PageHelper.startPage(index, count);
        List<T> list = mapper.selectByExample(example);
        PageInfo page = new PageInfo(list);
        return page;
    }

    @Override
    public int count(T entity) {
        return mapper.selectCount(entity);
    }

    @Override
    public int countByExample(Example example) {
        return mapper.selectCountByExample(example);
    }


}
