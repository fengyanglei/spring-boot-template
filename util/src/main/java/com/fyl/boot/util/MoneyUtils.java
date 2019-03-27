package com.fyl.boot.util;

import java.math.BigDecimal;

public class MoneyUtils {
    public static BigDecimal getYuanFromPercent(Long percent) {
        if (percent == null || percent == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = new BigDecimal(percent).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;
    }

    /**
     * 分转元
     * @param fen
     * @return
     */
    public static Double getYuanFromFen(Long fen) {
        return getYuanFromPercent(fen).doubleValue();
    }

    /**
     * 元转分
     * @param yuan
     * @return
     */
    public static Long getFenFromYuan(Double yuan) {
        if (yuan == null || yuan == 0) {
            return 0L;
        }
        return new BigDecimal(Double.toString(yuan)).multiply(new BigDecimal(Double.toString(100))).longValue();
    }

}
