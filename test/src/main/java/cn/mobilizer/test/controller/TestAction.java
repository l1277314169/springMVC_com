package cn.mobilizer.test.controller;

import java.util.Date;

import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.util.Constants;

public class TestAction{
public static void main(String[] args) {
	Date startDate = null;
	Date endDate  = null;
	try {
		startDate = DateUtil.toDate("2015-08-03  5:05:00",DateUtil.dateTimeFormat);
		endDate = DateUtil.toDate("2015-08-03  6:00:00", DateUtil.dateTimeFormat);
	} catch (Exception e) {
	System.out.println("tt");
	}
	long setTimeNeighbor = DateUtil.toDate(DateUtil.formatSimpleDate(startDate)+Constants.HMS,DateUtil.dateTimeFormat).getTime();
	/**下班时间不能超过上班时间第二天的05:59:59*/
	long nextDay = DateUtil.toDate(DateUtil.getFormatDate(DateUtil.dsDay_Date(startDate,1),DateUtil.dateFormat)+Constants.HMS,DateUtil.dateTimeFormat).getTime();
	long currDay =  DateUtil.toDate(DateUtil.getFormatDate(startDate,DateUtil.dateFormat)+Constants.HMS,DateUtil.dateTimeFormat).getTime();
	if(startDate.getTime() > setTimeNeighbor){
		if(endDate.getTime() > nextDay){
			System.out.println("vv");
		}
	}else{
		if(endDate.getTime() > currDay){
			System.out.println("ddd");
		}
	}
}
}
