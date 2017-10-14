package cn.mobilizer.channel.comm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;

import cn.mobilizer.channel.comm.vo.ChannelEnum;

/**
 *
 * @author none
 *
 */
public class DateUtil {
	public static String[] MONTHS = new String[] { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

	private static int[] DOMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static int[] lDOMonth = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	public static final String dateFormat = "yyyy-MM-dd";
	public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String dateTimeHM = "yyyy-MM-dd HH:mm";
	public static final String dateTime = "HH:mm:ss";

	/**
	 * 取日期是一个月中的几号.
	 *
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Object day = cal.get(Calendar.DATE);
		if (day != null) {
			return Integer.valueOf(day.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 判断两个时间的大小.
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean isCompareTime(Date startTime, Date endTime) {
		if (endTime.getTime() > startTime.getTime()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 取日期的月份.
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		Object day = cal.get(Calendar.MONTH);
		if (day != null) {
			return Integer.valueOf(day.toString());
		} else {
			return 0;
		}
	}

	public static int getDaysOfmonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		if ((cal.get(Calendar.YEAR) % 4) == 0) {
			if (((cal.get(Calendar.YEAR) % 100) == 0) && ((cal.get(Calendar.YEAR) % 400) != 0)) {
				return DOMonth[cal.get(Calendar.MONTH)];
			}
			return lDOMonth[cal.get(Calendar.MONTH)];
		} else {
			return DOMonth[cal.get(Calendar.MONTH)];
		}
	}

	public static Calendar getClearCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date getDateAfterDays(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, duration);
		return cal.getTime();
	}

	public static Date getDateBeforeHours(Date date, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -duration);
		return cal.getTime();
	}

	public static Date getDateAfterMinutes(long duration) {
		long curr = System.currentTimeMillis();
		curr = curr + (duration * 60 * 1000);
		return new Date(curr);
	}

	public static String formatDate(Date date, String format) {
		return getFormatDate(date, format);
	}

	public static String formatSimpleDate(Date date){
		return getFormatDate(date, SIMPLE_DATE_FORMAT);
	}
	/**
	 * 格式时间
	 *
	 * @param date
	 * @param format	时间格式
	 * @return
	 */
	public static String getFormatDate(Date date, String format) {
		if (date != null) {
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(date);
		} else {
			return null;
		}
	}

	public static String getZHDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return "星期一";
		case Calendar.TUESDAY:
			return "星期二";
		case Calendar.WEDNESDAY:
			return "星期三";
		case Calendar.THURSDAY:
			return "星期四";
		case Calendar.FRIDAY:
			return "星期五";
		case Calendar.SATURDAY:
			return "星期六";
		case Calendar.SUNDAY:
			return "星期日";
		default:
			return "";
		}
	}

	/**
	 * 当前日期小时相加或相减所得日期（+,-）操作,输入一个日期得到天数加减后的日期。
	 *
	 * @param cal
	 * @return
	 */
	public static Date DsDay_Hour(Date date, Integer hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);

		return cal.getTime();
	}

	/**
	 * 当前日期小时相加或相减所得日期（+,-）操作,输入一个日期得到天数加减后的日期。
	 *
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date DsDay_HourOfDay(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);

		return cal.getTime();
	}

	/**
	 * 把参数时间精确到天
	 *
	 * @param cal
	 * @return
	 */

	public static Date accurateToDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return clearDateAfterDay(cal);
	}

	public static int getDayOfWeek(Calendar cal) {// 得到每月1号是星期几
		cal.set(Calendar.DATE, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static Date getTheMiddle(Date date, int plus) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, plus);
		return cal.getTime();
	}

	public static Map<String, Object> getBeginAndEndDateByDate(Date date) {

		Calendar calClearDate = Calendar.getInstance();
		calClearDate.setTime(date);
		calClearDate.set(Calendar.DATE, 1);
		date = calClearDate.getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int dayOfWeek = getDayOfWeek(cal);
		cal.set(Calendar.DATE, -(dayOfWeek - 2));
		map.put("beginDate", cal.getTime());
		cal.add(Calendar.DATE, 21);
		map.put("currPageDate", cal.getTime());
		cal.add(Calendar.DATE, 20);
		map.put("endDate", cal.getTime());
		return map;
	}

	/**
	 * 根据格式获取日期字符串.
	 *
	 * @param format
	 * @param aDate
	 * @return
	 */
	public static String getDateTime(String format, Date aDate) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String returnValue = df.format(aDate);
		return returnValue;
	}

	/**
	 * 当前日期分钟相加或相减所得日期（+,-）操作,输入一个日期得到分钟加减后的日期。
	 *
	 * @param months
	 * @return
	 */
	public static Date DsDay_Minute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int minutes = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, minutes + minute);
		return calendar.getTime();
	}

	/**
	 * 清理所有天后面的日期时间
	 *
	 * @param c
	 * @return
	 */
	public static Date clearDateAfterDay(Calendar c) {
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		return c.getTime();
	}

	/**
	 * 清理所有天后面的日期时间
	 *
	 * @param date
	 * @return
	 */
	public static Date clearDateAfterDay(Date date) {
		return changeDayEnd(date, 0, 0, 0);
	}

	/**
	 * String转Date
	 *
	 * @param sdate
	 *            日期字符串
	 * @param fmString
	 *            指定日期格式
	 * @return
	 */
	public static Date toDate(String sdate, String fmString) {
		DateFormat df = new SimpleDateFormat(fmString);
		try {
			df.setLenient(false);  
			return df.parse(sdate);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式不正确 ");
		}
	}

	public static Date toSimpleDate(String sdate){
		return toDate(sdate, SIMPLE_DATE_FORMAT);
	}

	private static final String SIMPLE_DATE_FORMAT="yyyy-MM-dd";
	/**
	 * 根据出生日期得到年龄
	 */
	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if ((monthNow < monthBirth) || ((monthNow == monthBirth) && (dayOfMonthNow < dayOfMonthBirth))) {
			age--;
		}
		return age;
	}

	// 计算两个日期之间有多少天
	public static int getDaysBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 当前日期相加或相减所得日期（+,-）操作
	 *
	 * @param months
	 * @return Date
	 */
	public static Date dsDay_Date(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, days + day);
		Date cc = calendar.getTime();
		return cc;
	}

	/**
	 * 取年.
	 *
	 * @param date
	 * @return
	 */
	public static String getFormatYear(Date date) {
		String str = null;
		if (date != null) {
			str = DateFormatUtils.format(date, "yyyy");
		}
		return str;
	}

	public static List<Date> getDateList(Date beginDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		List<Date> dateList = new ArrayList<Date>();

		dateList.add(beginDate);

		while (true) {
			if (clean(beginDate).equals(endDate)) {
				break;
			}
			calendar.add(Calendar.DATE, 1);
			Date currentDate = calendar.getTime();
			dateList.add(currentDate);
			if (currentDate.after(endDate) || clean(currentDate).equals(clean(endDate))) {
				break;
			}

		}
		return dateList;
	}

	/**
	 * 获取昨天的日期
	 *
	 * @return
	 * @throws ParseException
	 */
	public static Date getYestoday() throws ParseException {
		Date d = new Date();
		SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(simpleOldDate.parse(simpleOldDate.format(d)));
		ca.add(Calendar.DATE, -1);
		return ca.getTime();
	}

	
	/**
	 * 得到前一天晚上6点钟.
	 *
	 * @return
	 * @throws ParseException
	 */
	public static Date getYestoday18Hour() throws ParseException {
		Date d = new Date();
		SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		ca.setTime(simpleOldDate.parse(simpleOldDate.format(d)));
		ca.add(Calendar.DATE, -1);
		ca.add(Calendar.HOUR, 18);
		return ca.getTime();
	}

	private static Date clean(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getAfterDay(Date date) {
		SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
		} catch (ParseException e) {
			ca.setTime(getDayStart(new Date()));
		}
		ca.add(Calendar.DATE, 1);
		return ca.getTime();

	}

	public static Date getBeforeDay(Date date) {
		SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar ca = Calendar.getInstance();
		try {
			ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
		} catch (ParseException e) {
			ca.setTime(getDayStart(new Date()));
		}
		ca.add(Calendar.DATE, -1);
		return ca.getTime();

	}

	/**
	 * 返回 该日期的开始处
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static Date getDayStart(String dateStr) {
		Date date = null;
		if (dateStr == null || dateStr == "") {
			return null;
		} else {
			date = toSimpleDate(dateStr);
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getDayEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
	
	public static Date getDayEnd(String dateStr) {
		Date date = null;
		if (dateStr == null || dateStr == "") {
			return null;
		} else {
			date = toSimpleDate(dateStr);
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 得到当前时间的23点.
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayEndBeforeOneH(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		c.set(Calendar.MILLISECOND, 000);
		return c.getTime();
	}

	/**
	 * date1是否早于date2
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean inAdvance(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return false;
		}
		return date1.getTime() < date2.getTime();
	}

	/**
	 * 将日期和时分的两个时间合并到一起
	 *
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDateTime(Date date, Date time) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		Calendar timeCalendar = Calendar.getInstance();
		timeCalendar.setTime(time);
		dateCalendar.set(Calendar.HOUR, timeCalendar.get(Calendar.HOUR));
		dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
		dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		return dateCalendar.getTime();
	}

	public static Date getDateByStr(String dateStr, String formate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		Date date  = sdf.parse(dateStr);
		return date;
	}

	/**
	 * 字符串转换到时间格式
	 *
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date stringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 比较2个日期 前一个日期至少比后一个日期大一天以上
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDateLessOneDayMore(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		if (c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)) {
			return true;
		} else if ((c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) > c2.get(Calendar.MONTH))) {
			return true;
		} else if ((c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) && (c1.get(Calendar.DATE) > c2.get(Calendar.DATE))) {
			return true;
		}
		return false;
	}

	/**
	 * 将日期加年.
	 *
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDateTimeAddYear(Date date, Integer years) {
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		dateCalendar.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR) + years);
		return dateCalendar.getTime();
	}

	public static Long getMinBetween(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		Long min = 0L;
		if (d1.getTimeInMillis() > d2.getTimeInMillis()) {
			min = (d1.getTimeInMillis() - d2.getTimeInMillis()) / (1000 * 60);
		} else {
			min = (d2.getTimeInMillis() - d1.getTimeInMillis()) / (1000 * 60);
		}
		return min;
	}

	/**
	 * 获取今天的日期，去掉时、分、秒
	 */
	public static Date getTodayYMDDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取今天的日期yyyy-MM-dd格式
	 *
	 * @return
	 */
	public static Date getTodayDate() {
		return stringToDate(formatDate(new Date(), SIMPLE_DATE_FORMAT), SIMPLE_DATE_FORMAT);
	}

	/**
	 * 将分钟转成小时数,有小误差
	 */
	public static float convertToHours(Long minutes) {
		if (minutes == null) {
			return 0f;
		}
		String fStr = (new Float(minutes + "") / 60) + "";
		String res = fStr.substring(0, fStr.lastIndexOf(".") + 2);
		return Float.parseFloat(res);
	}

	/**
	 * 将小时数转为分钟,有小误差
	 */
	public static long convertToMinutes(Float hours) {
		if (hours == null) {
			return 0l;
		}
		long h = hours.intValue() * 60;
		Float f = (hours - new Float(hours.intValue())) * 60;
		String fStr = f.toString();
		return h + Long.parseLong(fStr.substring(0, fStr.indexOf(".")));
	}

	/**
	 * 年月日时分秒的日期转只有年月日的日期
	 */
	public static Date toYMDDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);// 24小时制
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 本月第一天
	 */
	public static Date getFirstdayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 本月最后一天
	 */
	public static Date getLastdayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		date = cal.getTime();
		return date;
	}

	/**
	 * date 减去当前日期 . 剩余0天0时0分
	 *
	 * @return str
	 */
	public static String getRemainTimeByCurrentDate(Date date) {
		String str = "剩余0天0时0分";
		if (null != date) {
			Date d = new Date();
			long seconds = (date.getTime() - d.getTime()) / 1000;
			if (seconds > 0) { // 秒
				long day = seconds / (3600 * 24); // 天数
				long house = (seconds % (3600 * 24)) / 3600; // 小时
				long min = (seconds % (3600)) / 60;// 分
				return "剩余" + day + "天" + house + "时" + min + "分";
			}

		}
		return str;
	}
	/**
	 * 0,1,2,3,4,5,6
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 两个时间的分钟数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getMinute(Date beginDate, Date endDate) {
		return (int) ((endDate.getTime() - beginDate.getTime()) / (60 * 1000));
	}

	/**
	 * 两个时间的小时数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getHour(Date beginDate, Date endDate) {
		return (int) ((endDate.getTime() - beginDate.getTime()) / (3600L * 1000));
	}

	/**
	 * 修改时分秒
	 *
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date changeDayEnd(Date date, int hour, int minute, int second) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的秒数
	 */
	public static long diffSec(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / 1000;
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的分钟数
	 */
	public static long diffMinute(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / (60 * 1000);
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的小时数
	 */
	public static long diffHour(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / (60 * 60 * 1000);
	}

	/**
	 * startDate 减 endDate 的差值
	 *
	 * @return long 返回相差的天数
	 */
	public static long diffDay(Date startDate, Date endDate) {
		return diffMillis(startDate, endDate) / (24 * 60 * 60 * 1000);
	}

	private static long diffMillis(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);
		return c2.getTimeInMillis() - c1.getTimeInMillis();
	}
	
	public static Date getNextMonth(Date date,int addMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		int month=cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month+addMonth);
		return cal.getTime();
	}
	
	/**
	 * 获取当前日期的上一个月
	 * @param date
	 * @return
	 */
	public static String lastMonth(Date date, String format){
		if(StringUtil.isEmptyString(format)) {
			format = "yyyyMM";
		}
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(c.getTime());
	}
	
	/**
	 * 获取当前日期本月
	 * @param date
	 * @return
	 */
	public static String thisMonth(Date date, String format){
		if(StringUtil.isEmptyString(format)) {
			format = "yyyyMM";
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(c.getTime());
	}
	
	/** 
	 * 根据周期类型获取当前周期的开始时间
	 * CycleType周期类型: 1,2,3,4   分别表示：天,周,半月,月
	 */
	public static Date getBeginDateByCycle(Byte cycleType){
		Calendar cal = Calendar.getInstance();	     
		cal.setTime(DateUtil.getDayStart(cal.getTime()));
        Calendar beginDate=Calendar.getInstance();		 
    	beginDate.setTime(DateUtil.getDayStart(beginDate.getTime()));
		if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()){				 			     
	        int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;   //系统当前日期的索引
	        if (currentDateIndex <= 0)								//日期索引为0时，代表星期日
	        	 currentDateIndex = 7;
	        beginDate.add(Calendar.DATE,-currentDateIndex+1);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()){				 
	         int maxDate = cal.getActualMaximum(Calendar.DATE);
	         int middleDate = maxDate/2;
	         int currentDate = cal.get(Calendar.DATE);
	         int begin = 0;
	         if(currentDate<=middleDate){
	        	 begin = 1;
	         }else{
	        	 begin = middleDate+1;
	         }
	         beginDate.set(Calendar.DATE, begin);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()){				  		         
	         beginDate.set(Calendar.DATE, 1);
		}
		return beginDate.getTime();
	}
	
	/** 
	 * 根据周期类型获取当前周期的结束时间
	 * CycleType周期类型: 1,2,3,4   分别表示：天,周,半月,月
	 */
	public static Date getEndDateByCycle(Byte cycleType){
		Calendar cal = Calendar.getInstance();	     
		cal.setTime(DateUtil.getDayStart(cal.getTime()));
   	 	Calendar endDate=Calendar.getInstance();
   	 	endDate.setTime(DateUtil.getDayStart(endDate.getTime()));
		if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()){				 			     
	        int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;   //系统当前日期的索引
	        if (currentDateIndex <= 0)								//日期索引为0时，代表星期日
	        	 currentDateIndex = 7;
    	    endDate.add(Calendar.DATE, 7-currentDateIndex);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()){				 
	         int maxDate = cal.getActualMaximum(Calendar.DATE);
	         int middleDate = maxDate/2;
	         int currentDate = cal.get(Calendar.DATE);
	         int end = 0;
	         if(currentDate<=middleDate){
	        	 end = middleDate;
	         }else{
	        	 end = maxDate;	        	 		         	         		          
	         }
    	 	 endDate.set(Calendar.DATE, end);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()){				  		         
	         int maxDate = cal.getActualMaximum(Calendar.DATE);
		 	 endDate.set(Calendar.DATE, maxDate);
		}
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE)+1); 
		endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND)-1);     //日期减一秒为 2015-mm-dd:59:59:9
		return endDate.getTime();
	}
	
	/** 
	 *  根据周期类型与指定时间获取周期的开始时间
	 * CycleType周期类型: 1,2,3,4   分别表示：天,周,半月,月
	 */
	public static Date getBeginDateByCycle(Byte cycleType,Date date){
		Calendar cal = Calendar.getInstance();	     
		cal.setTime(date);
        Calendar beginDate=Calendar.getInstance();	
        beginDate.setTime(date);
    	beginDate.setTime(DateUtil.getDayStart(beginDate.getTime()));
		if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()){				 			     
	        int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;   //系统当前日期的索引
	        if (currentDateIndex <= 0)								//日期索引为0时，代表星期日
	        	 currentDateIndex = 7;
	        beginDate.add(Calendar.DATE,-currentDateIndex+1);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()){				 
	         int maxDate = cal.getActualMaximum(Calendar.DATE);
	         int middleDate = maxDate/2;
	         int currentDate = cal.get(Calendar.DATE);
	         int begin = 0;
	         if(currentDate<=middleDate){
	        	 begin = 1;
	         }else{
	        	 begin = middleDate+1;
	         }
	         beginDate.set(Calendar.DATE, begin);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()){				  		         
	         beginDate.set(Calendar.DATE, 1);
		}
		return beginDate.getTime();
	}
	
	/** 
	 * 根据周期类型与指定时间获取周期的结束时间
	 * CycleType周期类型: 1,2,3,4   分别表示：天,周,半月,月
	 */
	public static Date getEndDateByCycle(Byte cycleType,Date date){
		Calendar cal = Calendar.getInstance();	     
		cal.setTime(date);
   	 	Calendar endDate=Calendar.getInstance();
   	 	endDate.setTime(date);
   	 	endDate.setTime(DateUtil.getDayStart(endDate.getTime()));
		if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()){				 			     
	        int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;   //系统当前日期的索引
	        if (currentDateIndex <= 0)								//日期索引为0时，代表星期日
	        	 currentDateIndex = 7;
    	    endDate.add(Calendar.DATE, 7-currentDateIndex);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()){				 
	         int maxDate = cal.getActualMaximum(Calendar.DATE);
	         int middleDate = maxDate/2;
	         int currentDate = cal.get(Calendar.DATE);
	         int end = 0;
	         if(currentDate<=middleDate){
	        	 end = middleDate;
	         }else{
	        	 end = maxDate;	        	 		         	         		          
	         }
    	 	 endDate.set(Calendar.DATE, end);
		}else if(cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()){				  		         
	         int maxDate = cal.getActualMaximum(Calendar.DATE);
		 	 endDate.set(Calendar.DATE, maxDate);
		}
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE)+1); 
		endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND)-1);     //日期减一秒为 2015-mm-dd:59:59:9
		return endDate.getTime();
	}
	/**
	 * 判断日期格式:yyyy-mm-dd
	 * @param sDate
	 * @return
	 * <pre>
	 * 		建议使用 		isValidDate2
	 * 
	 * 		isValidDate("5012-05-09"); 		true 
	 * 		isValidDate("2015/05/06");		false
	 * </pre>
	 */
	@Deprecated
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }
    
    /**
	 * 判断日期格式:yyyy-mm-dd
	 * @param sDate
	 * @param key	时间区分格式 
	 * @return
	 * <pre>
	 * 			isValidDate2("2012/08/07",'/'); 		true
	 * 			isValidDate2("2012\08\07",'/'); 		false
	 * 			isValidDate2("2012\08\07",'\');			true
	 * 			isValidDate2("2012-08/07",'-');			false
	 * 			isValidDate2("2012-08-07",'-');			true
	 * </pre>
	 */
    public static boolean isValidDate2(String sDate ,char key) {
        String datePattern1 = "\\d{4}"+key+"\\d{2}"+key+"\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param key
     * 	<pre>
     * 		yyyyMMddhhmmss		20151014085734
     * 		yyyyMMddhhmmssss	2015101408570049
     * 
     * </pre>
     * @return
     * @author：wei.peng
     * @date 2015年10月14日
     */
    public static String getDateString(String key){
    	return new SimpleDateFormat(key).format(new Date()).toString();
    }
    
    
}
