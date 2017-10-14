package cn.mobilizer.channel.ctTask.service.impl;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientUserDao;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.ctTask.dao.CtTaskDao;
import cn.mobilizer.channel.ctTask.dao.CtTaskDataDao;
import cn.mobilizer.channel.ctTask.dao.CtTaskObjectDao;
import cn.mobilizer.channel.ctTask.dao.CtTaskParameterDao;
import cn.mobilizer.channel.ctTask.po.CtTask;
import cn.mobilizer.channel.ctTask.service.CtTaskDataService;
import cn.mobilizer.channel.ctTask.service.CtTaskService;
import cn.mobilizer.channel.ctTask.vo.ExportCtTaskDataSerchVo;
import cn.mobilizer.channel.ctTask.vo.StoreTask;
import cn.mobilizer.channel.ctTask.vo.TaskCycle;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;

@Service
public class CtTaskServiceImpl implements CtTaskService {

	@Autowired
	private CtTaskDao ctTaskDao;
	@Autowired
	private CtTaskParameterDao ctTaskParameterDao;
	@Autowired
	private CtTaskDataDao ctTaskDataDao;
	@Autowired
	private CtTaskObjectDao ctTaskObjectDao;
	@Autowired
	private CtTaskDataService ctTaskDataService;
	@Autowired
	private ClientUserDao clientUserDao;

	@Override
	public List<CtTask> selectByParams(Map<String, Object> param) throws BusinessException {
		List<CtTask> ctTaskList = ctTaskDao.selectByParams(param);
		for (CtTask ctTask : ctTaskList) {
			getValidity(ctTask, null); // 计算周期内的有效周期时间
			if (ctTask.getStartDate() != null) {
				Calendar startDate = new GregorianCalendar();
				startDate.setTime(DateUtil.toSimpleDate(ctTask.getStartDate()));
				Calendar now = new GregorianCalendar();
				if (!startDate.before(now)) {
					ctTask.setIsInCycel(false);
				}
			}
			if (ctTask.getEndDate() != null) {
				Calendar endDate = new GregorianCalendar();
				endDate.setTime(DateUtil.toSimpleDate(ctTask.getEndDate()));
				Calendar now = new GregorianCalendar();
				if (endDate.before(now)) {
					ctTask.setIsInCycel(false);
				}
			}
		}
		return ctTaskList;
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param) throws BusinessException {
		return ctTaskDao.selectByParamCount(param);
	}

	@Override
	public CtTask getCtTask(Integer ctTaskId) {
		return ctTaskDao.selectByPrimaryKey(ctTaskId);
	}

	@Override
	public Integer insert(CtTask ctTask) {
		return ctTaskDao.insert(ctTask);
	}

	@Override
	public List<CtTask> getTaskList(Map<String, Object> params) throws BusinessException {
		return ctTaskDao.selectTaskList(params);
	}

	@Override
	public List<CtTask> selectTaskByPosition(Map<String, Object> params) throws BusinessException {
		return ctTaskDao.selectTaskByPosition(params);
	}

	/**
	 * 查询门点的周期任务历史数据
	 */
	@Override
	public List<StoreTask> selectTaskDataStore(StoreTask storeTask, Long pageStart, Integer pageSize, Integer clientId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctTaskId", storeTask.getCtTaskId());
		params.put("clientId", storeTask.getClientId());
		params.put("clientUserIds", storeTask.getUserIds());
		params.put("subAllStructureId", storeTask.getSubAllStructureId());
		params.put("storeNo", storeTask.getStoreNo() == null ? null : storeTask.getStoreNo());
		params.put("storeName", storeTask.getStoreName() == null ? null : storeTask.getStoreName());
		params.put("updateClientUserName", storeTask.getUpdateClientUserName()); // 更新人
		params.put("provinceId", storeTask.getProvinceId() == null ? null : storeTask.getProvinceId());
		params.put("cityId", storeTask.getCityId() == null ? null : storeTask.getCityId());
		params.put("startDate", storeTask.getStartDate() == null ? null : DateUtil.getDayStart(storeTask.getStartDate()));
		params.put("endDate", storeTask.getEndDate() == null ? null : DateUtil.getDayEnd(storeTask.getEndDate()));
		params.put("_start", pageStart);
		params.put("_size", pageSize);
		// params.put("_orderby", "ctd.last_update_time");
		// params.put("_order", "desc");
		if (storeTask.getIsMobileSearch() != null) {
			params.put("isMobileSearch", storeTask.getIsMobileSearch());
		}
		CtTask ctTask = ctTaskDao.selectByPrimaryKey(storeTask.getCtTaskId());
		if (storeTask.getTaskCycle() != null && storeTask.getTaskCycle().indexOf("~") > 0) {
			String beginDate = StringUtils.isEmpty(storeTask.getTaskCycle().split("~")[0]) ? "" : storeTask.getTaskCycle().split("~")[0].trim();
			String endDate = StringUtils.isEmpty(storeTask.getTaskCycle().split("~")[1]) ? "" : storeTask.getTaskCycle().split("~")[1].trim();
			params.put("startDate", DateUtil.getDayStart(beginDate));
			if (StringUtils.isNotEmpty(endDate)) {
				params.put("endDate", DateUtil.getDayEnd(endDate));
			}
		}

		List<StoreTask> storeTaskList = null;
		if (clientId == 7) {// 尤妮佳查询所有的
			if (null == storeTask.getReportData() || "".equals(storeTask.getReportData())) { // 查询所有数据
				if (storeTask.getCycle().equals(ChannelEnum.CYCLE_TYPE.DAY.getKey())) { // 如果是按天的周期，需要去关联calendar日历表
					storeTaskList = ctTaskDao.selectTaskDataStoreForDay(params);
				} else {
					storeTaskList = ctTaskDao.selectTaskDataStore(params);
				}
			} else {
				if (storeTask.getCycle().equals(ChannelEnum.CYCLE_TYPE.DAY.getKey())) { // 如果是按天的周期，需要去关联calendar日历表
					if (storeTask.getReportData().equals(Constants.IS_REPORT_DATA)) { // 查看已经提交数据
						storeTaskList = ctTaskDao.selectTaskDataStoreDayForReportData(params);
					} else {// 查看未提交的数据
						params.put("reportData", storeTask.getReportData());
						storeTaskList = ctTaskDao.selectTaskDataStoreForDay(params);
					}
				} else {// 按月，按周查看，不需要关联calendar表
					if (storeTask.getReportData().equals(Constants.IS_REPORT_DATA)) { // 查看已经提交数据
						storeTaskList = ctTaskDao.selectTaskDataStoreDayForReportData(params);
					} else {// 查看未提交的数据
						params.put("reportData", storeTask.getReportData());
						storeTaskList = ctTaskDao.selectTaskDataStoreForDay2(params);
					}
				}
			}
			boolean isCycle;
			Date start = null;
			Date date;
			if (storeTaskList != null && storeTaskList.size() > 0) {
				for (StoreTask storeTaskInfo : storeTaskList) {
					date = new Date();
					if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
						try {
							start = sdf.parse(storeTaskInfo.getStartDate());
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						start = storeTaskInfo.getStartTime() == null ? date : storeTaskInfo.getStartTime();
					}
					isCycle = getValidity2(ctTask, start); // 计算是否在同一个周期内,控制只能修改当前周期的数据
					if (ctTask.getIsSaveSameCycle() != null && ctTask.getIsSaveSameCycle()) { // 如果在同一个周期内
						if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
							if (isCycle) {
								storeTaskInfo.setIsInCycel(true);
							}
						} else {
							Boolean flag = true;
							if (ctTask.getValidFromDate() != null) {
								Calendar startDate = new GregorianCalendar();
								startDate.setTime(ctTask.getValidFromDate());
								Calendar now = new GregorianCalendar();
								if (!startDate.before(now)) {
									flag = false;
								}
							}
							if (ctTask.getValidEndDate() != null) {
								Calendar endDate = new GregorianCalendar();
								endDate.setTime(ctTask.getValidEndDate());
								Calendar now = new GregorianCalendar();
								if (endDate.before(now)) {
									flag = false;
								}
							}
							if (flag) {
								storeTaskInfo.setIsInCycel(true);
							}
						}
					} else {
						storeTaskInfo.setIsInCycel(false);
					}
				}
			}
		} else {// 其他客户只查询已提交数据
			storeTaskList = ctTaskDao.selectTaskDataStoreDayForReportData(params);
		}
		return storeTaskList;
	}
	/**
	 * 判断Date是否在任务周期内，Date为空时默认为当前时间
	 */
	public boolean getValidity2(CtTask ctTask, Date date) throws BusinessException {
		String currTime;
		String datTime;
		boolean falge = true;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			cal.setTime(date); // 用于保存时判断是否在同一周期内
		}
		cal.setTime(DateUtil.getDayStart(cal.getTime()));
		Calendar beginDate = Calendar.getInstance();
		if (date != null) {
			beginDate.setTime(date); // 用于保存时判断是否在同一周期内
		}
		beginDate.setTime(DateUtil.getDayStart(beginDate.getTime()));
		Calendar endDate = Calendar.getInstance();
		if (date != null) {
			endDate.setTime(date); // 用于保存时判断是否在同一周期内
		}
		endDate.setTime(DateUtil.getDayStart(endDate.getTime()));
		int begin = 0;
		int end = 0;
		if (ctTask.getValidFrom() != null) {
			begin = ctTask.getValidFrom().intValue();
		}
		if (ctTask.getValidEnd() != null) {
			end = ctTask.getValidEnd().intValue();
		}
		if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.DAY.getKey().byteValue()) {
			ctTask.setIsInCycel(true);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()) {
			if (begin < 0) {
				begin = 8 + begin; // 小于0时 -1代表周内最后一天 所以加1
			}
			if (end < 0) {
				end = 8 + end;
			}
			int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // 系统当前日期的索引
			if (currentDateIndex <= 0)
				currentDateIndex = 7;
			if (currentDateIndex >= begin && currentDateIndex <= end) {
				ctTask.setIsInCycel(true);
			} else {
				ctTask.setIsInCycel(false);
			}
			beginDate.add(Calendar.DATE, begin - currentDateIndex);
			endDate.add(Calendar.DATE, end - currentDateIndex);
			ctTask.setValidFromDate(beginDate.getTime());
			ctTask.setValidEndDate(endDate.getTime());
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			int middleDate = maxDate / 2;
			int currentDate = cal.get(Calendar.DATE);
			if (currentDate <= middleDate) {
				if (begin < 0) {
					begin = middleDate + begin + 1;
				}
				if (end < 0) {
					end = middleDate + begin + 1;
				}
				if (currentDate >= begin && currentDate <= end) {
					ctTask.setIsInCycel(true);
				} else {
					ctTask.setIsInCycel(false);
				}
			} else {
				if (begin < 0) {
					begin = maxDate + begin + 1;
				} else {
					begin = middleDate + begin;
				}
				if (end < 0) {
					end = maxDate + begin + 1;
				} else {
					end = middleDate + begin;
				}
				if (currentDate >= begin && currentDate <= end) {
					ctTask.setIsInCycel(true);
				} else {
					ctTask.setIsInCycel(false);
				}
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
			Calendar curr = Calendar.getInstance();
			curr.setTimeInMillis(System.currentTimeMillis());
			curr.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			Calendar dat = Calendar.getInstance();
			if (date != null) {// 获取周期开始时间当月的最后一天
				dat.setTime(date);
				dat.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
				beginDate = dat;
				dat.add(Calendar.MONDAY, 1);
			}
			currTime = sdf.format(curr.getTime());
			datTime = sdf.format(dat.getTime());
			if (currTime.equals(datTime)) {
				ctTask.setIsInCycel(true);
			} else {
				ctTask.setIsInCycel(false);
				falge = false;
			}
		}
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE) + 1);
		endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND) - 1); // 日期减一秒为
																		// 2015-mm-dd:59:59:9
		ctTask.setValidFromDate(beginDate.getTime());
		ctTask.setValidEndDate(endDate.getTime());
		if (date != null) {
			boolean flag = endDate.getTime().before(getBeginValidity(ctTask)); // 上一次结束时间与本次周期时间比较
			if (flag) {
				ctTask.setIsSaveSameCycle(false);
			} else {
				ctTask.setIsSaveSameCycle(true); // 当前时间大于上一次的周期时间，不在同一周期内
			}
			if (falge) {
				ctTask.setIsSaveSameCycle(true);
			} else {
				ctTask.setIsSaveSameCycle(false);
			}
			ctTask.setBeforeValidEndDate(endDate.getTime());
		}
		return falge;
	}

	@Override
	public Integer selectTaskDataStoreCount(StoreTask storeTask, Integer clientId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctTaskId", storeTask.getCtTaskId());
		params.put("clientId", storeTask.getClientId());
		params.put("clientUserIds", storeTask.getUserIds());
		params.put("subAllStructureId", storeTask.getSubAllStructureId());
		params.put("storeNo", storeTask.getStoreNo() == null ? null : storeTask.getStoreNo());
		params.put("storeName", storeTask.getStoreName() == null ? null : storeTask.getStoreName());
		params.put("provinceId", storeTask.getProvinceId() == null ? null : storeTask.getProvinceId());
		params.put("cityId", storeTask.getCityId() == null ? null : storeTask.getCityId());
		params.put("startDate", storeTask.getStartDate() == null ? null : DateUtil.getDayStart(storeTask.getStartDate()));
		params.put("endDate", storeTask.getEndDate() == null ? null : DateUtil.getDayEnd(storeTask.getEndDate()));
		if (storeTask.getTaskCycle() != null && storeTask.getTaskCycle().indexOf("~") > 0) {
			String beginDate = StringUtils.isEmpty(storeTask.getTaskCycle().split("~")[0]) ? "" : storeTask.getTaskCycle().split("~")[0].trim();
			String endDate = StringUtils.isEmpty(storeTask.getTaskCycle().split("~")[1]) ? "" : storeTask.getTaskCycle().split("~")[1].trim();
			params.put("startDate", DateUtil.getDayStart(beginDate));
			if (StringUtils.isNotEmpty(endDate)) {
				params.put("endDate", DateUtil.getDayEnd(endDate));
			}
		}

		// 尤妮佳查询所有的
		Integer items = 0;
		if (clientId == 7) {
			if (null == storeTask.getReportData() || "".equals(storeTask.getReportData())) { // 查询所有数据
				if (storeTask.getCycle().equals(ChannelEnum.CYCLE_TYPE.DAY.getKey())) { // 如果是按天的周期，需要去关联calendar日历表
					items = ctTaskDao.selectTaskDataStoreDayCount(params);
				} else {
					items = ctTaskDao.selectTaskDataStoreCount(params);
				}
			} else {
				if (storeTask.getCycle().equals(ChannelEnum.CYCLE_TYPE.DAY.getKey())) { // 如果是按天的周期，需要去关联calendar日历表
					if (storeTask.getReportData().equals(Constants.IS_REPORT_DATA)) { // 查看已经提交数据
						items = ctTaskDao.selectTaskDataStoreCountForReportData(params);
					} else {// 查看未提交的数据
						params.put("reportData", storeTask.getReportData());
						items = ctTaskDao.selectTaskDataStoreDayCount(params);
					}
				} else {
					if (storeTask.getReportData().equals(Constants.IS_REPORT_DATA)) { // 查看已经提交数据
						items = ctTaskDao.selectTaskDataStoreCountForReportData(params);
					} else {// 查看未提交的数据
						params.put("reportData", storeTask.getReportData());
						items = ctTaskDao.selectTaskDataStoreDayCount2(params);
					}
				}
			}
		} else {// 其他客户只查询已提交数据
			items = ctTaskDao.selectTaskDataStoreCountForReportData(params);
		}
		return items;
	}

	/**
	 * 从2015-1-5 开始根据不同周期类型获取至今的所有周、半月、年的时间段
	 */
	@Override
	public List<TaskCycle> getTaskCycelList(Integer taskId) throws BusinessException {
		CtTask ctTask = ctTaskDao.selectByPrimaryKey(taskId);
		List<TaskCycle> taskCycels = new ArrayList<TaskCycle>();
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		c_end.setTime(getTaskCycleMaxDate(ctTask.getCycleType()));
		c_begin.set(2015, 0, 5); // Calendar的月从0-11，所以4月是3.
		String str = "";
		if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()) {
			DateFormatSymbols dfs = new DateFormatSymbols();
			String[] weeks = dfs.getWeekdays();
			c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
			while (c_begin.before(c_end)) {
				if (weeks[c_begin.get(Calendar.DAY_OF_WEEK)].equals("星期一")) {
					str += new java.sql.Date(c_begin.getTime().getTime()).toString() + " ~ ";
				}
				if (weeks[c_begin.get(Calendar.DAY_OF_WEEK)].equals("星期日")) {
					str += new java.sql.Date(c_begin.getTime().getTime()).toString() + ",";
				}
				c_begin.add(Calendar.DAY_OF_YEAR, 1);
			}
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化为年月
			Calendar min = Calendar.getInstance();
			min.setTime(DateUtil.getDayStart(min.getTime()));
			Calendar max = Calendar.getInstance();
			max.setTime(DateUtil.getDayStart(max.getTime()));
			int maxDate = max.getActualMaximum(Calendar.DATE);
			int middleDate1 = maxDate / 2;
			int currentDateIndex = max.get(Calendar.DATE);
			if (currentDateIndex <= middleDate1) {
				max.set(Calendar.DATE, middleDate1);
			} else {
				max.set(Calendar.DATE, maxDate);
			}
			min.setTime(c_begin.getTime());
			Calendar curr = min;
			while (curr.before(c_end)) {
				// 月初
				Calendar calendar = Calendar.getInstance();
				Calendar calendarNext = Calendar.getInstance();
				calendar.setTime(curr.getTime());
				int index = calendar.get(Calendar.DAY_OF_MONTH);
				calendar.add(Calendar.DATE, (1 - index));
				calendarNext = calendar;
				String firstDate = sdf.format(calendar.getTime());
				// 月中
				calendar = Calendar.getInstance();
				calendar.setTime(curr.getTime());
				int middleIndex = calendar.get(Calendar.DAY_OF_MONTH);
				calendar.add(Calendar.DATE, (1 - middleIndex));
				int maxDate1 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				calendar.add(Calendar.DATE, (maxDate1 / 2) - 1);
				String middleDate = sdf.format(calendar.getTime());
				str += firstDate + " ~ " + middleDate + ","; // 上半月
				calendarNext.add(Calendar.DATE, (maxDate1 / 2));
				String meddleDateNext = sdf.format(calendarNext.getTime());
				if (calendar.before(max)) {
					// 月末
					calendar = Calendar.getInstance();
					calendar.setTime(curr.getTime());
					calendar.add(Calendar.MONTH, 1);
					int endIndex = calendar.get(Calendar.DAY_OF_MONTH);
					calendar.add(Calendar.DATE, (-endIndex));
					String endDate = sdf.format(calendar.getTime());
					str += meddleDateNext + " ~ " + endDate + ","; // 后半月
				}
				curr.add(Calendar.MONTH, 1);
			}
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化为年月
			Calendar min = Calendar.getInstance();
			min.setTime(c_begin.getTime());
			Calendar curr = min;
			while (curr.before(c_end)) {
				// 月初
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(curr.getTime());
				int index = calendar.get(Calendar.DAY_OF_MONTH);
				calendar.add(Calendar.DATE, (1 - index));
				String firstDate = sdf.format(calendar.getTime());
				// 月末
				calendar = Calendar.getInstance();
				calendar.setTime(curr.getTime());
				calendar.add(Calendar.MONTH, 1);
				int endIndex = calendar.get(Calendar.DAY_OF_MONTH);
				calendar.add(Calendar.DATE, (-endIndex));
				String endDate = sdf.format(calendar.getTime());
				str += firstDate + " ~ " + endDate + ",";
				curr.add(Calendar.MONTH, 1);
			}
		}
		for (String strCycle : str.split(",")) {
			TaskCycle taskCycle = new TaskCycle();
			taskCycle.setDataCycle(strCycle);
			taskCycels.add(taskCycle);
		}
		Collections.reverse(taskCycels); // 日期顺序反转
		if (taskCycels != null && taskCycels.size() > 0) {
			taskCycels.get(0).setCurrentCycle(true);
		}
		return taskCycels;
	}

	/**
	 * 判断Date是否在任务周期内，Date为空时默认为当前时间
	 */
	@Override
	public void getValidity(CtTask ctTask, Date date) throws BusinessException {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date); // 用于保存时判断是否在同一周期内
		}
		cal.setTime(DateUtil.getDayStart(cal.getTime()));
		Calendar beginDate = Calendar.getInstance();
		if (date != null) {
			beginDate.setTime(date); // 用于保存时判断是否在同一周期内
		}
		beginDate.setTime(DateUtil.getDayStart(beginDate.getTime()));
		Calendar endDate = Calendar.getInstance();
		if (date != null) {
			endDate.setTime(date); // 用于保存时判断是否在同一周期内
		}
		endDate.setTime(DateUtil.getDayStart(endDate.getTime()));
		int begin = 0;
		int end = 0;
		if (ctTask.getValidFrom() != null) {
			begin = ctTask.getValidFrom().intValue();
		}
		if (ctTask.getValidEnd() != null) {
			end = ctTask.getValidEnd().intValue();
		}
		if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.DAY.getKey().byteValue()) {
			ctTask.setIsInCycel(true);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()) {
			if (begin < 0) {
				begin = 8 + begin; // 小于0时 -1代表周内最后一天 所以加1
			}
			if (end < 0) {
				end = 8 + end;
			}
			int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // 系统当前日期的索引
			if (currentDateIndex <= 0)
				currentDateIndex = 7;
			if (currentDateIndex >= begin && currentDateIndex <= end) {
				ctTask.setIsInCycel(true);
			} else {
				ctTask.setIsInCycel(false);
			}
			beginDate.add(Calendar.DATE, begin - currentDateIndex);
			endDate.add(Calendar.DATE, end - currentDateIndex);
			ctTask.setValidFromDate(beginDate.getTime());
			ctTask.setValidEndDate(endDate.getTime());
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			int middleDate = maxDate / 2;
			int currentDate = cal.get(Calendar.DATE);
			if (currentDate <= middleDate) {
				if (begin < 0) {
					begin = middleDate + begin + 1;
				}
				if (end < 0) {
					end = middleDate + begin + 1;
				}
				if (currentDate >= begin && currentDate <= end) {
					ctTask.setIsInCycel(true);
				} else {
					ctTask.setIsInCycel(false);
				}
			} else {
				if (begin < 0) {
					begin = maxDate + begin + 1;
				} else {
					begin = middleDate + begin;
				}
				if (end < 0) {
					end = maxDate + begin + 1;
				} else {
					end = middleDate + begin;
				}
				if (currentDate >= begin && currentDate <= end) {
					ctTask.setIsInCycel(true);
				} else {
					ctTask.setIsInCycel(false);
				}
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			int currentDate = cal.get(Calendar.DATE);
			if (begin < 0) {
				begin = maxDate + begin + 1;
			}
			if (end < 0) {
				end = maxDate + end + 1;
			}
			if (currentDate >= begin && currentDate <= end) {
				ctTask.setIsInCycel(true);
			} else {
				ctTask.setIsInCycel(false);
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		}
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE) + 1);
		endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND) - 1); // 日期减一秒为
																		// 2015-mm-dd:59:59:9
		ctTask.setValidFromDate(beginDate.getTime());
		ctTask.setValidEndDate(endDate.getTime());
		if (date != null) {
			boolean flag = endDate.getTime().before(getBeginValidity(ctTask)); // 上一次结束时间与本次周期时间比较
			if (flag) {
				ctTask.setIsSaveSameCycle(false);
			} else {
				ctTask.setIsSaveSameCycle(true); // 当前时间大于上一次的周期时间，不在同一周期内
			}
			ctTask.setBeforeValidEndDate(endDate.getTime());
		}
	}

	/**
	 * 根据Date和任务类型的计算周期
	 */
	@Override
	public void getTaskCycle(CtTask ctTask, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDayStart(date));

		Calendar beginDate = Calendar.getInstance();
		beginDate.setTime(DateUtil.getDayStart(date));

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(DateUtil.getDayStart(date));
		if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.DAY.getKey().byteValue()) {
			ctTask.setValidFromDate(beginDate.getTime());
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()) {
			int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // 系统当前日期的索引
			if (currentDateIndex <= 0) // 日期索引为0时，代表星期日
				currentDateIndex = 7;
			beginDate.add(Calendar.DATE, -currentDateIndex + 1);
			endDate.add(Calendar.DATE, 7 - currentDateIndex);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			int middleDate = maxDate / 2;
			int currentDate = cal.get(Calendar.DATE);
			int begin = 0;
			int end = 0;
			if (currentDate <= middleDate) {
				begin = 1;
				end = middleDate;
			} else {
				begin = middleDate + 1;
				end = maxDate;
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			beginDate.set(Calendar.DATE, 1);
			endDate.set(Calendar.DATE, maxDate);
		}
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE) + 1);
		endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND) - 1); // 日期减一秒为
																		// 2015-mm-dd:59:59:9
		ctTask.setValidFromDate(beginDate.getTime());
		ctTask.setValidEndDate(endDate.getTime());
	}

	/**
	 * 根据CtTask类型获取周期的开始时间
	 */
	@Override
	public Date getBeginValidity(CtTask ctTask) throws BusinessException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDayStart(cal.getTime()));

		Calendar beginDate = Calendar.getInstance();
		beginDate.setTime(DateUtil.getDayStart(beginDate.getTime()));

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(DateUtil.getDayStart(endDate.getTime()));
		int begin = 0;
		int end = 0;
		if (ctTask.getValidFrom() != null) {
			begin = ctTask.getValidFrom().intValue();
		}
		if (ctTask.getValidEnd() != null) {
			end = ctTask.getValidEnd().intValue();
		}
		if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.DAY.getKey().byteValue()) {
			ctTask.setIsInCycel(true);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()) {
			if (begin < 0) {
				begin = 8 + begin; // 小于0时 -1代表周内最后一天 所以加1
			}
			if (end < 0) {
				end = 8 + end;
			}
			int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // 系统当前日期的索引
			if (currentDateIndex <= 0)
				currentDateIndex = 7;
			if (currentDateIndex >= begin && currentDateIndex <= end) {
				ctTask.setIsInCycel(true);
			} else {
				ctTask.setIsInCycel(false);
			}
			beginDate.add(Calendar.DATE, begin - currentDateIndex);
			endDate.add(Calendar.DATE, end - currentDateIndex);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			int middleDate = maxDate / 2;
			int currentDate = cal.get(Calendar.DATE);
			if (currentDate <= middleDate) {
				if (begin < 0) {
					begin = middleDate + begin + 1;
				}
				if (end < 0) {
					end = middleDate + begin + 1;
				}
				if (currentDate >= begin && currentDate <= end) {
					ctTask.setIsInCycel(true);
				} else {
					ctTask.setIsInCycel(false);
				}
			} else {
				if (begin < 0) {
					begin = maxDate + begin + 1;
				} else {
					begin = middleDate + begin;
				}
				if (end < 0) {
					end = maxDate + begin + 1;
				} else {
					end = middleDate + begin;
				}
				if (currentDate >= begin && currentDate <= end) {
					ctTask.setIsInCycel(true);
				} else {
					ctTask.setIsInCycel(false);
				}
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			int currentDate = cal.get(Calendar.DATE);
			if (begin < 0) {
				begin = maxDate + begin + 1;
			}
			if (end < 0) {
				end = maxDate + end + 1;
			}
			if (currentDate >= begin && currentDate <= end) {
				ctTask.setIsInCycel(true);
			} else {
				ctTask.setIsInCycel(false);
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		}
		return beginDate.getTime();
	}

	@Override
	public Date getTaskCycleMaxDate(Byte cycleType) {
		Calendar max = Calendar.getInstance();
		if (cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()) { // 取到当前周的最大一天
			max.add(Calendar.DATE, 8 - (max.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : max.get(Calendar.DAY_OF_WEEK)));
		} else if (cycleType.byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()) {
			int maxDate = max.getActualMaximum(Calendar.DATE);
			int middleDate = maxDate / 2;
			int currentDate = max.get(Calendar.DATE);
			if (currentDate <= middleDate) {
				max.set(Calendar.DATE, middleDate);
			} else {
				max.set(Calendar.DATE, maxDate);
			}
		} else {
			int maxDate = max.getActualMaximum(Calendar.DATE);
			max.set(Calendar.DATE, maxDate);
		}
		return max.getTime();
	}

	@Override
	public List<?> exportCtTaskData(ExportCtTaskDataSerchVo exportCtTaskDataSerchVo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", exportCtTaskDataSerchVo.getClientId());
		params.put("clientUserId", exportCtTaskDataSerchVo.getClientUserId());
		params.put("userIds", exportCtTaskDataSerchVo.getUserIds());
		params.put("structureIds", exportCtTaskDataSerchVo.getStructureIds());
		params.put("deptIds", exportCtTaskDataSerchVo.getDeptIds());
		params.put("userName", exportCtTaskDataSerchVo.getUserName());
		params.put("ctTaskId", exportCtTaskDataSerchVo.getCtTaskId());
		params.put("visitType", exportCtTaskDataSerchVo.getVisitType());
		params.put("cityId", exportCtTaskDataSerchVo.getCityId());
		params.put("provinceId", exportCtTaskDataSerchVo.getProvinceId());
		params.put("storeName", exportCtTaskDataSerchVo.getStoreName());
		params.put("storeNo", exportCtTaskDataSerchVo.getStoreNo());
		params.put("positionIds", exportCtTaskDataSerchVo.getPositionIds());

		// 默认导出所有的
		Integer ifReport = 0;
		if (null == exportCtTaskDataSerchVo.getReportData() || "".equals(exportCtTaskDataSerchVo.getReportData())) {
			ifReport = 0;
		} else if (exportCtTaskDataSerchVo.getReportData().equals(Constants.IS_REPORT_DATA)) { // 已提交
			ifReport = 1;
		} else if (exportCtTaskDataSerchVo.getReportData().equals(Constants.IS_NOT_REPORT_DATA)) {// 未提报
			ifReport = 2;
		}
		params.put("ifReport", ifReport);
		if (exportCtTaskDataSerchVo.getTaskCycle() != null && exportCtTaskDataSerchVo.getTaskCycle().indexOf("~") > 0) {
			String startTime = StringUtils.isEmpty(exportCtTaskDataSerchVo.getTaskCycle().split("~")[0]) ? "" : exportCtTaskDataSerchVo.getTaskCycle().split("~")[0].trim();
			String endTime = StringUtils.isEmpty(exportCtTaskDataSerchVo.getTaskCycle().split("~")[1]) ? "" : exportCtTaskDataSerchVo.getTaskCycle().split("~")[1].trim();
			exportCtTaskDataSerchVo.setStartDate(DateUtil.formatDate(DateUtil.getDayStart(startTime), DateTimeUtils.yyyyMMddHHmmss));
			if (StringUtils.isNotEmpty(endTime)) {
				exportCtTaskDataSerchVo.setEndDate(DateUtil.formatDate(DateUtil.getDayEnd(endTime), DateTimeUtils.yyyyMMddHHmmss));
			}
		}
		params.put("startTime", DateUtil.getDayStart(exportCtTaskDataSerchVo.getStartDate()));
		params.put("endTime", DateUtil.getDayEnd(exportCtTaskDataSerchVo.getEndDate()));
		return ctTaskDao.exportCtTaskData(params);
	}

	@Override
	public void getValidityDate(StoreTask storeTask, CtTask ctTask) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getDayStart(cal.getTime()));
		Calendar beginDate = Calendar.getInstance();
		beginDate.setTime(DateUtil.getDayStart(beginDate.getTime()));
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(DateUtil.getDayStart(endDate.getTime()));
		int begin = 0;
		int end = 0;
		if (ctTask.getValidFrom() != null) {
			begin = ctTask.getValidFrom().intValue();
		}
		if (ctTask.getValidEnd() != null) {
			end = ctTask.getValidEnd().intValue();
		}
		if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.WEEK.getKey().byteValue()) {
			if (begin < 0) {
				begin = 8 + begin; // 小于0时 -1代表周内最后一天 所以加1
			}
			if (end < 0) {
				end = 8 + end;
			}
			int currentDateIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // 系统当前日期的索引
			if (currentDateIndex <= 0)
				currentDateIndex = 7;
			beginDate.add(Calendar.DATE, begin - currentDateIndex);
			endDate.add(Calendar.DATE, end - currentDateIndex);
			storeTask.setValidFromDate(beginDate.getTime());
			storeTask.setValidEndDate(endDate.getTime());
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.HALFMONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			int middleDate = maxDate / 2;
			int currentDate = cal.get(Calendar.DATE);
			if (currentDate <= middleDate) {
				if (begin < 0) {
					begin = middleDate + begin + 1;
				}
				if (end < 0) {
					end = middleDate + begin + 1;
				}
			} else {
				if (begin < 0) {
					begin = maxDate + begin + 1;
				} else {
					begin = middleDate + begin;
				}
				if (end < 0) {
					end = maxDate + begin + 1;
				} else {
					end = middleDate + begin;
				}
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		} else if (ctTask.getCycleType().byteValue() == ChannelEnum.CYCLE_TYPE.MONTH.getKey().byteValue()) {
			int maxDate = cal.getActualMaximum(Calendar.DATE);
			if (begin < 0) {
				begin = maxDate + begin + 1;
			}
			if (end < 0) {
				end = maxDate + end + 1;
			}
			beginDate.set(Calendar.DATE, begin);
			endDate.set(Calendar.DATE, end);
		}
		endDate.set(Calendar.DATE, endDate.get(Calendar.DATE) + 1);
		endDate.set(Calendar.SECOND, endDate.get(Calendar.SECOND) - 1); // 日期减一秒为
																		// 2015-mm-dd:59:59:9
		Calendar now = new GregorianCalendar();
		if (storeTask.getStartTime() != null) {
			now.setTime(storeTask.getStartTime()); // 数据的填写日期
		}
		if (!beginDate.before(now)) {
			storeTask.setIsInCycel(false);
		}
		if (endDate.before(now)) {
			storeTask.setIsInCycel(false);
		}
		if (storeTask.getIsInCycel() == null) {
			storeTask.setIsInCycel(true); // 同一个周期有效期内
		}
	}

}
