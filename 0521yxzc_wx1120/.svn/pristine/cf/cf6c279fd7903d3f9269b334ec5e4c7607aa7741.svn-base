package com.plugs.utils;

import com.util.MapUtil;
import com.util.StringUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhouhy on 2016/9/26.
 */
public class StringUtils extends StringUtil {
    //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
    Pattern idCardPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

    /***
     * 验证身份证号
     *
     * @param idCard 身份证号
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        //身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(idCard);
        //判断用户输入是否为身份证号
        if (idNumMatcher.matches())
            return true;
        else
            return false;
    }

    /***
     * 提取身份证出生日期
     *
     * @param idCard 身份证号
     * @return
     */
    public static Map<String, Object> getIdCardBirthDay(String idCard) {
        if (checkIdCard(idCard)) {
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");//身份证上的前6位以及出生年月日
            Matcher birthDateMather = birthDatePattern.matcher(idCard);
            //通过Matcher获得用户的出生年月日
            if (birthDateMather.find()) {
                String year = birthDateMather.group(1);
                String month = birthDateMather.group(2);
                String date = birthDateMather.group(3);
                //输出用户的出生年月日
                System.out.println(year + "年" + month + "月" + date + "日");
                Map<String, Object> map = MapUtil.buildMap(3);
                map.put("year", year);
                map.put("month", month);
                map.put("date", date);
                return map;
            }
        }
        return null;
    }

    /**
     * 数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean ArrayIsEmpty(String[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 验证图片url是否有效
     * @param imgUrl
     * @return
     */
    public static boolean ImageTypeCheck(String imgUrl) {
        try {
            if (imgUrl == null || imgUrl.indexOf(".") == -1){
                return false;
            }
            String imgSuffix = imgUrl.substring(imgUrl.lastIndexOf(".")+1).trim().toLowerCase();
            if (!("jpg".equals(imgSuffix) || "gif".equals(imgSuffix)
                    || "bmp".equals(imgSuffix) || "png".equals(imgSuffix))) {
                return false;
            }

//            URL urlStr = new URL(imgUrl);
//            HttpURLConnection connection = (HttpURLConnection) urlStr.openConnection();
//            int state = connection.getResponseCode();
//            if (state == 200) {
                return true;
//            } else {
//                return false;
//            }
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean checkMobile(String mobileNumber) {
        if(mobileNumber.length() != 11) {
            return Boolean.valueOf(false);
        } else {
            Pattern mobileExp = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");
            Pattern cmExp = Pattern.compile("(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)");
            Pattern cuExp = Pattern.compile("(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)");
            Pattern ctExp = Pattern.compile("(^1(33|53|71|72|73|74|75|76|77|78|79|8[019])\\d{8}$)|(^1700\\d{7}$)");
            Matcher matcher1 = mobileExp.matcher(mobileNumber);
            Matcher matcher2 = cmExp.matcher(mobileNumber);
            Matcher matcher3 = cuExp.matcher(mobileNumber);
            Matcher matcher4 = ctExp.matcher(mobileNumber);
            return !matcher1.matches() && !matcher2.matches() && !matcher3.matches() && !matcher4.matches()?Boolean.valueOf(false):Boolean.valueOf(true);
        }
    }


}
