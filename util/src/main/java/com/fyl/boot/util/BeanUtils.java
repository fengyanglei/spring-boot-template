package com.fyl.boot.util;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 拷贝bean
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 单个bean拷贝
     * @param obj 源bean
     * @param cls 目标bean class
     * @param <T>
     * @return
     */
    public static <T> T copy(Object obj, Class<T> cls) {
        if (obj == null) {
            return null;
        }
        Object target = null;
        try {
            target = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        copyProperties(obj, target);

        return (T) target;

    }

    /**
     * 单个bean拷贝
     * @param obj 源bean
     * @param cls 目标bean class
     * @param ignoreProperties 忽略属性
     * @param <T>
     * @return
     */
    public static <T> T copy(Object obj, Class<T> cls, String... ignoreProperties) {
        Object target = null;
        try {
            target = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyProperties(obj, target, ignoreProperties);
        return (T) target;

    }

    /**
     * 集合bean拷贝
     * @param objects 源bean
     * @param cls 目标bean class
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List objects, Class<T> cls) {
        List<T> results = new ArrayList<T>();
        if (CollectionUtils.isEmpty(objects)) {
            return results;
        }

        for (Object obj : objects) {
            T t = copy(obj, cls);
            results.add(t);
        }
        return results;
    }

    /**
     * 集合bean拷贝
     * @param objects 源bean
     * @param cls 目标bean class
     * @param ignoreProperties 忽略属性
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List objects, Class<T> cls, String... ignoreProperties) {
        List<T> results = new ArrayList<T>();
        if (CollectionUtils.isEmpty(objects)) {
            return results;
        }

        for (Object obj : objects) {
            T t = copy(obj, cls, ignoreProperties);
            results.add(t);
        }
        return results;
    }

}
