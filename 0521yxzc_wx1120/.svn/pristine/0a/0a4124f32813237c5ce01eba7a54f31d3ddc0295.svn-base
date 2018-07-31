package com.plugs.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * NumberFormatUtil
 *
 * @author Zoro
 * @date 2016/11/3
 */
public class NumberFormatUtil {

    private static final String DEFAULT_FORMAT_2 = "#0.00";
    private static final String DEFAULT_FORMAT_1 = "#0.0";
    private static final String DEFAULT_FORMAT_INT = "#";

    /**
     * 格式化金额
     *
     * @param num
     * @param format
     * @return
     */
    public static String format(double num, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        if (num == 0d) {
            decimalFormat = new DecimalFormat(DEFAULT_FORMAT_INT);
            decimalFormat.format(num);
        }
        return decimalFormat.format(num);
    }

    /**
     * 格式化成整数，去除小数部分
     *
     * @param num
     * @return
     */
    public static String formatToInt(double num) {

        return format(num, DEFAULT_FORMAT_INT);
    }

    /**
     * 保留两位小数，不四舍五入
     *
     * @param num
     * @return
     */
    public static String format2TwoDecimal(double num) {

        return format(num, DEFAULT_FORMAT_2);
    }

    /**
     * 保留一位小数，不四舍五入
     *
     * @param num
     * @return
     */
    public static String format2OneDecimal(double num) {

        return format(num, DEFAULT_FORMAT_1);
    }

    /**
     * 格式化成整数，去除小数部分
     *
     * @param num
     * @return
     */
    public static String roundAndFormatToInt(double num) {

        return format(MathUtils.round(num, 0), DEFAULT_FORMAT_INT);
    }

    /**
     * 保留两位小数，并四舍五入
     *
     * @param num
     * @return
     */
    public static String roundAndFormat2TwoDecimal(double num) {
        return format2TwoDecimal(MathUtils.round(num, 2));
    }

    /**
     * 保留1位小数，并四舍五入
     *
     * @param num
     * @return
     */
    public static String roundAndFormat2OneDecimal(double num) {
        return format2OneDecimal(MathUtils.round(num, 2));
    }
}
