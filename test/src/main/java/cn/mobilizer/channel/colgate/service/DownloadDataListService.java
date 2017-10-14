package cn.mobilizer.channel.colgate.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.colgate.po.DownloadDataList;

public interface DownloadDataListService {

	public void createMainQuestionnaire(Date date) throws Exception;
	
	public void createSecondaryDisplayQuestionnaire(Date date) throws Exception;
	
	public void createSupplementaryQuestionnaire(Date date) throws Exception;
	
	public List<DownloadDataList> loadRawDataList(Map<String, Object> params) throws Exception;

	public String createTrend(Date date) throws Exception;

	public void updateLastUpdateTime(Date date,String fileName)throws Exception;
	
}
