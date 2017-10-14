package cn.mobilizer.channel.export.service;

import java.util.List;

import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.export.po.DumpDataSetting;

public interface ExportDataService {

	/**
	 * 获取存储过程配置信息
	 * @param params
	 * @return
	 */
	public DumpDataSetting getDumpDataSettingInfo(int clientId,int settingId);
	
	/**
	 * 获取存储过程数据
	 */
	public List<DataInfo> getExportData(String sql);
	
	/**
	 * 获取数据
	 * @param sql
	 * @return
	 */
	public DataInfo getDataInfo(String sql,DumpDataSetting dds,boolean flag);
	
}
