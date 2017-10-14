package cn.mobilizer.channel.colgate.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.mobilizer.channel.util.DateTimeUtils;

public class MonthVo implements Serializable {

	private static final long serialVersionUID = 5012259918260469594L;
	private String name;
	private Integer monthId;

	private final static String[] EN_MONTH = new String[] { "Jan", "Feb","Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov","Dec" };
	private final static Integer START_MONTH = 201401;

	public List<MonthVo> initDefault() {
		Date currDate = DateTimeUtils.getCurrentDate();
		Integer endMonth = Integer.parseInt(DateTimeUtils.formatTime(currDate,DateTimeUtils.yyyyMM));
		return this.initMonth(START_MONTH, endMonth);
	}

	public List<MonthVo> initMonth(Integer startMonth, Integer endMonth) {
		Date startDate = DateTimeUtils.StringToDate(String.valueOf(startMonth),DateTimeUtils.yyyyMM);
		Date endDate = DateTimeUtils.StringToDate(String.valueOf(endMonth),DateTimeUtils.yyyyMM);

		List<MonthVo> months = new ArrayList<MonthVo>();
		while (startDate.getTime() <= endDate.getTime()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			Integer year = calendar.get(Calendar.YEAR);
			Integer month = calendar.get(Calendar.MONTH) + 1;
			StringBuilder builder = new StringBuilder();
			builder.append(year);
			if (month < 10) {
				builder.append("0");
			}
			builder.append(month);
			Integer fullMonth = Integer.parseInt(builder.toString());

			MonthVo monthVo = new MonthVo();
			monthVo.setMonthId(fullMonth);
			monthVo.setName(EN_MONTH[month - 1] + " " + year);
			endDate = DateTimeUtils.addMonths(endDate, -1);
			months.add(monthVo);
		}
		return months;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

}
