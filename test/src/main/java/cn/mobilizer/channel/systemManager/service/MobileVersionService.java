package cn.mobilizer.channel.systemManager.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.MobileVersion;

public interface MobileVersionService {
	public MobileVersion findNewMobileVersion(Integer clientId, Integer ClientUserId, String version, Integer appCode);
	/**
	 *获取总记录数
	 * @return
	 */
    public Integer queryMobileVersionCount(Map<String, Object> searchParams)throws BusinessException;
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<MobileVersion> queryMobileVersionList(Map<String, Object> searchParams) throws BusinessException;
    /**
	 * 增加
	 * @param MobileVersion
	 * @return
	 */
	public int addMobileVersion(MobileVersion mobileVersion);
	/**
	 * 修改
	 * @param MobileVersion
	 * @return
	 */
	public int updateMobileVersion(MobileVersion mobileVersion);
	/**
	 * 删除
	 * @param mobileVersionId
	 * @return
	 */
	public int deleteMobileVersion(Integer mobileVersionId);
	/**
	 * 获取手机版本对象
	 * @param mobileVersionId
	 * @return
	 */
	public MobileVersion findByPrimaryKey(Integer mobileVersionId);
	/**
	 * 
	 * @param searchParams
	 * @return
	 */
	public String findByParams(Integer clientId, Boolean isEnable, Byte appCode) throws BusinessException;
}
