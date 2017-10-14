package cn.mobilizer.channel.visit.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.visit.po.Attendance;

public interface AttendanceService extends BasicService<Attendance>{
	public int insert(Attendance attendance);
	public Attendance doAttendance(Integer clientId, Integer clientUserId, Integer type, String longtitude, String latitude, String pics);
	public Attendance getAttendance(Integer clientId, Integer clientUserId);
	public Integer queryCount(Map<String, Object> searchParams) throws BusinessException;
	public boolean attendanceRemark(Integer clientId, Integer clientUserId, Integer attendanceId, String remark);
	public List<Attendance> getAttendanceList(Map<String, Object> parameters);
	public List<Attendance> exportAttendance(Map<String, Object> paras)throws BusinessException;
	/**
	 * 考勤导入新增
	 * @param parmarter
	 * @return
	 * @throws BusinessException
	 */
	public ImportVO addAttendanceByList(List<Attendance> parmarter)throws BusinessException;
	
	/**
	 * 考勤导入
	 * @throws BusinessException
	 */
	public ResultMessage addImportAttendance(MultipartFile file, HttpServletRequest request,Integer clientId, HttpServletResponse resp)throws BusinessException;
	
	/**
	 * unicharm 记录数
	 * @param searchParams
	 * @return
	 */
	public Integer unicharmCount(Map<String, Object> searchParams);
	
	/**
	 * unicharm 列表
	 * @param searchParams
	 * @return
	 */
	public List<Attendance> unicharmQueryList(Map<String, Object> searchParams);
		
	
}
