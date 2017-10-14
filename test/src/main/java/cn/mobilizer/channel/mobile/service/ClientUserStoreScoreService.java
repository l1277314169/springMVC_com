package cn.mobilizer.channel.mobile.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.mobile.po.ClientUserStoreScore;

public interface ClientUserStoreScoreService {
	
	/**
	 * 手机端查询-门店打分
	 * @param clientId
	 * @param clientUserIds
	 * @param storeNo
	 * @param scoreType
	 * @param monthId
	 * @return
	 * @throws BusinessException
	 */
	public List<ClientUserStoreScore> getStoreScoreList(Integer clientId, Integer clientUserId, String clientUserIds, String storeNo, Byte scoreType, String monthId)throws BusinessException;
	

}
