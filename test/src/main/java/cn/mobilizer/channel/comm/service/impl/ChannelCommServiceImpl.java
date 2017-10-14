package cn.mobilizer.channel.comm.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientStructureDao;
import cn.mobilizer.channel.base.dao.ClientUserRelationDao;
import cn.mobilizer.channel.comm.init.InitBusinessDefinitionBean;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.MonthVo;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;

@Service
public class ChannelCommServiceImpl implements ChannelCommService {
	
	private static final Log LOG = LogFactory.getLog(ChannelCommServiceImpl.class);
	
	@Autowired
	private ClientStructureDao clientStructureDao;
	@Autowired
	private ClientUserRelationDao clientUserRelationDao;
	
	@Override
	public String getSubStructrueIds(Integer id) {
		return clientStructureDao.getSubStructure(id);
	}
	
	@Override
	public String getSubStructrueIds(String id) {
		return clientStructureDao.getSubStructure(id);
	}
	
	@Override
	public String getSubordinates(String clientUserIds){
		return clientUserRelationDao.getSubordinates(clientUserIds);
	}

	@Override
	public String getSubordinatesWithOutSelf(String clientUserId) {
		return clientUserRelationDao.getSubordinatesWithOutSelf(clientUserId);
		
	}

	@Override
	public String getParentStructrueIds(Integer id){
		return clientStructureDao.getParentStructure(id);
	}

	@Override
	public List<ClientBusinessDefinition> getModBusinessDefinition(String tablename,Integer pageTpye, Integer clientId) throws BusinessException{
		List<ClientBusinessDefinition> ls = new ArrayList<ClientBusinessDefinition>();
		Map<String,Map<String,List<ClientBusinessDefinition>>> definitionMap = InitBusinessDefinitionBean.DEFINITION_MAP;
		
		if(definitionMap.containsKey(clientId.toString())){
			Map<String,List<ClientBusinessDefinition>> map_1 = definitionMap.get(clientId.toString());
			if(map_1.containsKey(tablename)){
				ls.addAll(map_1.get(tablename));
			}
		}
		if(ls == null || ls.size() == 0){
			return null;
		}
		
		if(pageTpye.equals(ChannelEnum.PAGE_TPYE.QUERY.getKey())){
			Iterator<ClientBusinessDefinition> it = ls.iterator();
			while (it.hasNext()) {
				ClientBusinessDefinition clientBusinessDefinition = (ClientBusinessDefinition) it.next();
				clientBusinessDefinition.setAttributeName(StringUtil.toJavaAttributeName(clientBusinessDefinition.getColumnName()));
				if (clientBusinessDefinition.getQuerySequence() == null){
					it.remove();
				}
			}
			Collections.sort(ls, new Comparator<ClientBusinessDefinition>(){
				public int compare(ClientBusinessDefinition o1, ClientBusinessDefinition o2){
					if(o1.getQuerySequence() > o2.getQuerySequence()){
						return 1;
					} else if (o1.getQuerySequence() == o2.getQuerySequence()){
						return 0;
					} else {
						return -1;
					}
				}
			});
		} else if(pageTpye.equals(ChannelEnum.PAGE_TPYE.LIST.getKey())){
			Iterator<ClientBusinessDefinition> it = ls.iterator();
			while (it.hasNext()) {
				ClientBusinessDefinition clientBusinessDefinition = (ClientBusinessDefinition) it.next();
				clientBusinessDefinition.setAttributeName(StringUtil.toJavaAttributeName(clientBusinessDefinition.getColumnName()));
				if (clientBusinessDefinition.getListSequence() == null){
					it.remove();
				}
			}
			Collections.sort(ls, new Comparator<ClientBusinessDefinition>(){
				public int compare(ClientBusinessDefinition o1, ClientBusinessDefinition o2){
					if(o1.getListSequence() > o2.getListSequence()){
						return 1;
					} else if (o1.getListSequence() == o2.getListSequence()){
						return 0;
					} else {
						return -1;
					}
				}
			});
		}else if(pageTpye.equals(ChannelEnum.PAGE_TPYE.ADD.getKey()) || pageTpye.equals(ChannelEnum.PAGE_TPYE.EDIT.getKey()) || pageTpye.equals(ChannelEnum.PAGE_TPYE.SEE.getKey())){
			Iterator<ClientBusinessDefinition> it = ls.iterator();
			while (it.hasNext()) {
				ClientBusinessDefinition clientBusinessDefinition = (ClientBusinessDefinition) it.next();
				clientBusinessDefinition.setAttributeName(StringUtil.toJavaAttributeName(clientBusinessDefinition.getColumnName()));
				if (clientBusinessDefinition.getEditSequence() == null){
					it.remove();
				}
			}
			Collections.sort(ls, new Comparator<ClientBusinessDefinition>(){
				public int compare(ClientBusinessDefinition o1, ClientBusinessDefinition o2){
					if(o1.getEditSequence() > o2.getEditSequence()){
						return 1;
					} else if (o1.getEditSequence() == o2.getEditSequence()){
						return 0;
					} else {
						return -1;
					}
				}
			});
		} else if(pageTpye.equals(ChannelEnum.PAGE_TPYE.IMPORT.getKey())){
			Iterator<ClientBusinessDefinition> it = ls.iterator();
			while (it.hasNext()) {
				ClientBusinessDefinition clientBusinessDefinition = (ClientBusinessDefinition) it.next();
				clientBusinessDefinition.setAttributeName(StringUtil.toJavaAttributeName(clientBusinessDefinition.getColumnName()));
				if (clientBusinessDefinition.getImportSequence() == null){
					it.remove();
				}
			}
			Collections.sort(ls, new Comparator<ClientBusinessDefinition>(){
				public int compare(ClientBusinessDefinition o1, ClientBusinessDefinition o2){
					if(o1.getImportSequence() > o2.getImportSequence()){
						return 1;
					} else if (o1.getImportSequence() == o2.getImportSequence()){
						return 0;
					} else {
						return -1;
					}
				}
			});
		} 
		return ls;
	}

	@Override
	public String getPoraControlValue(Integer paraControlType,String paraControlName, String paraValue,Map<String,Map<String,Map<String,String>>> optionMap, Integer clientId) {
		String str = "";
		if(paraControlType == null) {
			return paraValue;
		}
		switch (paraControlType) {
			case 5://RADIO ("单选",5)
				if(clientId !=null && paraControlName !=null && paraValue != null){
					str = (optionMap.get(clientId.toString()) !=null && optionMap.get(clientId.toString()).get(paraControlName) != null)? optionMap.get(clientId.toString()).get(paraControlName).get(paraValue):"";
				}
				break;
			case 6://CHECK_BOX ("多选",6),
				if(clientId !=null && paraControlName !=null && paraValue != null){
					String str_tmp = "";
					String[] value = paraValue.split(",");
					for ( int i = 0 ; i < value.length ; i++ ) {
						str_tmp = (optionMap.get(clientId.toString()) !=null && optionMap.get(clientId.toString()).get(paraControlName) != null)? optionMap.get(clientId.toString()).get(paraControlName).get(value[i]):"";
						if(StringUtil.isNotEmptyString(str_tmp)){
							str += str_tmp+",";
						}
					}
				}
				break;
			case 7://SELECT ("勾选",7),
				if(paraValue.equals("2")){
					str = "否";
				} else if(paraValue.equals("1")){
					str = "是";
				} else {
					
				}
				break;
			case 16://SELECT ("勾选(两种状态)",16),
				if(paraValue.equals("0")){
					str = "否";
				} else if(paraValue.equals("1")){
					str = "是";
				} else {

				}
				break;
			default:
				str = paraValue;
				break;
		}
		return str;
	}

	@Override
	public List<MonthVo> initializeMonthControl() throws BusinessException{
		List<MonthVo> months = new ArrayList<MonthVo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
	    Calendar c_begin = new GregorianCalendar();
	    Calendar c_now = new GregorianCalendar();	
	    c_begin.set(2015, 0, 5); 		//Calendar的月从0-11，所以4月是3.
	    Calendar curr = c_begin;
	    String str = "";
	    while (curr.before(c_now)) {
	    	// 月初
	        Calendar calendar = Calendar.getInstance();		        
	        calendar.setTime(curr.getTime());
	        int index = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar.add(Calendar.DATE, (1 - index));		       
	        String month = sdf.format(calendar.getTime()); 
	        str+=month+",";
	        curr.add(Calendar.MONTH,1);	        
	    }
		for(String month : str.split(",")){
			MonthVo monthVo = new MonthVo();
			monthVo.setText(month); 
			monthVo.setValue(month);
			months.add(monthVo);		        
	    }
		Collections.reverse(months);				//日期顺序反转
		return months;
	}

	@Override
	public String getDistributorStructrueIds(Integer id){
		String str = "" ;
		/**获取所有下一级包括直接**/
		String str_d =  clientStructureDao.getSubStructure(id);
		
		String str_u =  clientStructureDao.getParentStructureById(id);
		if(StringUtil.isNotEmptyString(str_u)){
			str =  str_u +"," +str_d;
		} else {
			str = str_d;
		}
		return str;
	}

	@Override
	public String findUserIdsByParentId(Integer clientUserId) {
		return clientUserRelationDao.findUserIdsByParentId(clientUserId);
	}
	
	
}
