package cn.mobilizer.channel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * @author Fred.Chung
 * 2014/10/14
 */
public final class DateTimeUtils {
	
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMdd = "yyyy-MM-dd";
	public static final String yyyyMMdd2 = "yyyyMMdd";
	public static final String yyyyMM = "yyyyMM";
	public static final String yyyyMM2 = "yyyy-MM";
	public static final String hhmmss = "hh:mm:ss";
	public static final String yyyy = "yyyy";
	public static final String MM = "MM";

	private DateTimeUtils() {
	}
	
	public static final String getFormatTime(String formatStr){
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(new GregorianCalendar().getTime());
	}

	/**
	 * 返回给定日期的前days[-]天或者后days[+]天的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
	
	
	public static Date addMonths(Date date, int months) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	public static Date StringToDate(String strDate,String pattern){
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Date date1, Date date2) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(date1);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(date2);
		if (d1.after(d2)) {
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
	
	
	public static int daysBetween(Date smdate,Date bdate) throws ParseException{    
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));           
    }
	
	public static int compareDate(String date1, String date2,SimpleDateFormat sdf) throws ParseException {
		return compareDate(sdf.parse(date1),sdf.parse(date2));
	}

	public static int compareDate(Date date1, Date date2) {
		if (null == date1) {
			if (null == date2) {
				return 0;
			} else {
				return -1;
			}
		} else {
			if (null == date2) {
				return -1;
			} else {
				return date1.compareTo(date2);
			}
		}
	}

	public static Date getCurrentTime() {
		Calendar calendar = new GregorianCalendar();
		return calendar.getTime();
	}

	public static Date getCurrentDate() {
		Calendar calendar = new GregorianCalendar();
		Date time = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String s = format.format(time);
		try {
			return format.parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static int getCurrentYear() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static String getWeekOfDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			break;
		}
		return "Unknown";
	}

	public static String formatTime(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static Date toTime(String time, String pattern) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(time);
	}

	public static Date getFirstDayOfCurrentWeek() {
		Calendar calendar = new GregorianCalendar();
		int day = calendar.getActualMinimum(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DAY_OF_WEEK, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	public static Date getFirstDayOfCurrentMonth() {
		Calendar calendar = new GregorianCalendar();
		int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getFirstDayOfCurrentSeason() {
        Calendar cDay = Calendar.getInstance();     
        cDay.setTime(new Date());  
        int curMonth = cDay.get(Calendar.MONTH);  
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){    
            cDay.set(Calendar.MONTH, Calendar.JANUARY);  
        }  
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){    
            cDay.set(Calendar.MONTH, Calendar.APRIL);  
        }  
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {    
            cDay.set(Calendar.MONTH, Calendar.JULY);  
        }  
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {    
            cDay.set(Calendar.MONTH, Calendar.OCTOBER);  
        }  
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return cDay.getTime();     
	}
	public static Date getLastDayOfCurrentMonth() {
		Calendar calendar = new GregorianCalendar();
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	
	public static Date getLastDay(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getFirstDayOfDate(String date) throws Exception {
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		String nowDate = year+"-"+month+"-"+1;
		return toTime(nowDate, "yyyy-MM-dd");
	}
	
	public static Date getLastDayOfDate(String date) throws Exception {
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[2]);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);
		int maxDate = cal.getActualMaximum(Calendar.DATE);
		String nowDate = year+"-"+month+"-"+maxDate;
		return toTime(nowDate, "yyyy-MM-dd");
	}
	
	 /**获取指定日期的星期信息
	 * @author Nany
	 * 2015年2月2日下午2:40:02
	 * @param date
	 * @return
	 */
	public static String getWeek(Date date){  
	        String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
	        if(week_index<0){  
	            week_index = 0;  
	        }   
	        return weeks[week_index];  
	    }  
	
	/**
	 * 当前时间在今年的第几周
	 * @param date
	 * @return
	 */
	public static Integer getWeekOfYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	
	public static void main(String[] args) {
		Date str = DateTimeUtils.addMonths(new Date(), -2);
		System.out.println(DateTimeUtils.formatTime(str, DateTimeUtils.yyyyMM));
	}
	
}
