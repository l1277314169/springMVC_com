package cn.mobilizer.channel.systemManager.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.MobileVersionDetail;

@Repository
public class MobileVersionDetailDao extends MyBatisDao{
	public MobileVersionDetailDao(){
		super("MOBILE_VERSION_DETAIL");
		
	}
	
	public int insert(MobileVersionDetail mobileVersionDetail){
		return super.insert("insertSelective", mobileVersionDetail);
	}
	
	public int update(MobileVersionDetail mobileVersionDetail){
		return super.update("updateByPrimaryKeySelective", mobileVersionDetail);
	}
	
	public MobileVersionDetail queryMobileVersionDetailList(Integer mobileVersionId){
		return super.get("queryMobileVersionDetailList", mobileVersionId);
	}
	
	public int updateMobileVersionDetailIsdelete(Integer MobileVersionId){
		return super.update("updateMobileVersionDetailIsdelete", MobileVersionId);
	}
	
	public MobileVersionDetail findMobileVersionDetail(Map<String,Object> params){
		return super.get("findMobileVersionDetail", params);
	}
	
	public int updateMobileVersionDetailIsdelete_1(MobileVersionDetail mobileVersionDetail){
		
		return super.update("updateByPrimaryKeySelective", mobileVersionDetail);
	}
	
	public List<MobileVersionDetail> findMobileVersionDetailForClientUser(Integer mobileVersionId, Integer clientUserId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobileVersionId", mobileVersionId);
		params.put("clientUserId", clientUserId);
		return super.getList("findMobileVersionDetailForClientUser", params);
	}
}
