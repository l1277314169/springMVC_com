package cn.mobilizer.channel.export.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.export.po.DumpDataSetting;

@Repository
public class ExportDataDao extends MyBatisDao{

	public ExportDataDao(){
		super("REPORT_DATA");
	}
	
	public DumpDataSetting getDumpDataSettingInfo(Map<String, Object> params){
		return super.get("getDumpDataSettingInfo",params);
	}
	
	public List<TreeMap<String, String>> getExportData(String sql){
		Map<String, String> params = new HashMap<String, String>();
		params.put("arg_procedure", sql);
		List<TreeMap<String, String>> list = super.getList("getExportData",params);
		return list;
	}
	
}
