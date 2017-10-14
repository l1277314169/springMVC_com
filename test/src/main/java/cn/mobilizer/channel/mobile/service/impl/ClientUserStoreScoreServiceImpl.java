package cn.mobilizer.channel.mobile.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.mobile.dao.ClientUserStoreScoreDao;
import cn.mobilizer.channel.mobile.po.ClientUserStoreScore;
import cn.mobilizer.channel.mobile.service.ClientUserStoreScoreService;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class ClientUserStoreScoreServiceImpl implements ClientUserStoreScoreService {
	private static final Log LOG = LogFactory.getLog(ClientUserStoreScoreServiceImpl.class);
	
	@Autowired
	private ClientUserStoreScoreDao clientUserStoreScoreDao;
	
	@Override
	public List<ClientUserStoreScore> getStoreScoreList(Integer clientId, Integer clientUserId, String clientUserIds,String storeNo,Byte scoreType,String monthId) throws BusinessException{
		List<ClientUserStoreScore> ls = null;
		try {
			if (StringUtil.isEmptyString(monthId)) {
				monthId = DateUtil.thisMonth(new Date(),null);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("clientId", clientId);
			param.put("clientUserId", clientUserId);
			param.put("clientUserIds", clientUserIds);
			param.put("storeNo", storeNo);
			param.put("scoreType", scoreType);
			param.put("monthId", monthId);
			param.put("flag", ReportGlobal.ReportFlag.EXPORT);
			ls = clientUserStoreScoreDao.getStoreScoreList(param);
		} catch (BusinessException e) {
			LOG.error("method getStoreScoreList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

}
