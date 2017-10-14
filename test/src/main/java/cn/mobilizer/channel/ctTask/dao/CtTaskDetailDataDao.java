package cn.mobilizer.channel.ctTask.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.ctTask.po.CtTaskData;
import cn.mobilizer.channel.ctTask.po.CtTaskDetailData;

@Repository
public class CtTaskDetailDataDao extends MyBatisDao{
	public CtTaskDetailDataDao() {
		super("CT_TASK_DETAIL_DATA");
	}
	
	public List<CtTaskDetailData> selectByParams(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}

	public Integer selectByParamCount(Map<String, Object> parames){
		return super.get("selectByParamCount", parames);
	}
	
	public String insert(CtTaskDetailData ctTaskDetailData){
		super.insert ("insert", ctTaskDetailData);
		return ctTaskDetailData.getCtTaskDetailDataId();
	} 	
	
	public String insertColgateCtTaskDetailData(CtTaskDetailData ctTaskDetailData){
		super.insert ("insertColgateCtTaskDetailData", ctTaskDetailData);
		return ctTaskDetailData.getCtTaskDetailDataId();
	}
	
	public String insertSelective(CtTaskDetailData ctTaskDetailData){
		super.insert ("insertSelective", ctTaskDetailData);
		return ctTaskDetailData.getCtTaskDetailDataId();
	}
	
	public CtTaskDetailData getDetailDataByTarget1AndParamId(Map<String, Object> parames){
		return super.get("getDetailDataByTarget1AndParamid", parames);
	}
	
	public int update(CtTaskDetailData ctTaskDetailData){
		return super.update("updateByPrimaryKeySelective", ctTaskDetailData); 
	}
	
	public void batchCtTaskDetailDatas(List<CtTaskDetailData> ctTaskDetailDatas){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ctTaskDetailDatas", ctTaskDetailDatas);
		super.insert("batchSaveCtTaskDetailData", params);
	}
	
	public List<CtTaskDetailData> findDetailDatasByTarget1AndParamid(Map<String, Object> parames){
		return super.queryForList("findDetailDatasByTarget1AndParamid", parames);
	}
	
}