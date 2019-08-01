package com.fyl.boot.util.chars;

import com.fyl.boot.util.ArrayUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

    static String[] videoPrex = {"wmv", "asf", "asx", "rm", "rmvb", "mpg", "mpeg", "mpe", "dat", "vob", "dv", "3gp", "3g2", "mov", "avi", "mkv", "mp4", "m4v", "flv"};
    static String flvVideo = "flv";
    static String[] spePicSuff = {"gif", "png", "jpg"};
    static final Pattern phonePattern = Pattern.compile("^1[3-9][0-9]{9}$");
    /**
     * 订单生成格式
     */
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    //判断是否是视频格式
    public static Boolean isVideo(String prefix) {
        for (String str : videoPrex) {
            if (str.equals(prefix.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证文件后缀名是否是flv
     * <br>如果是flv格式，返回true；否则false
     *
     * @param suffix
     * @return
     * @author wk.s
     */
    public static Boolean isFlvVideo(String suffix) {

        Boolean flag = true;
        if (!suffix.equals(flvVideo)) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证后缀名是否是指定的图片后缀名
     * <br>如果是指定格式的图片后缀，返回true；否则返回false
     *
     * @param suffix
     * @return
     * @author wk.s
     */
    public static Boolean isSpecPic(String suffix) {

        Boolean flag = false;
        for (String str : spePicSuff) {
            if (str.equals(suffix)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String buildOrderNo() {
        return sdf.format(new Date());
    }

    public static List<byte[]> splitString(String data, int len) {
        byte[] all = data.getBytes();
        List<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < all.length; i = i + len) {
            int end = i + len;
            if (end >= all.length) {
                end = all.length;
            }
            byte[] temp = ArrayUtils.subarray(all, i, end);
            list.add(temp);

        }
        return list;
    }

    public static boolean startsWith(String methodName, String[] prefixs) {

        if (StringUtils.isEmpty(methodName) || ArrayUtils.isEmpty(prefixs)) {
            return false;
        }

        for (String prefix : prefixs) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCorrectPhoneNumber(String phone) {
        return isNotEmpty(phone) && phonePattern.matcher(phone).find();
    }

    /**
     * 隐藏手机号中间4位
     *
     * @param tel 手机号
     * @return
     */
    public static String hideTel(String tel) {
        if (tel == null) {
            return null;
        }
        return tel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 数组转化为字符串
     *
     * @param arr [a,b,c]
     * @return
     */
    public static String arrayToString(Object[] arr) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        return "[" + join(arr, ",") + "]";
    }

    /**
     * 数组字符串转化为数组
     *
     * @param arrStr [a,b,c]
     * @return
     */
    public static String[] stringToArray(String arrStr) {
        if (isBlank(arrStr)) {
            return null;
        }
        arrStr = arrStr.substring(1, arrStr.length() - 1);
        return arrStr.split(",");
    }

    /***
     * 字符串是否包含中文
     * @param name
     * @return
     */
    public static boolean isContainChinese(String name) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(name);
        if(matcher.find()){
            return true;
        }
        return false;
    }

}
