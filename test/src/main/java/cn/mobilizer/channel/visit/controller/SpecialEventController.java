package cn.mobilizer.channel.visit.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.ClientUserRelation;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.vo.SpecialVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum.VISIT_POP_TYPE;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.systemManager.service.ClientBusinessDefinitionService;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.visit.po.CallPlanning;
import cn.mobilizer.channel.visit.po.SpecialEvent;
import cn.mobilizer.channel.visit.service.CallPlanningService;
import cn.mobilizer.channel.visit.service.SpecialEventService;
import cn.mobilizer.channel.visit.vo.CallPlanningQuery;
import cn.mobilizer.channel.visit.vo.CallPlanningVO;
@Controller
@RequestMapping(value = "/specialEvent")
public class SpecialEventController extends BaseActionSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private ClientBusinessDefinitionService clientBusinessDefinitionService;
	@Autowired
	private SpecialEventService specialEventService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private ClientStructureService clientStructureService;
	
	@RequestMapping(value = "/query",method = RequestMethod.GET)
	public String query(Model model, Integer page,Integer clientStructureId,String searchName,HttpServletRequest req,Integer  optionValue,String startDate,String endDate) throws BusinessException{
		int clientId = getClientId ();
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("optionName", "event_type");
		parameters.put("name", searchName);
		parameters.put("subordinates", subordinates);
		parameters.put("subStructureId", deptIds);
		parameters.put("startDate",startDate);
		parameters.put("endDate", endDate);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		List<Options> optionsList= optionsService.queryOptions(parameters);
		String tableName;
		if(optionValue == null  && optionsList.size()>0){
			optionValue=optionsList.get(0).getOptionValue();
			tableName="special_event"+optionsList.get(0).getOptionValue();
		}else{
			tableName="special_event"+optionValue;
		}
	   parameters.put("tableName", tableName);
	   parameters.put("optionValue", optionValue);
	   List<ClientBusinessDefinition> clientBusinessDefinitionList=clientBusinessDefinitionService.findClientBusinessDefinitionByTabelName(parameters);
		int count = specialEventService.findSpecialEventCount(parameters);
		int pagenum = page == null ? 1 : page;
		Page<List<SpecialVO>> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Map<String, Object>> list = specialEventService.findSpecialEventList(parameters);
		List<List<SpecialVO>> nlist = new ArrayList<List<SpecialVO>>();
		for (Map<String, Object> map : list) {
			List<SpecialVO> mm = new ArrayList<SpecialVO>();
			SpecialVO svo = new SpecialVO();
			svo.setType(0);//人员姓名，type暂定0
			if(map.get("user_name") != null){
				svo.setCvalue(map.get("user_name").toString());
				svo.setColWidth(10);
				mm.add(svo);
			}
			SpecialVO svos = new SpecialVO();
			svos.setType(1);//人员部门 ，type暂定1
			if(map.get("structure_name") != null){
				svos.setCvalue(map.get("structure_name").toString());
				svos.setColWidth(10);
				mm.add(svos);
			}
			for (ClientBusinessDefinition clientBusinessDefinition : clientBusinessDefinitionList) {
				SpecialVO svof = new SpecialVO();
				svof.setType(clientBusinessDefinition.getControlType());
				if (map.get(clientBusinessDefinition.getColumnName()) != null && !StringUtils.isEmpty(map.get(clientBusinessDefinition.getColumnName()).toString()))
					if (clientBusinessDefinition.getControlType() == 11) {
						String picName = map.get(clientBusinessDefinition.getColumnName()).toString();
						//处理多个照片
						String[] picStr=picName.split(",");
						String[] newPicName =new String[picStr.length];
						for (int i = 0; i < picStr.length; i++) {
							String[] vv = picStr[i].split("_");
							newPicName[i]=(vv[1] + "/" + vv[2] + "/" + picStr[i]);
						}
						svof.setPicNames(newPicName);
					} else if (clientBusinessDefinition.getControlType() == 5) {
						// 取option值
							svof.setCvalue(optionsService.getOptionText(clientBusinessDefinition.getEnumName(), Byte.parseByte(map.get(clientBusinessDefinition.getColumnName()).toString()) , getClientId()));
					} else {
						svof.setCvalue(map.get(clientBusinessDefinition.getColumnName()).toString());
					}
				if(clientBusinessDefinition.getGridWidth() != null)
					svof.setColWidth(clientBusinessDefinition.getGridWidth().intValue());
				mm.add(svof);
			}
			nlist.add(mm);
		}
		pageParam.setItems(nlist);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("optionsList", optionsList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("searchName", searchName);
		model.addAttribute("clientBusinessDefinitionList", clientBusinessDefinitionList);
		model.addAttribute("searchClientStructureId", clientStructureId);
		model.addAttribute("page", pageParam.getPage().toString());
		return "visit/specialEventList";
	}
	/**
	 * 特殊事件导出
	 * @param clientStructureId
	 * @param searchName
	 * @param req
	 * @param optionValue
	 * @param startDate
	 * @param endDate
	 */
	@RequestMapping(value = "/showExportDialog")
	public void exportSpecialEvent(Integer clientStructureId,String structureName,String searchName,Integer  optionValue,String startDate,String endDate, HttpServletResponse resp,HttpServletRequest req){
		int clientId = getClientId ();
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("optionName", "event_type");
		parameters.put("name", searchName);
		parameters.put("subordinates", subordinates);
		parameters.put("subStructureId", deptIds);
		parameters.put("startDate", DateUtil.getDayStart(startDate));
		parameters.put("endDate", DateUtil.getDayStart(endDate));
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		List<Options> optionsList= optionsService.queryOptions(parameters);
		String tableName;
		if(optionValue == null  && optionsList.size()>0){
			optionValue=optionsList.get(0).getOptionValue();
			tableName="special_event"+optionsList.get(0).getOptionValue();
		}else{
			tableName="special_event"+optionValue;
		}
	   parameters.put("tableName", tableName);
	   parameters.put("optionValue", optionValue);
	   	List<ClientBusinessDefinition> clientBusinessDefinitionList=clientBusinessDefinitionService.findClientBusinessDefinitionByTabelName(parameters);
	   	List<Map<String, Object>> list = specialEventService.findSpecialEventList(parameters);
		List<List<SpecialVO>> nlist = new ArrayList<List<SpecialVO>>();
		if(list != null && list.size()>0){
			for (Map<String, Object> map : list) {
				List<SpecialVO> mm = new ArrayList<SpecialVO>();
				SpecialVO svo = new SpecialVO();
				svo.setType(0);//人员姓名，type暂定0
				if(map.get("user_name") != null){
					svo.setCvalue(map.get("user_name").toString());
					svo.setColWidth(10);
					mm.add(svo);
				}
				SpecialVO svos = new SpecialVO();
				svos.setType(1);//人员部门 ，type暂定1
				if(map.get("structure_name") != null){
					svos.setCvalue(map.get("structure_name").toString());
					svos.setColWidth(10);
					mm.add(svos);
				}
				if(clientBusinessDefinitionList != null && clientBusinessDefinitionList.size()>0){
					for (ClientBusinessDefinition clientBusinessDefinition : clientBusinessDefinitionList) {
						SpecialVO svof = new SpecialVO();
						svof.setType(clientBusinessDefinition.getControlType());
						if (map.get(clientBusinessDefinition.getColumnName()) != null && !StringUtils.isEmpty(map.get(clientBusinessDefinition.getColumnName()).toString()))
							if (clientBusinessDefinition.getControlType() == 11) {
								String picName = map.get(clientBusinessDefinition.getColumnName()).toString();
								String[] vv = picName.split("_");
								svof.setCvalue(vv[1] + "/" + vv[2] + "/" + map.get(clientBusinessDefinition.getColumnName()).toString());
							} else if (clientBusinessDefinition.getControlType() == 5) {
								// 取option值
								svof.setCvalue(optionsService.getOptionText(clientBusinessDefinition.getEnumName(), Byte.parseByte(map.get(clientBusinessDefinition.getColumnName()).toString()) , getClientId()));
							} else {
								svof.setCvalue(map.get(clientBusinessDefinition.getColumnName()).toString());
							}
						if(clientBusinessDefinition.getGridWidth() != null)
							svof.setColWidth(clientBusinessDefinition.getGridWidth().intValue());
						mm.add(svof);
					}
				}
				nlist.add(mm);
			}
		}
		if(nlist != null && nlist.size()>0){
			HSSFWorkbook  wb = new HSSFWorkbook();
			ClientStructure clientStructure = clientStructureService.getClientStructureById(clientStructureId);
			String clientStructureName = clientStructure.getStructureName();
			String excelName = null;
			if(clientStructureName.equals(structureName)){
				excelName = clientStructure.getStructureName()+"特殊事件数据";
			}else{
				excelName = clientStructure.getStructureName()+"-"+structureName+"特殊事件数据";
			}
			try {
				resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			resp.setContentType("application/vnd.ms-excel");
			/**在we中创建一个sheet*/
			HSSFSheet wTSSheet = wb.createSheet("特殊事件数据");
			/**在wTSSheet值添加表头(第0行)*/
			HSSFRow row = wTSSheet.createRow((int)0);
			/**创建单元格，并设置表头，设置表头居中*/
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("人员姓名");
			cell = row.createCell(1);
			cell.setCellValue("登录名");
			cell = row.createCell(2);
			cell.setCellValue("部门");
			
			/**表头信息*/
			for (int i = 0; i < clientBusinessDefinitionList.size(); i++) {
				cell = row.createCell(i+3);
				cell.setCellValue(clientBusinessDefinitionList.get(i).getColumnDesc().toString());
			}
			cell.setCellStyle(style);
			for (int i = 0; i < nlist.size(); i++) {
				List<SpecialVO> slist=nlist.get(i);
					row = wTSSheet.createRow((int) i + 1); 
				for (int j = 0; j < slist.size(); j++) {
					SpecialVO vo=slist.get(j);
					if(vo.getType() == 0){
						row.createCell(0).setCellValue(vo.getCvalue().substring(0,vo.getCvalue().lastIndexOf(",")) == null?"":vo.getCvalue().substring(0,vo.getCvalue().lastIndexOf(",")));
						row.createCell(1).setCellValue(vo.getCvalue().substring(vo.getCvalue().lastIndexOf("--")+2) == null?"":vo.getCvalue().substring(vo.getCvalue().lastIndexOf("--")+2));
					}else{
						row.createCell(j+1).setCellValue(vo.getCvalue() == null?"":vo.getCvalue());
					}
				}
			}
				try {
					OutputStream out = resp.getOutputStream();
					wb.write(out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else{
			HSSFWorkbook  wb = new HSSFWorkbook();
			ClientStructure clientStructure = clientStructureService.getClientStructureById(clientStructureId);
			String clientStructureName = clientStructure.getStructureName();
			String excelName = null;
			if(clientStructureName.equals(structureName)){
				excelName = clientStructure.getStructureName()+"特殊事件数据";
			}else{
				excelName = clientStructure.getStructureName()+"-"+structureName+"特殊事件数据";
			}
			try {
				resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			resp.setContentType("application/vnd.ms-excel");
			/**在we中创建一个sheet*/
			HSSFSheet wTSSheet = wb.createSheet("特殊事件数据");
			/**在wTSSheet值添加表头(第0行)*/
			HSSFRow row = wTSSheet.createRow((int)0);
			/**创建单元格，并设置表头，设置表头居中*/
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("人员姓名");
			cell = row.createCell(1);
			cell.setCellValue("登录名");
			cell = row.createCell(2);
			cell.setCellValue("部门");
			
			/**表头信息*/
			for (int i = 0; i < clientBusinessDefinitionList.size(); i++) {
				cell = row.createCell(i+3);
				cell.setCellValue(clientBusinessDefinitionList.get(i).getColumnDesc().toString());
			}
			cell.setCellStyle(style);
			try {
				OutputStream out = resp.getOutputStream();
				wb.write(out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
