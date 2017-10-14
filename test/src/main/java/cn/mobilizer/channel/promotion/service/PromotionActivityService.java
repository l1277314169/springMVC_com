package cn.mobilizer.channel.promotion.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.promotion.po.PromotionActivity;


public interface PromotionActivityService extends BasicService<PromotionActivity>{
	
	/**
	 * 
	 * @param loginName
	 * @param clientName
	 * @return
	 */
	public PromotionActivity findByLoginNameAndClientName(String loginName, String clientName);
	
	/**
	 * 
	 * @param loginName
	 * @return
	 */
	public PromotionActivity findByLoginName(String loginName, int clientId);
	
	/**
	 * 
	 * @param loginName
	 * @param loginPWD
	 * @return
	 */
	public PromotionActivity findByLoginNameAndLoginPWD(String loginName, String loginPWD);
	
	public PromotionActivity findByPrimaryKey(Integer clientUserid);
	public int addPromotionActivity(PromotionActivity promotionActivity);
	public int update(PromotionActivity promotionActivity);
	public int deletePromotionActivity(Integer clientUserId);
	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public Integer queryPromotionActivityCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<PromotionActivity> queryPromotionActivityList(Map<String, Object> searchParams) throws BusinessException;
	
	public boolean saveAll(List<PromotionActivity> users);
	
	
	
}
