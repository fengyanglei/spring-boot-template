package com.fyl.boot.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 对象工具
 */
public class ObjectUtils {
    /**
     * 空值判断
     *
     * @param obj
     * @return
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().trim().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 非空判断
     *
     * @param obj
     * @return
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }


    /**
     * 参数处理
     * 空字符串、空集合 赋值null,字符串trim
     *
     * @param obj 参数实体
     * @throws Exception
     */
    public static void trim(Object obj) throws IllegalAccessException {
        //entity所有字段
        Field[] fields = obj.getClass().getDeclaredFields();
        //字段
        Field field = null;
        //字段值
        Object value = null;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            field.setAccessible(true);
            value = field.get(obj);
            if (value == null) {
                continue;
            }
            //重新赋值
            if (ObjectUtils.isBlank(value)) {
                JavaBeansUtils.dynamicSet(obj, field.getName(), null);
            } else if (value instanceof String) {
                JavaBeansUtils.dynamicSet(obj, field.getName(), value.toString().trim());
            }

        }
    }
}
