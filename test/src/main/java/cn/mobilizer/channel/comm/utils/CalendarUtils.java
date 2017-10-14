package cn.mobilizer.channel.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 封装了和日期相关的通用方法
 * 
 * @author none
 * @date 2013-10-24
 */
public class CalendarUtils {
	
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String DATE_NUMBER_PATTERN = "yyyyMMdd";

	public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final String HH_MM_PATTERN = "HH:mm";
	
	public static final String YYYY_MM_DD_HH_MM_PATTERN = "yyyy-MM-dd HH:mm";
	
	
    /**
     * 根据起始日和相隔分钟数 计算终止日
     *
     * @param startDate
     * @param minute
     * @return
     */
    public static Date getEndDateByMinute(Date startDate, Long minute) {

    	if (minute==null) {
    		return startDate;
		}
        return DateUtils.addMinutes(startDate,minute.intValue() );
    }
    
    
    
    

	/**
	 * 计算两个日期之间的所有日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @return List<Date>
	 * @说明 该返回值的List会包含开始日期和结束日期
	 */
	public static List<Date> getDates(Date startDate, Date endDate) {
		List<Date> list = new ArrayList<Date>();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		// 如果开始日期小于结束日期
		if (startCalendar.after(endCalendar)) {
			return null;
		}
		// 计算2个日期之间的日期
		while (startCalendar.before(endCalendar)) {
			list.add(startCalendar.getTime());
			startCalendar.add(Calendar.DATE, 1);
		}
		// 添加结束日期
		list.add(endDate);
		return list;
	}

	/**
	 * 计算两个日期之间的所有日期（是否包含开始、结束日期则按参数传递），时间列表从开始到结束有序。
	 * 
	 * @param startDate
	 * @param includeStart
	 * @param endDate
	 * @param includeEnd
	 * @return 该返回值的List非null,如果没有符合条件的日期，则list的size为0.
	 */
	public static List<Date> getDatesExtension(Date startDate, boolean includeStart, Date endDate, boolean includeEnd) {
		List<Date> list = new ArrayList<Date>();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		// 如果开始日期小于结束日期
		if (startCalendar.after(endCalendar)) {
			return null;
		}

		if (startCalendar.before(endCalendar)) {
			// 是否包含开始日期
			if (includeStart) {
				list.add(startDate);
			}

			startCalendar.add(Calendar.DATE, 1);
			// 计算2个日期之间的日期
			while (startCalendar.before(endCalendar)) {
				list.add(startCalendar.getTime());
				startCalendar.add(Calendar.DATE, 1);
			}

			// 是否包含结束日期
			if (includeEnd) {
				list.add(endDate);
			}
		}

		return list;
	}
	
	/**
	 * 获得当前月的第一天
	 */
	public static Date getFirstDayOfMonth(Date dateTime){
		if(dateTime == null) return dateTime;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}
	
	/**
	 * 获得该月的最后一天
	 * @param dateTime
	 * @return
	 */
	public static Date getLastDayOfMonth(Date dateTime){
		if(dateTime == null) return dateTime;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
	
	/**
	 * 将小时、分钟、秒数归零
	 * 
	 * @param dateTime
	 * @return
	 */
	public static Date getDateFromDateTime(Date dateTime) {
		return getDateFormatTime(dateTime,0,0,0);
	}
	
	/**
	 * 将小时、分钟、秒数 设置为指定参数
	 * @param dateTime
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static Date getDateFormatTime(Date dateTime,int hours,int minutes,int seconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期之间的所有日期，并根据周末进行过滤
	 * 
	 * @param startDate
	 * @param endDate
	 * @param weeks
	 * @return List<Date>
	 * @说明 该返回值的List为日期和周末的交集
	 */
	public static List<Date> getDates(Date startDate, Date endDate, List<Integer> weeks) {
		List<Date> list = new ArrayList<Date>();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		// 如果开始日期小于结束日期
		if (startCalendar.after(endCalendar)) {
			return null;
		}
		// 计算2个日期之间的日期
		while (!endCalendar.before(startCalendar)) {
			// 获得今天星期几
			int dayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK);
			// 根据星期数据进行过滤
			if ((weeks == null) || weeks.contains(dayOfWeek)) {
				list.add(startCalendar.getTime());
			}
			startCalendar.add(Calendar.DATE, 1);
		}
		return list;
	}

	/**
	 * 计算两个日期之间的相隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return int
	 * 
	 */
	public static int getDayCounts(Date startDate, Date endDate) {
		int days = 0;
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		// 如果开始日期小于截止日期
		if (startCalendar.before(endCalendar)) {
			while (startCalendar.before(endCalendar)) {
				days++;
				startCalendar.add(Calendar.DATE, 1);
			}
		} else if (startCalendar.after(endCalendar)) {
			// 如果开始日期大于截止日期
			while (startCalendar.after(endCalendar)) {
				days--;
				startCalendar.add(Calendar.DATE, -1);
			}
		} else {
			return 0;
		}
		return days;
	}

	/**
	 * 获得当前日期的字符串
	 * 
	 * @return
	 */
	public static String getDateFormatString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 解析日期字符串
	 * 
	 * @return
	 */
	public static Date getDateFromString(String dateStr, String pattern) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * 字符串获得日期
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date getDateFormatDate(String date, String pattern) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	public static void main(String[] args) throws Exception {
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));

	}
}
