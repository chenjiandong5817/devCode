package com.plugs.utils;

import com.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * <p>
 * Created by Zhouhy on 2016/9/27.
 * update by Zoro on 2016/10/6
 */
public class DateUtils extends DateUtil {
    static SimpleDateFormat dtf = new SimpleDateFormat(DEFAULT_FORMAT);
    static SimpleDateFormat dtf2 = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 根据字符串解析时间yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String date) throws ParseException {
        return dtf.parse(date);
    }

    /**
     * 根据字符串解析时间yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        return dtf2.parse(date);
    }

    /**
     * 根据字符串和格式解析时间
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, String patten) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        return sf.parse(date);
    }

    /**
     * 解析当前时间
     *
     * @return
     * @throws ParseException
     */
    public static Date CurrentDateTime() throws ParseException {
        String now = getCurrentDate();
        return dtf.parse(now);

    }


    /**
     * 获得当天0点时间
     *
     * @return
     */
    public static Date getZeroTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获得当天23:59:59点时间
     *
     * @return
     */
    public static Date getNighTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得昨天0点时间
     *
     * @return
     */
    public static Date getYesterdayZeroTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getZeroTime().getTime() - 24 * 3600 * 1000);//减去一天毫秒
        return cal.getTime();
    }


    /**
     * 获得本周一0点时间
     *
     * @return
     */
    public static Date getZeroTimeOnWeekBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获得本周日23:59:59点时间
     *
     * @return
     */
    public static Date getNightTimeOnWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getZeroTimeOnWeekBegin());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    /**
     * 获得本月第一天0点时间
     *
     * @return
     */
    public static Date getZeroTimeOnMonthBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得本月最后一天23:59:59点时间
     *
     * @return
     */
    public static Date getNightTimeOnMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }


    /**
     * 获取本季度开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }


    /**
     * 获取本年开始时间
     *
     * @return
     */
    public static Date getCurrentYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
        return cal.getTime();
    }

    /**
     * 获取本年份结束时间
     *
     * @return
     */
    public static Date getCurrentYearEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime());
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    public static Date getTimeByHourAndMinute(int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH) + day);//如果-1,就是前一天
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 根据字符串获取夜间时段起始时间
     *
     * @param nightTimeStr
     * @return
     */
    public static Date getNightTimeBegin(String nightTimeStr) {
        String nightTimeStrBegin = nightTimeStr.split("-")[0];
        int nightTimeBeginHour = Integer.parseInt(nightTimeStrBegin.split(":")[0]);
        int nightTimeBeginMinute = Integer.parseInt(nightTimeStrBegin.split(":")[1]);

        Calendar cal = Calendar.getInstance();
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        int day = 0;
        if (0 <= hourOfDay && hourOfDay < 18) {
            day = -1;
        }
        return DateUtils.getTimeByHourAndMinute(day, nightTimeBeginHour, nightTimeBeginMinute);
    }

    /**
     * 根据字符串获取夜间时段结束时间
     *
     * @param nightTimeStr
     * @return
     */
    public static Date getNightTimeEnd(String nightTimeStr) {
        String nightTimeStrEnd = nightTimeStr.split("-")[1];
        int nightTimeEndHour = Integer.parseInt(nightTimeStrEnd.split(":")[0]);
        int nightTimeEndMinute = Integer.parseInt(nightTimeStrEnd.split(":")[1]);

        int day = 0;
        if (18 <= nightTimeEndHour && nightTimeEndHour < 24) {
            Calendar cal = Calendar.getInstance();
            int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
            if (0 <= hourOfDay && hourOfDay < 18) {
                day = -1;
            }
        }

        return DateUtils.getTimeByHourAndMinute(day, nightTimeEndHour, nightTimeEndMinute);
    }


    /**
     * 是否在夜间时段
     *
     * @param nightTimeStr
     * @return
     */
    public static boolean isInNightTime(String nightTimeStr, Date date) {
        String[] args = nightTimeStr.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(sdf.format(date)).getTime();// sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) { //结束时间为0点 转为24点
                args[1] = "24:00";
            }
            if (end < start) { //类似跨天 22:00-06:00   09:00
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } else { //04:00-06:00  05:00
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            return false; //发生异常 返回false
        }

        //TODO:20180130 应福州夜间时段非跨天 作废；
       /* Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);

        int dateHour = calDate.get(Calendar.HOUR_OF_DAY);


        String nightTimeStrBegin = nightTimeStr.split("-")[0];
        int nightTimeBeginHour = Integer.parseInt(nightTimeStrBegin.split(":")[0]);
        int nightTimeBeginMinute = Integer.parseInt(nightTimeStrBegin.split(":")[1]);

        String nightTimeStrEnd = nightTimeStr.split("-")[1];
        int nightTimeEndHour = Integer.parseInt(nightTimeStrEnd.split(":")[0]);
        int nightTimeEndMinute = Integer.parseInt(nightTimeStrEnd.split(":")[1]);

        //设置夜间起始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.set(Calendar.YEAR, calDate.get(Calendar.YEAR));
        calBegin.set(Calendar.MONTH, calDate.get(Calendar.MONTH));

        if (dateHour >= 0 && dateHour <= nightTimeEndHour) {
            calBegin.set(Calendar.DAY_OF_MONTH, calDate.get(Calendar.DAY_OF_MONTH) - 1);
        } else {
            calBegin.set(Calendar.DAY_OF_MONTH, calDate.get(Calendar.DAY_OF_MONTH));
        }
        calBegin.set(Calendar.HOUR_OF_DAY, nightTimeBeginHour);
        calBegin.set(Calendar.MINUTE, nightTimeBeginMinute);
        calBegin.set(Calendar.SECOND, 0);


        //设置夜间结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.YEAR, calDate.get(Calendar.YEAR));
        calEnd.set(Calendar.MONTH, calDate.get(Calendar.MONTH));

        if (dateHour >= 0 && dateHour <= nightTimeEndHour) {
            calEnd.set(Calendar.DAY_OF_MONTH, calDate.get(Calendar.DAY_OF_MONTH));
        } else {
            calEnd.set(Calendar.DAY_OF_MONTH, calDate.get(Calendar.DAY_OF_MONTH) + 1);
        }
        calEnd.set(Calendar.HOUR_OF_DAY, nightTimeEndHour);
        calEnd.set(Calendar.MINUTE, nightTimeEndMinute);
        calEnd.set(Calendar.SECOND, 0);

        if ((calBegin.getTime().getTime() <= date.getTime()) && (date.getTime() <= calEnd.getTime().getTime())) {
            return true;
        }

        return false;*/
    }


    /***
     * 两个日期相差天数
     *
     * @param fDate
     * @param oDate
     * @return
     */
    public static int daysBetween(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year1 = aCalendar.get(Calendar.YEAR);

        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year2 = aCalendar.get(Calendar.YEAR);

        return year2 > year1 ? 1 : (year2 < year1 ? -1 : day2 - day1);

    }

    public static Date addDay(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }


    /**
     * 获取指定多少天前的时间
     *
     * @param indexDay 传入天数
     * @return
     */
    public static Date getSpecifiedDate(int indexDay) {
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(new Date());
        theCa.add(theCa.DATE, -indexDay);
        return theCa.getTime();
    }

    public static boolean isInBusyTime(Date date) {

        String[] busyTime1 = new String[]{"06:50:00", "10:10:00"};
        String[] busyTime2 = new String[]{"15:50:00", "21:10:00"};


        Date busyDateBegin1 = DateUtil.getDateFromStr(getCurrentDate() + " " + busyTime1[0], DateUtil.DEFAULT_FORMAT);
        Date busyDateEnd1 = DateUtil.getDateFromStr(getCurrentDate() + " " + busyTime1[1], DateUtil.DEFAULT_FORMAT);

        Date busyDateBegin2 = DateUtil.getDateFromStr(getCurrentDate() + " " + busyTime2[0], DateUtil.DEFAULT_FORMAT);
        Date busyDateEnd2 = DateUtil.getDateFromStr(getCurrentDate() + " " + busyTime2[1], DateUtil.DEFAULT_FORMAT);

        if ((busyDateBegin1.getTime() <= date.getTime() && date.getTime() <= busyDateEnd1.getTime())
                || (busyDateBegin2.getTime() <= date.getTime() && date.getTime() <= busyDateEnd2.getTime())) {
            return true;

        }
        return false;
    }


    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 判断日期是否为今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//转换为Calendar类型比较
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            //同年
            if (calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
                //同月
                if (calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                    //同日
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 转换成毫秒
     *
     * @param time
     * @return
     */
    public static long transferToMillisecond(long time) {
        return time * 1000;
    }

}
