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
import cn.mobilizer.channel.visit.po.WorkTimeSettings;

/**
 * @author Nany
 * 2015年1月16日上午10:16:30
 */
public interface WorkTimeSettingsService extends BasicService<WorkTimeSettings> {

	int queryWorKTimeSettingsCount(Map<String, Object> parameters)throws BusinessException;

	List<WorkTimeSettings> queryWorkTimeSettingsList(Map<String, Object> parameters)throws BusinessException;

	WorkTimeSettings getWorkTimeSettings(String settingId)throws BusinessException;

	void updateWorkTimesSetting(WorkTimeSettings workTimeSettings)throws BusinessException;
	
	public ImportVO addWorkTimeSettings(List<WorkTimeSettings> parmarter)throws BusinessException;
	
	/**
	 * 排班导入
	 * @param file
	 * @param request
	 * @throws BusinessException
	 */
	public ResultMessage addImprtWorkTimeSettings(MultipartFile file, HttpServletRequest request,Integer clientId, HttpServletResponse resp)throws BusinessException;
	



}
