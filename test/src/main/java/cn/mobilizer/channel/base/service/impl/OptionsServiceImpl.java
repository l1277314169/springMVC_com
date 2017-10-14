package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.OptionsDao;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

/**
 * 查找Option表的数据。optionName目前是在代码中写死的，目前维护在每个表的po中
 * @author yeeda.tian
 *
 */
@Service
public class OptionsServiceImpl implements OptionsService {
	
	private static final Log LOG = LogFactory.getLog(OptionsServiceImpl.class);
	
	@Autowired
	private OptionsDao optionsDao;
	
	@Override
	public List<Options> findOptionsByOptionName(String optionName, Integer clientId) throws BusinessException{
		List<Options> list = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientId);
			params.put("optionName", optionName);
			params.put("isDelete", Constants.IS_DELETE_FALSE);
			list = optionsDao.findOptionsByOptionName(params);
		} catch (BusinessException e) {
			LOG.error("method findOptionsByOptionName error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<Options> queryOptions(Map<String, Object> params) throws BusinessException {
		
		return optionsDao.findOptionsByOptionName(params);
	}

	@Override
	public String getOptionText(String optionName, Byte optionValue, int clientId)
			throws BusinessException {
		String workType = null;
		Map<String, Object> parames = new HashMap<String, Object>();
		try {
			parames.put("string", optionName);
			parames.put("optionName", optionValue);
			parames.put("clientId", clientId);
			workType = optionsDao.getOptionText(parames);
		} catch (Exception e) {
			LOG.error("method getOptionText error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return workType;
	}

	@Override
	public String getLeaveTypeText(String string, int leaveType, int clientId)throws BusinessException {
		String leaveTypeText;
		Map<String, Object> parames = new HashMap<String, Object>();
		try {
			parames.put("string", string);
			parames.put("optionName", leaveType);
			parames.put("clientId", clientId);
			leaveTypeText = optionsDao.getOptionText(parames);
		} catch (BusinessException e) {
			LOG.error("method getOptionText error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return leaveTypeText;
	}

	@Override
	public Byte getOptionValue(String optionName, String optionText,
			int clientId) throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("optionName", optionName);
		parames.put("optionText", optionText);
		parames.put("clientId", clientId);
		return optionsDao.getOptionValue(parames);
	}

	@Override
	public List<Options> selectOptionsList(Map<String, Object> paramrter)
			throws BusinessException {
		
		return optionsDao.selectOptionsList(paramrter);
	}

	@Override
	public Map<String, Options> selectOptionsListbyYue(Integer clientId) throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("optionName", "store_type");
		parames.put("clientId", clientId);
		return optionsDao.findOptionValueByKey(parames);
	}

	@Override
	public Map<String, Options> selectOptionsListbyType( Integer clientId) throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("optionName", "display_type_4_colgate");
		parames.put("clientId", clientId);
		return optionsDao.findOptionValueByKey(parames);
	}
}
