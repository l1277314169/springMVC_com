package cn.mobilizer.channel.colgate.dao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.colgate.po.DownloadDataList;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class DownloadDataListDao extends MyBatisDao{

	public DownloadDataListDao(){
		super("DOWNLOADDATALIST");
	}
	
	public List<TreeMap<String, String>> getSurveyMainQuestionnaire(Map<String, Object> params){
		List<TreeMap<String, String>> list = super.getList("getSurveyMainQuestionnaire",params);
		return list;
	}
	
	public List<TreeMap<String, String>> getSurveySecondaryDisplayQuestionnaire(Map<String, Object> params){
		List<TreeMap<String, String>> list = super.getList("getSurveySecondaryDisplayQuestionnaire",params);
		return list;
	}
	
	
	public List<TreeMap<String, String>> getSupplementaryQuestionnaire(Map<String, Object> params){
		List<TreeMap<String, String>> list = super.getList("getSupplementaryQuestionnaire",params);
		return list;
	}
	
	public DownloadDataList getDownloadListInfo(Map<String, Object> params){
		DownloadDataList dataList = super.get("getDownloadListInfo",params);
		return dataList;
	}
	
	public void insert(DownloadDataList downloadDataList){
		super.insert("insert", downloadDataList);
	}
	
	public void updateLastUpdateTime(DownloadDataList downloadDataList){
		super.update("updateLastUpdateTime", downloadDataList);
	}
	
	public List<DownloadDataList> loadRawDataList(Map<String, Object> params){
		return super.queryForList("loadRawDataList",params);
	}

	public List<TreeMap<String, String>> getTrend(Map<String, Object> params) {
		List<TreeMap<String, String>> list = super.getList("getTrend",params);
		return list;
	}
	
}
