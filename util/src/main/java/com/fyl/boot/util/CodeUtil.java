package com.fyl.boot.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机码
 */
public class CodeUtil {

    /***
     * 获取商品编码
     * @return P + 编码
     */
    public static String getProductCode() {
        return getCode("P");
    }

    /***
     * 获取SKU编码
     * @return SKU + 编码
     */
    public static String getSkuCode() {
        return getCode("SKU");
    }

    /***
     * 获取编码
     * @param prefix 前缀
     * @return 前缀 + 时间 + 随机码
     */
    public static String getCode(String prefix) {
        return prefix + getCode(5);
    }

    /***
     * 获取编码
     * @param length 随机码长度
     * @return 时间 + 随机码
     */
    public static String getCode(int length) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        final String str = sdf.format(date);

        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        int a = (int) ((random * num));
        return str + a;
    }

    /**
     * 随机验证码
     *
     * @param length
     * @return
     */
    public static String getNumCode(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }

    public static String getRndSTR(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }

    public static String getFastCode(int length) {
        String base = "abcdefghjkmnpqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }



    public static String code36() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        String rndStr = String.valueOf(rnd.nextInt(10, 99));
        String timespan = String.valueOf(new Date().getTime());
        return Long.toString(Long.parseLong(timespan + rndStr), 36).toUpperCase();
    }


    public static void main(String[] args) {

        System.out.println(getSkuCode());

    }
}
