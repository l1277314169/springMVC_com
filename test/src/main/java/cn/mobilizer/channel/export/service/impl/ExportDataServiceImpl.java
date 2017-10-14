package cn.mobilizer.channel.export.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.export.dao.ExportDataDao;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.export.po.DumpDataSetting;
import cn.mobilizer.channel.export.service.ExportDataService;
import cn.mobilizer.channel.export.vo.DateVoSupport;
import cn.mobilizer.channel.report.po.Column;
import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.vo.MapKeyComparator;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.util.Constants;


@Service
public class ExportDataServiceImpl implements ExportDataService {

	@Autowired
	private ExportDataDao exportDataDao;
	
	/**
	 * 目前最多支持40个sheet导出
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DataInfo> getExportData(String sql) {
		List<TreeMap<String, String>> list = exportDataDao.getExportData(sql);
		List<DataInfo> dataList = new ArrayList<DataInfo>();
		if(!list.isEmpty()){
			for (int i = 0; i < list.size();i+=2) {
				DataInfo data = new DataInfo();
				List<TreeMap<String, String>> head = (List<TreeMap<String, String>>) list.get(i);
				String headVals = DateVoSupport.getHeadVals(head);
				String[] arr = headVals.split("@");
				String sheetName = arr[0];
				String[] arrHeads = arr[1].split(",");
				List<String> heads = Arrays.asList(arrHeads);
				data.setHeads(heads);
				data.setSheetName(sheetName);
				
				List<TreeMap<String, String>> values = (List<TreeMap<String, String>>) list.get(i+1);
				data.setValues(DateVoSupport.sort(values, data));
				dataList.add(data);
			}
		}
		return dataList;
	}
	
	public DataInfo getDataInfo(String sql,DumpDataSetting dds,boolean flag){
		List<TreeMap<String, String>> list = exportDataDao.getExportData(sql);
		List<TreeMap<String, String>> valList = DateVoSupport.getValues(list, flag);
		DataInfo dataInfo = new DataInfo();
		if(flag){
			dataInfo.setItems(DateVoSupport.getItems(list));
		}
		dataInfo.setSheetName(dds.getName());
		dataInfo.setHeadsForColumn(dds.getColumnList());
		dataInfo.setValues(sort2(valList,dds));
		return dataInfo;
	}

	public List<TreeMap<String, DataVo>> sort2(List<TreeMap<String, String>> result,DumpDataSetting dds){
		List<TreeMap<String, DataVo>> resultPart = new ArrayList<TreeMap<String,DataVo>>();
		if(null!=result){
			for (TreeMap<String, String> map : result) {//全集,需要从全集中剔除不要显示的列
				//Set<String> keys = map.keySet();
				TreeMap<String, DataVo> partMap = new TreeMap<String, DataVo>(new MapKeyComparator());
				int i = 0;
				for (Column column : dds.getColumnList()) {
					Object val = map.get(column.getColName());
					val = val==null?"":val;
					String _key = i+"@"+column.getColName();
					
					DataVo dataVo = new DataVo();
					dataVo.setKey(_key);
					dataVo.setValue(val);
					dataVo.setIndex(i);
					partMap.put(_key+"",dataVo);
					i++;
				}
				resultPart.add(partMap);
			}
		}
		return resultPart;
	}
	

	@Override
	public DumpDataSetting getDumpDataSettingInfo(int clientId,int settingId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		params.put("settingId", settingId);
		return exportDataDao.getDumpDataSettingInfo(params);
	}
}
