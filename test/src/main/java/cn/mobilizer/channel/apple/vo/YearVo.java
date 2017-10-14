package cn.mobilizer.channel.apple.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.mobilizer.channel.util.DateTimeUtils;

public class YearVo implements Serializable {

	private static final long serialVersionUID = -3465477384908807155L;
	private final static int START_YEAR = 2014;
	
	private int yearId;

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	
	public List<YearVo> initYearVo(){
		int year = DateTimeUtils.getCurrentYear();
		List<YearVo> years = new ArrayList<YearVo>();
		for (int i = year; i >= START_YEAR; i--) {
			YearVo yearVo = new YearVo();
			yearVo.setYearId(i);
			years.add(yearVo);
		}
		return years;
	}
	
	public List<YearVo> initYearVo(Integer startYear){
		int year = DateTimeUtils.getCurrentYear();
		List<YearVo> years = new ArrayList<YearVo>();
		for (int i = year; i >= startYear; i--) {
			YearVo yearVo = new YearVo();
			yearVo.setYearId(i);
			years.add(yearVo);
		}
		return years;
	}
}
