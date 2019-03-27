package com.fyl.boot.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Bean操作
 */
public class JavaBeansUtils {
    private JavaBeansUtils() {
    }

    /**
     * @param obj          对象
     * @param propertyName 属性名
     * @description:根据属性名调用Get方法
     */
    public static Object dynamicGet(Object obj, String propertyName) {
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param obj          对象
     * @param propertyName 属性名
     * @param value        要插入的值
     * @description:根据属性名调用set方法
     */
    public static void dynamicSet(Object obj, String propertyName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取get方法名-驼峰格式
     *
     * @param property 字段名
     * @return
     */
    public static String getGetterMethodNameCamelCase(String property) {
        return getGetterMethodName(getCamelCaseString(property, Boolean.TRUE));
    }

    /**
     * 获取set方法名-驼峰格式
     *
     * @param property 字段名
     * @return
     */
    public static String getSetterMethodNameCamelCase(String property) {
        return getSetterMethodName(getCamelCaseString(property, Boolean.TRUE));
    }

    /**
     * 获取get方法名
     *
     * @param property 字段名
     * @return
     */
    public static String getGetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();
        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0)) && (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1)))) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        sb.insert(0, "get");
        return sb.toString();
    }

    /**
     * 获取set方法名
     *
     * @param property 字段名
     * @return
     */
    public static String getSetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();
        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0)) && (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1)))) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        sb.insert(0, "set");
        return sb.toString();
    }

    /**
     * 驼峰命名
     *
     * @param inputString             字段名
     * @param firstCharacterUppercase 首字母是否大写
     * @return
     */
    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < inputString.length(); ++i) {
            char c = inputString.charAt(i);
            switch (c) {
                case ' ':
                case '#':
                case '$':
                case '&':
                case '-':
                case '/':
                case '@':
                case '_':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;
                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    public static String getValidPropertyName(String inputString) {
        String answer;
        if (inputString == null) {
            answer = null;
        } else if (inputString.length() < 2) {
            answer = inputString.toLowerCase(Locale.US);
        } else if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
            answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
        } else {
            answer = inputString;
        }

        return answer;
    }


    /**
     * 对象赋值- 类型自动转换
     *
     * @param entity     对象实体
     * @param methodName 方法名
     * @param value      属性值
     * @throws Exception
     */
    public static void doInitEntity(Object entity, String methodName, String value){
        //对象方法集
        Method[] methods = entity.getClass().getMethods();
        //匹配methodName
        Optional<Method> opt = Arrays.stream(methods).filter(m -> Objects.equals(methodName, m.getName())).findFirst();
        if (!opt.isPresent()) {
            return;
        }
        Method method = opt.get();
        try {
            if(value==null){
                method.invoke(entity, value);
                return;
            }
            //参数类型
            Class<?>[] clazz = method.getParameterTypes();
            String type = clazz[0].getName();
            //匹配赋值
            switch (type) {
                case "java.lang.String":
                    method.invoke(entity, value);
                    break;
                case "java.lang.Integer":
                    method.invoke(entity, new Integer(value));
                    break;
                case "java.lang.Long":
                    method.invoke(entity, new Long(value));
                    break;
                case "java.lang.Float":
                    method.invoke(entity, new Float(value));
                    break;
                case "java.lang.Double":
                    method.invoke(entity, new Double(value));
                    break;
                case "java.lang.Boolean":
                    method.invoke(entity, new Boolean(value));
                    break;
                case "java.math.BigDecimal":
                    method.invoke(entity, new BigDecimal(value));
                    break;
                case "java.math.BigInteger":
                    method.invoke(entity, new BigInteger(value));
                    break;
                case "java.util.Date":
                    method.invoke(entity, DateUtils.stringToDate(value));
                    break;
                default:
                    System.out.println("未知类型:" + type);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
