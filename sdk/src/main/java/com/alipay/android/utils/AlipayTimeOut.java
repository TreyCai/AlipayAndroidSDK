package com.alipay.android.utils;

/**
 * <pre>
 * 订单支付过期时间生成类。
 * 有效范围：1m~15d
 * 对应该参数：{@link AlipayParam#IT_B_PAY}
 * </pre>
 */
public final class AlipayTimeOut {

    private static final String UNIT_MINUTE = "m";
    private static final String UNIT_HOUR = "h";
    private static final String UNIT_DAY = "d";

    /**
     * 过期时间为：当天(无论交易何时创建,都在 0 点关闭)
     */
    public static final String CURRENT_DAY = "1c";

    private final int mTimeValue;

    public AlipayTimeOut(int timeValue) {
        mTimeValue = timeValue;
    }

    /**
     * @return 分钟的字符串。比如 "15m"
     */
    public String minute() {
        if (!isTimeAvailable(UNIT_MINUTE)) {
            throw new IllegalArgumentException(
                    "Invalid time value: " + mTimeValue + ". Value should be in the [1m, 21600m].");
        }
        return mTimeValue + UNIT_MINUTE;
    }

    /**
     * @return 小时的字符串。比如 "15h"
     */
    public String hour() {
        if (!isTimeAvailable(UNIT_HOUR)) {
            throw new IllegalArgumentException(
                    "Invalid time value: " + mTimeValue + ". Value should be in the [1h, 360h].");
        }
        return mTimeValue + UNIT_HOUR;
    }

    /**
     * @return 天数的字符串。比如 "15d"
     */
    public String day() {
        if (!isTimeAvailable(UNIT_DAY)) {
            throw new IllegalArgumentException(
                    "Invalid time value: " + mTimeValue + ". Value should be in the [1d, 15d].");
        }
        return mTimeValue + UNIT_DAY;
    }

    private boolean isTimeAvailable(String unit) {
        if (unit.equals(UNIT_MINUTE)) {
            return mTimeValue >= 1 && mTimeValue <= 21600; // 21600m == 15d
        } else if (unit.equals(UNIT_HOUR)) {
            return mTimeValue >= 1 && mTimeValue <= 360; // 360h == 15d
        } else if (unit.equals(UNIT_DAY)) {
            return mTimeValue >= 1 && mTimeValue <= 15;
        }
        return false;
    }
}
