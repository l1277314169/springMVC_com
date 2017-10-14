package cn.mobilizer.channel.export.vo;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import cn.mobilizer.channel.comm.utils.POIExcelSupport;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.report.po.DataVo;
import cn.mobilizer.channel.report.vo.MapKeyComparator;
import cn.mobilizer.channel.report.vo.ReportGlobal;
import cn.mobilizer.channel.util.PropertiesHelper;

public class DateVoSupport {

	public static List<TreeMap<String, DataVo>> sort(List<TreeMap<String, String>> result,DataInfo data){
		List<TreeMap<String, DataVo>> resultPart = new ArrayList<TreeMap<String,DataVo>>();
		if(null!=result){
			for (TreeMap<String, String> map : result) {
				TreeMap<String, DataVo> partMap = new TreeMap<String, DataVo>(new MapKeyComparator());
				for (String k : data.getHeads()) {
					int index = data.getColIndex(k);
					Object val = map.get(k);
					val = val==null?"":val;
					String _key = index+"@"+k;
					
					DataVo dataVo = new DataVo();
					dataVo.setKey(_key);
					dataVo.setValue(val);
					dataVo.setIndex(index);
					
					partMap.put(_key, dataVo);
				}
				resultPart.add(partMap);
			}
		}
		return resultPart;
	}
	
	
	public static List<TreeMap<String, DataVo>> sort2(List<TreeMap<String, String>> result,DataInfo data){
		List<TreeMap<String, DataVo>> resultPart = new ArrayList<TreeMap<String,DataVo>>();
		String url = PropertiesHelper.getInstance().getProperty("channelPlus.domain");
		if(null!=result){
			for (TreeMap<String, String> map : result) {
				TreeMap<String, DataVo> partMap = new TreeMap<String, DataVo>(new MapKeyComparator());
				for (String k : data.getHeads()) {
					int index = data.getColIndex(k);
					Object val = map.get(k);
					val = val==null?"":val;
					String _key = index+"@"+k;
					
					DataVo dataVo = new DataVo();
					dataVo.setKey(_key);
					if(val.toString().indexOf(".jpg")!=-1){
						String[] temp = val.toString().split(",");
						String[] newTemp = new String[temp.length];
						for (int i = 0; i < temp.length; i++) {
							newTemp[i]= url+"/uploadfiles/15/web/"+temp[i];
						}
						val = org.springframework.util.StringUtils.arrayToDelimitedString(newTemp, ",");
					}
					dataVo.setValue(val);
					dataVo.setIndex(index);
					
					partMap.put(_key, dataVo);
				}
				resultPart.add(partMap);
			}
		}
		return resultPart;
	}
	
	public static List<TreeMap<String, DataVo>> sort3(List<TreeMap<String, String>> result,DataInfo data){
		List<TreeMap<String, DataVo>> resultPart = new ArrayList<TreeMap<String,DataVo>>();
		if(null!=result){
			for (TreeMap<String, String> map : result) {
				TreeMap<String, DataVo> partMap = new TreeMap<String, DataVo>(new MapKeyComparator());
				for (String k : data.getHeads()) {
					int index = data.getColIndex(k);
					Object val = map.get(k);
					val = val==null?"":val;
					String _key = index+"@"+k;
					
					DataVo dataVo = new DataVo();
					dataVo.setKey(_key);
					dataVo.setValue(val);
					dataVo.setIndex(index);
					
					partMap.put(_key, dataVo);
				}
				resultPart.add(partMap);
			}
		}
		return resultPart;
	}
	
	public static List<String> getHSHead(){
		List<String> heads = new ArrayList<String>();
		heads.add("store_no");
		heads.add("city");
		heads.add("province");
		heads.add("store_name");
		heads.add("channel_name");
		heads.add("chain_name");
		heads.add("sign_date");
		heads.add("start_date");
		heads.add("end_date");
		
		heads.add("other_201508");
		heads.add("other_201509");
		heads.add("other_201510");
		heads.add("other_201511");
		heads.add("other_201512");
		heads.add("other_201601");
		heads.add("other_201602");
		heads.add("other_201603");
		heads.add("other_201604");
		heads.add("other_201605");
		heads.add("other_201606");
		heads.add("other_201607");
		
		heads.add("gift_201508");
		heads.add("gift_201509");
		heads.add("gift_201510");
		heads.add("gift_201511");
		heads.add("gift_201512");
		heads.add("gift_201601");
		heads.add("gift_201602");
		heads.add("gift_201603");
		heads.add("gift_201604");
		heads.add("gift_201605");
		heads.add("gift_201606");
		heads.add("gift_201607");
		
		
		heads.add("jhje_201508");
		heads.add("jhje_201509");
		heads.add("jhje_201510");
		heads.add("jhje_201511");
		heads.add("jhje_201512");
		heads.add("jhje_201601");
		heads.add("jhje_201602");
		heads.add("jhje_201603");
		heads.add("jhje_201604");
		heads.add("jhje_201605");
		heads.add("jhje_201606");
		heads.add("jhje_201607");
		
		heads.add("pos_201508");
		heads.add("pos_201509");
		heads.add("pos_201510");
		heads.add("pos_201511");
		heads.add("pos_201512");
		heads.add("pos_201601");
		heads.add("pos_201602");
		heads.add("pos_201603");
		heads.add("pos_201604");
		heads.add("pos_201605");
		heads.add("pos_201606");
		heads.add("pos_201607");
		
		heads.add("gift");
		heads.add("gift_price");
		heads.add("other_gift");
		heads.add("other_price");
		heads.add("options_of_invest");
		heads.add("val_of_shelf");
		heads.add("other_invest");
		heads.add("has_invoice");
		heads.add("invoice_type");
		heads.add("other_invoice");
		heads.add("license_type");
		heads.add("acct_type");
		heads.add("bank_name");
		heads.add("bank_acct");
		heads.add("acct_holder");
		
		heads.add("month_id");
		heads.add("num_of_gift");
		heads.add("num_of_member");
		heads.add("rate_of_review");
		heads.add("num1_of_unqualified");
		heads.add("num2_of_unqualified");
		heads.add("num_of_verification");
		heads.add("amount");
		heads.add("status");
		heads.add("status2");
		heads.add("status3");
		
		return heads;
	}
	
	
	public static List<String> getHSHTHead(){
		List<String> heads = new ArrayList<String>();
		heads.add("store_no");
		heads.add("city");
		heads.add("province");
		heads.add("store_name");
		//heads.add("channel_name");
		heads.add("chain_name");
		heads.add("sign_date");
		heads.add("start_date");
		heads.add("end_date");
		
		heads.add("other_201508");
		heads.add("other_201509");
		heads.add("other_201510");
		heads.add("other_201511");
		heads.add("other_201512");
		heads.add("other_201601");
		heads.add("other_201602");
		heads.add("other_201603");
		heads.add("other_201604");
		heads.add("other_201605");
		heads.add("other_201606");
		heads.add("other_201607");
		
		heads.add("gift_201508");
		heads.add("gift_201509");
		heads.add("gift_201510");
		heads.add("gift_201511");
		heads.add("gift_201512");
		heads.add("gift_201601");
		heads.add("gift_201602");
		heads.add("gift_201603");
		heads.add("gift_201604");
		heads.add("gift_201605");
		heads.add("gift_201606");
		heads.add("gift_201607");
		
		
		heads.add("jhje_201508");
		heads.add("jhje_201509");
		heads.add("jhje_201510");
		heads.add("jhje_201511");
		heads.add("jhje_201512");
		heads.add("jhje_201601");
		heads.add("jhje_201602");
		heads.add("jhje_201603");
		heads.add("jhje_201604");
		heads.add("jhje_201605");
		heads.add("jhje_201606");
		heads.add("jhje_201607");
		
		heads.add("pos_201508");
		heads.add("pos_201509");
		heads.add("pos_201510");
		heads.add("pos_201511");
		heads.add("pos_201512");
		heads.add("pos_201601");
		heads.add("pos_201602");
		heads.add("pos_201603");
		heads.add("pos_201604");
		heads.add("pos_201605");
		heads.add("pos_201606");
		heads.add("pos_201607");
		
		heads.add("gift");
		heads.add("gift_price");
		heads.add("other_gift");
		heads.add("other_price");
		heads.add("options_of_invest");
		heads.add("val_of_shelf");
		heads.add("other_invest");
		heads.add("has_invoice");
		heads.add("invoice_type");
		heads.add("other_invoice");
		heads.add("license_type");
		heads.add("acct_type");
		heads.add("bank_name");
		heads.add("bank_acct");
		heads.add("acct_holder");
		
		return heads;
	}
	
	public static List<String> getHSHXHead()
	{
		List<String> heads = new ArrayList<String>();
		heads.add("store_no");
		heads.add("province");
		heads.add("city");
		heads.add("store_name");
		heads.add("chain_name");
		heads.add("sign_date");
		heads.add("start_date");
		heads.add("end_date");
		heads.add("has_invoice");
		heads.add("invoice_type");
		heads.add("other_invoice");
		heads.add("license_type");
		heads.add("acct_type");
		heads.add("bank_name");
		heads.add("bank_acct");
		heads.add("acct_holder");
		heads.add("month_id");
		heads.add("num_of_gift");
		heads.add("num_of_member");
		heads.add("rate_of_review");
		heads.add("num1_of_unqualified");
		heads.add("num2_of_unqualified");
		heads.add("num_of_verification");
		heads.add("amount");
		heads.add("status");
		
		return heads;
	}
	public static String getHeadVals(List<TreeMap<String, String>> lists){
		TreeMap<String, String> map = lists.get(0);
		Set<String> keys = map.keySet();
		String val = null;
		for (String key : keys) {
			val = map.get(key);
		}
		val = val.replaceAll("`", "");
		return val;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<TreeMap<String, String>> getValues(List<?> list,boolean flag){
		List<TreeMap<String, String>> valList = null;
		if(!list.isEmpty()){
			if(flag){
				valList = (List<TreeMap<String, String>>) list.get(ReportGlobal.Procedure.VALUE);
			}else{
				valList = (List<TreeMap<String, String>>) list;
			}
		}
		return valList;
	}
	
	
	@SuppressWarnings("unchecked")
	public static int getItems(List<?> list){
		List<TreeMap<String, String>> object =  (List<TreeMap<String, String>>)list.get(ReportGlobal.Procedure.ITEMS);
		int items = 0;
		TreeMap<String, String> map = object.get(0);
		Set<String> keys = map.keySet();
		for (String key : keys) {
			Object obj = map.get(key);
			items = Integer.parseInt(obj+"");
		}
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public static List<DataInfo> getDataInfoList(List<TreeMap<String, String>> lists){
		List<DataInfo> dataList = new ArrayList<DataInfo>();
		if(!lists.isEmpty()){
			for (int i = 0; i < lists.size();i+=2) {
				DataInfo data = new DataInfo();
				List<TreeMap<String, String>> head = (List<TreeMap<String, String>>) lists.get(i);
				String headVals = DateVoSupport.getHeadVals(head);
				String[] arr = headVals.split("@");
				String sheetName = arr[0];
				String[] arrHeads = arr[1].split(",");
				List<String> heads = Arrays.asList(arrHeads);
				data.setHeads(heads);
				data.setSheetName(sheetName);
				
				List<TreeMap<String, String>> values = (List<TreeMap<String, String>>) lists.get(i+1);
				data.setValues(DateVoSupport.sort2(values, data));
				dataList.add(data);
			}
		}
		return dataList;
	}
	
	public static void writer(List<DataInfo> vos,OutputStream out) throws Exception{
		POIExcelSupport poi = new POIExcelSupport();
		for (DataInfo vo : vos) {
			List<TreeMap<String, DataVo>> values = vo.getValues();
	        List<String> heards = vo.getHeads();
			String fileName = vo.getSheetName();
			poi.export(fileName,heards,values);
		}
		poi.workFlush(out);
	}
	
	
}
