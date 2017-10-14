/**
 * 
 */
package cn.mobilizer.channel.base.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.exception.ImprotException;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.ClientUserRelation;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserExpandService;
import cn.mobilizer.channel.base.service.ClientUserRelationService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.DistrictService;
import cn.mobilizer.channel.base.service.ProvinceService;
import cn.mobilizer.channel.base.service.StoreUserMappingService;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.ChannelEnum.CLIENT_USER_ISACTIVATION;
import cn.mobilizer.channel.comm.vo.ChannelEnum.CLIENT_USER_STATUS;
import cn.mobilizer.channel.comm.vo.Enum2Bean;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.systemManager.po.ClientRoles;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesUserMappingService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PasswordHelper;

/**
 * 用户管理Controller
 * @author yeeda.tian
 * 2014年11月12日
 */
@Controller
@RequestMapping(value = "/promotions")
public class PromotionsController extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3822293984997940577L;
	
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientUserExpandService clientUserExpandService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ClientPositionService clientPositionService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private ClientRolesService clientRolesService;
	@Autowired
	private ClientRolesUserMappingService clientRolesUserMappingService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private ClientUserRelationService clientUserRelationService;
	@Autowired
	private StoreUserMappingService storeUserMappingService;
	
	 /**
	  * 用户管理-查询列表
	  * @param model
	  * @param page
	  * @param searchUserName
	  * @param searchUserNo
	  * @param searchClientStructureId
	  * @param req
	  * @return
	  * @throws BusinessException
	  */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String name,Integer clientStructureId,String loginName,Integer isActivation,Integer status, String structureName, Integer parentId,Integer rolesId, Integer isLower, String mod, Integer clientPositionId, HttpServletRequest req) throws BusinessException{
		String reFtl = "/base/clientUserList";
		int clientUserId = getCurrentUserId();
		int clientId = getClientId ();
		if(isLower == null) {
			isLower = 0;
		}
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinates(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		
		ClientRoles clientRole = clientRolesService.getClientRoleByRoleName(clientId, Constants.MOBI_OPTION_STORE);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionService.findClientPositionByName(params);
		String 	clientPositionIds = "";
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			for (int i = 0; i < clientPositionlist.size(); i++) {
				clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
			}
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("name", name);
		parameters.put("loginName", loginName);
		parameters.put("clientUserId", clientUserId);
		parameters.put("clientPositionId", clientPositionId);
		parameters.put("clientPositionIds", clientPositionIds);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subordinates", subordinates);
		parameters.put("subStructureId", deptIds);
		parameters.put("isActivation", isActivation);
		parameters.put("isLower", isLower);
		parameters.put("status", status);
		parameters.put("parentId", parentId);
		parameters.put("rolesId", clientRole.getRoleId());
		
		/**如果为配置模式-获取client_business_definition配置信息**/
		if(mod != null && mod.equals(Constants.DATA_MOD_CONF)) {
			
			List<ClientBusinessDefinition> queryList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_CLIENTUSER, ChannelEnum.PAGE_TPYE.QUERY.getKey(), clientId);
			List<ClientBusinessDefinition> viewList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_CLIENTUSER, ChannelEnum.PAGE_TPYE.LIST.getKey(), clientId);
//			String queryColumn
			model.addAttribute("queryList", queryList);
			model.addAttribute("viewList", viewList);
			reFtl = "/base/autoPromotionsList";
		}
		int count = clientUserService.queryClientUserCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());	
		parameters.put("_size", pageParam.getPageSize ());
		//parameters.put("_orderby", "CLIENT_USER_ID");
		//parameters.put("_order", "DESC");
		List<ClientUser> list = clientUserService.queryClientUserList(parameters);
		pageParam.setItems(list);
		List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId(clientId);
		
		
		model.addAttribute("cpList", cpList);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("name", name);
		model.addAttribute("loginName", loginName);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("structureName", structureName);
		model.addAttribute("clientPositionId", clientPositionId);
		model.addAttribute("isActivation", isActivation);
		model.addAttribute("status", status);
		model.addAttribute("isLower", isLower);
		model.addAttribute("parentId", parentId);
		model.addAttribute("rolesId", rolesId);
		model.addAttribute("mod", mod);
		model.addAttribute("count", count);
		model.addAttribute("clientRole", clientRole);
		//model.addAttribute("clientUserIds", clientUserIds);
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			model.addAttribute("clientPositionlist", clientPositionlist);
		}
		model.addAttribute("page", pageParam.getPage().toString());
		
		return reFtl;
	}
	
	/**
	 * 人员导出
	 * @param searchName
	 * @param searchUserNo
	 * @param clientStructureId
	 * @param clientPositionId
	 * @param loginName
	 * @param isActivation
	 * @param status
	 * @param structureSel
	 * @param structureName
	 * @param req
	 * @param resp
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/showExportDialog")
	public void exportClientUser(String name,Integer clientStructureId,Integer clientPositionId,String loginName,Integer isActivation,Integer status, String structureName,Integer parentId,Integer rolesId,HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		int clientId = getClientId ();
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionService.findClientPositionByName(params);
		String 	clientPositionIds = "";
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			for (int i = 0; i < clientPositionlist.size(); i++) {
				clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
			}
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("name", name);
		parameters.put("loginName", loginName);
		parameters.put("clientPositionId", clientPositionId);
		parameters.put("clientPositionIds", clientPositionIds);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subordinates", subordinates);
		parameters.put("subStructureId", deptIds);
		parameters.put("isActivation", isActivation);
		parameters.put("status", status);
		parameters.put("parentId", parentId);
		parameters.put("rolesId", rolesId);
		List<ClientUser> clientUserList = clientUserService.queryForListForReport(parameters);
		if(clientUserList != null && clientUserList.size()>0){
			HSSFWorkbook  wb = new HSSFWorkbook();
			ClientStructure clientStructure = clientStructureService.getClientStructureById(clientStructureId);
			String clientStructureName = clientStructure.getStructureName();
			String excelName = null;
			if(clientStructureName.equals(structureName)){
				excelName = clientStructure.getStructureName()+"人员数据";
			}else{
				excelName = clientStructure.getStructureName()+"-"+structureName+"人员数据";
			}
			resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
			resp.setContentType("application/vnd.ms-excel");
			/**在we中创建一个sheet*/
			HSSFSheet wTSSheet = wb.createSheet("人员数据");
			/**在wTSSheet值添加表头(第0行)*/
			HSSFRow row = wTSSheet.createRow((int)0);
			/**创建单元格，并设置表头，设置表头居中*/
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("工号");
			cell = row.createCell(1);
			cell.setCellValue("姓名");
			cell = row.createCell(2);
			cell.setCellValue("用户名");
			cell = row.createCell(3);
			cell.setCellValue("部门");
			cell = row.createCell(4);
			cell.setCellValue("职位");
			cell = row.createCell(5);
			cell.setCellValue("电话");
			cell = row.createCell(6);
			cell.setCellValue("手机号");
			cell = row.createCell(7);
			cell.setCellValue("身份证");
			cell = row.createCell(8);
			cell.setCellValue("性别");
			cell = row.createCell(9);
			cell.setCellValue("账号状态");
			cell = row.createCell(10);
			cell.setCellValue("家庭地址");
			cell = row.createCell(11);
			cell.setCellValue("邮编");
			cell = row.createCell(12);
			cell.setCellValue("上级");
			cell = row.createCell(13);
			cell.setCellValue("在职状态");
			cell = row.createCell(14);
			cell.setCellValue("省份");
			cell = row.createCell(15);
			cell.setCellValue("城市");
			cell = row.createCell(16);
			cell.setCellValue("区县");
			cell = row.createCell(17);
			cell.setCellValue("角色");
			cell = row.createCell(18);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			for (int i = 0; i < clientUserList.size(); i++) {
				row = wTSSheet.createRow((int) i + 1); 
				ClientUser clientUser = clientUserList.get(i);
				Province province=provinceService.getPrivinceById(clientUser.getProvinceId());
				if(province != null ){
					clientUser.setProvince(province.getProvince());
				}
				City city=cityService.getCityById(clientUser.getCityId());
				if(city != null){
					clientUser.setCity(city.getCity());
				}
				District district=districtService.getDistrictById(clientUser.getDistrictId());
				if(district != null){
					clientUser.setDistrict(district.getDistrict());
				}
				Map<String, Object> parametersRoels = new HashMap<String, Object>();
				parametersRoels.put("clientId", clientId);
				parametersRoels.put("clientUserId", clientUser.getClientUserId());
				List<ClientRolesUserMapping> clientRolesUserMappingList = clientRolesUserMappingService.getClientRolesUserMapping(parametersRoels);
				String roles="";
				for (int j = 0; j < clientRolesUserMappingList.size(); j++) {
					roles+=clientRolesUserMappingList.get(j).getName()+",";
				}
				clientUser.setRoles(roles);
				ClientUserRelation clientUserparent = clientUserRelationService.getParentByClientUserId(parametersRoels);
				if(clientUserparent != null){
					ClientUser clientUserByparentId=clientUserService.selectByPrimaryKey(clientUserparent.getParentId());
					if(clientUserByparentId != null ){
						clientUser.setParentName(clientUserByparentId.getName());
					}
				}
				row.createCell(0).setCellValue(clientUser.getUserNo() == null?"":clientUser.getUserNo());
				row.createCell(1).setCellValue(clientUser.getName() == null?"":clientUser.getName());
				row.createCell(2).setCellValue(clientUser.getLoginName() == null?"":clientUser.getLoginName());
				row.createCell(3).setCellValue(clientUser.getStructureName() == null?"":clientUser.getStructureName());
				row.createCell(4).setCellValue(clientUser.getPositionName() == null?"":clientUser.getPositionName());
				row.createCell(5).setCellValue(clientUser.getTelephoneNo() == null?"":clientUser.getTelephoneNo());
				row.createCell(6).setCellValue(clientUser.getMobileNo() == null?"":clientUser.getMobileNo());
				row.createCell(7).setCellValue(clientUser.getIdcard() == null?"":clientUser.getIdcard());
				if(clientUser.getSex() != null ){
					if(clientUser.getSex() == 1){
						row.createCell(8).setCellValue("男");
					}else if(clientUser.getSex() == 2){
						row.createCell(8).setCellValue("女");
					}else{
						row.createCell(8).setCellValue("");
					}
				}
				if(clientUser.getIsActivation() != null){
					if(clientUser.getIsActivation() == 1){
						row.createCell(9).setCellValue("启用");
					}else if(clientUser.getIsActivation() == 0){
						row.createCell(9).setCellValue("禁用");
					}else if(clientUser.getIsActivation() == 2){
						row.createCell(9).setCellValue("无法正常使用APP");
					}else{
						row.createCell(9).setCellValue("");
					}
				}
				row.createCell(10).setCellValue(clientUser.getAddr() == null?"":clientUser.getAddr());
				row.createCell(11).setCellValue(clientUser.getPostcode() == null?"":clientUser.getPostcode());
				row.createCell(12).setCellValue(clientUser.getParentName() == null?"":clientUser.getParentName());
				if(clientUser.getStatus() != null){
					if(clientUser.getStatus() == 1){
						row.createCell(13).setCellValue("在职");
					}else if(clientUser.getStatus() == 0){
						row.createCell(13).setCellValue("离职");
					}else{
						row.createCell(13).setCellValue("");
					}
				}
				row.createCell(14).setCellValue(clientUser.getProvince() == null?"":clientUser.getProvince());
				row.createCell(15).setCellValue(clientUser.getCity() == null?"":clientUser.getCity());
				row.createCell(16).setCellValue(clientUser.getDistrict() == null?"":clientUser.getDistrict());
				row.createCell(17).setCellValue(clientUser.getRoleNames() == null?"":clientUser.getRoleNames());
				row.createCell(18).setCellValue(clientUser.getRemark() == null?"":clientUser.getRemark());
			}
				try {
					OutputStream out = resp.getOutputStream();
					wb.write(out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else{
			/**创建一个Excel文件*/
			HSSFWorkbook  wb = new HSSFWorkbook();
			ClientStructure clientStructure = clientStructureService.getClientStructureById(clientStructureId);
			String clientStructureName = clientStructure.getStructureName();
			String excelName = null;
			if(clientStructureName.equals(structureName)){
				excelName = clientStructure.getStructureName()+"人员数据";
			}else{
				excelName = clientStructure.getStructureName()+"-"+structureName+"人员数据";
			}
			resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
			resp.setContentType("application/vnd.ms-excel");
			/**在we中创建一个sheet*/
			HSSFSheet wTSSheet = wb.createSheet("考勤数据");
			/**在wTSSheet值添加表头(第0行)*/
			HSSFRow row = wTSSheet.createRow((int)0);
			/**创建单元格，并设置表头，设置表头居中*/
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("工号");
			cell = row.createCell(1);
			cell.setCellValue("姓名");
			cell = row.createCell(2);
			cell.setCellValue("用户名");
			cell = row.createCell(3);
			cell.setCellValue("部门");
			cell = row.createCell(4);
			cell.setCellValue("职位");
			cell = row.createCell(5);
			cell.setCellValue("电话");
			cell = row.createCell(6);
			cell.setCellValue("手机号");
			cell = row.createCell(7);
			cell.setCellValue("身份证");
			cell = row.createCell(8);
			cell.setCellValue("性别");
			cell = row.createCell(9);
			cell.setCellValue("账号状态");
			cell = row.createCell(10);
			cell.setCellValue("家庭地址");
			cell = row.createCell(11);
			cell.setCellValue("邮编");
			cell = row.createCell(12);
			cell.setCellValue("上级");
			cell = row.createCell(13);
			cell.setCellValue("在职状态");
			cell = row.createCell(14);
			cell.setCellValue("省份");
			cell = row.createCell(15);
			cell.setCellValue("城市");
			cell = row.createCell(16);
			cell.setCellValue("区县");
			cell = row.createCell(17);
			cell.setCellValue("角色");
			cell = row.createCell(18);
			cell.setCellValue("备注");
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
	
	@RequestMapping(value = "/showImportDialog")
	public String showImportDialog(Model model){
		String tempURL = null;
		//美即为模板定制
		if(4 == getClientId())
			tempURL = "/download/import_template/4/user_import_template.xlsx";
		else
			tempURL = "/download/import_template/0/user_import_template.xlsx";
		model.addAttribute("clientId", getClientId());
		model.addAttribute("tempURL", tempURL);
		return "base/importClientUser";
	}
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping(value = "/showUpdatePasswordDialog")
	public String showUpdatePasswordDialog(){
		
		return "base/updatePassword";
	}
	
	/**
     * <p>Description: 人员导入</p>
	 * @throws Exception 
	 * 单位	客户名称	客户代码	地址	电话
     */
	@RequestMapping(value = "/import", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultMessage importClientUser(MultipartFile file, HttpServletRequest request) {
		List<ClientUser> list = new ArrayList<ClientUser>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		String[] titles = values.get(0);
		List<ClientPosition> positionList = clientPositionService.getObjectList(getBaseParams());//职位
		List<Province> provices = provinceService.getAll();//省份
		List<City> citys = cityService.getAll();//城市
		List<District> districts = districtService.getAll();//区县
		List<ClientStructure> structures = clientStructureService.getObjectList(getBaseParams());//部门
		List<ClientRoles> clientRolesList = clientRolesService.getObjectList(getBaseParams());
		//表头
		String[] ctitles = values.get(0);
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.CLIENT_USER_TITLE, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别的列名：" +titles[i]);
				return rm;
			}
		}
		//数据校验
		//拼装数据
		for (int i = 1; i < values.size(); i++) {
			ClientUser ncu = new ClientUser();
			Method[] methods = ncu.getClass().getMethods();
			//得到每个人员的信息(每列数据)
			String[] vv = values.get(i);
			for (int j = 0; j < vv.length; j++) {
				String kvalue = vv[j];
				if(StringUtil.isEmptyString(kvalue)){
					continue;
				}
				//获取列对应的数据库字段
				String cvalue = null;
				for (int k = 0; k < ImportConstants.CLIENT_USER_TITLE.length; k++) {
					if(ImportConstants.CLIENT_USER_TITLE[k].equals(ctitles[j])){
						cvalue = ImportConstants.CLIENT_USER_COLUMN[k];
						break;
					}
				}


				//利用反射机制调用set方法，注意如果是关联字段，定义的临时字段名不要与已有的字段名重复
				for (int k = 0; k < methods.length; k++) {
					if(methods[k].getName().equalsIgnoreCase("set" + cvalue)){
						try {
							methods[k].invoke(ncu, kvalue);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						break;
					}
				}

				

				//处理关联字段
				if(ImportConstants.PROVINCE_NAME.equals(cvalue)){
					Province provice = null;
					for (Province p : provices) {
						if(p.getProvince().equals(kvalue)){
							provice = p;
							break;
						}
					}
					if(provice != null){
						ncu.setProvinceId(provice.getProvinceId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知省份名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.CITY_NAME.equals(cvalue)){
					City city = null;
					for (City c : citys) {
						if(c.getCity().equals(kvalue)){
							city = c;
							break;
						}
					}
					if(city != null){
						ncu.setCityId(city.getCityId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知城市名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.DISTRICT_NAME.equals(cvalue)){
					District district = null;
					for (District d : districts) {
						if(d.getDistrict().equals(kvalue)){
							district = d;
							break;
						}
					}
					if(district != null){
						ncu.setDistrictId(district.getDistrictId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知区县名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.CLIENT_STRUCTURE_NAME.equals(cvalue)){
					ClientStructure clientStructure = null;
					for (ClientStructure cs : structures) {
						if(cs.getStructureName().equals(kvalue)){
							clientStructure = cs;
							break;
						}
					}
					if(clientStructure != null){
						ncu.setClientStructureId(clientStructure.getClientStructureId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知部门名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.POSITION_NAME.equals(cvalue)){
					ClientPosition clientPosition = null;
					for (ClientPosition cp : positionList) {
						if(cp.getPositionName().equals(kvalue)){
							clientPosition = cp;
							break;
						}
					}
					if(clientPosition != null){
						ncu.setClientPositionId(clientPosition.getClientPositionId());;
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知职位名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.I_UP_LEVEL.equals(cvalue)){
					//上级主管
					ncu.setUplevelName(kvalue);
				}
				
				/**暂时不用
				 * else if(ImportConstants.I_PLAT_USER.equals(cvalue)){
					StringBuffer sb = new StringBuffer();
					if(kvalue.indexOf("手机") != -1){
						sb.append("mobile");
					}
					if(kvalue.indexOf("Web") != -1){
						if(!StringUtils.isEmpty(sb)){
							sb.append(",");
						}
						sb.append("user");
					}
					ncu.setRoles(sb.toString());
				}*/
				
			}
			
			ncu.setClientId(getClientId());
			ncu.setPlainPassword("8888");
			PasswordHelper.entryptPassword(ncu);
			list.add(ncu);
		}
		//中间映射表(人员角色、人员上下级关系映射表),在人员添加完毕后处理
		ImportVO ivo = null;
		try {
			ivo = clientUserService.saveAll(list);
		} catch (ImprotException e) {
			ivo = new ImportVO();
			ivo.setResult(false);
			ivo.setMsg(e.getMsg());
		}
		ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
		if(ivo != null && ivo.getResult())
			return rm;
		else{
			rm = ResultMessage.IMPORT_FAIL_RESULT;
			rm.setMessage(ivo.getMsg());
			return rm;
		}
	}
	
	
	/**
	 * 人员管理-编辑页面
	 * @param clientPositionId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditClientUser/{clientUserId}")
	public String showEditClientUser(@PathVariable("clientUserId")Integer clientUserId, Model model, String mod) throws BusinessException{
		String reFtl = "base/showEditClientUser";
		int clientId = getClientId();
		if(mod !=null && mod.equals(Constants.DATA_MOD_CONF)) {
			List<ClientBusinessDefinition> editList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_CLIENTUSER, ChannelEnum.PAGE_TPYE.EDIT.getKey(), clientId);
			model.addAttribute("editList", editList);
			reFtl = "base/autoShowEditPromotions";
		}
		ClientUser clientUser = clientUserService.findFullUserInfoByPrimaryKey (clientUserId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientUserId", clientUserId);
		List<ClientRolesUserMapping> clientRolesUserMappingList=clientRolesUserMappingService.findClientRolesListByClientUserId(clientUserId,clientId);
		String roleNames = "";
		if(clientRolesUserMappingList !=null && clientRolesUserMappingList.size()>0){
			for ( int i = 0 ; i < clientRolesUserMappingList.size() ; i++ ) {
				if(i == 0){
					roleNames += clientRolesUserMappingList.get(i).getRoleId();
				} else {
					roleNames += ","+clientRolesUserMappingList.get(i).getRoleId();
				}
			}
		}
		clientUser.setRoleNames(roleNames);
		model.addAttribute("clientUser" , clientUser);
		model.addAttribute("clientId" , clientId);
		return reFtl;
	}
	/**
	 * 查看
	 * @param clientUserId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showClientUserDtail/{clientUserId}")
	public String showDtail(@PathVariable("clientUserId")Integer clientUserId, Model model,String mod)throws BusinessException{
		String reFtl="base/showClientUserDtail";
		ShiroUser user = getShiroUser ();
		if(user != null && user.clientId != null) {
		int clientId = getClientId();
		if(mod !=null && mod.equals(Constants.DATA_MOD_CONF)) {
			List<ClientBusinessDefinition> seeList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_CLIENTUSER, ChannelEnum.PAGE_TPYE.SEE.getKey(), clientId);
			model.addAttribute("seeList", seeList);
			reFtl = "base/autoShowClientUserDtail";
		}
		ClientUser clientUser = clientUserService.findFullUserInfoByPrimaryKey(clientUserId);
		ClientPosition clientPosition=clientPositionService.findClientPositionById(clientUser.getClientPositionId(), clientId);
		clientUser.setPositionName(clientPosition.getPositionName());
		Province province=provinceService.getPrivinceById(clientUser.getProvinceId());
		if(province != null ){
			clientUser.setProvince(province.getProvince());
		}
		City city=cityService.getCityById(clientUser.getCityId());
		if(city != null){
			clientUser.setCity(city.getCity());
		}
		District district=districtService.getDistrictById(clientUser.getDistrictId());
		if(district != null){
			clientUser.setDistrict(district.getDistrict());
		}
		ClientUser clientUserparent = clientUserService.findFullUserInfoByPrimaryKey(clientUser.getParentId());
		if(clientUserparent != null){
			clientUser.setParentName(clientUserparent.getName());
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientUserId", clientUserId);
		List<ClientRolesUserMapping> clientRolesUserMappingList=clientRolesUserMappingService.findClientRolesListByClientUserId(clientUserId,clientId);
		String roleNames = "";
		if(clientRolesUserMappingList !=null && clientRolesUserMappingList.size()>0){
			for ( int i = 0 ; i < clientRolesUserMappingList.size() ; i++ ) {
				if(i == 0){
					roleNames += clientRolesUserMappingList.get(i).getRoleId();
				} else {
					roleNames += ","+clientRolesUserMappingList.get(i).getRoleId();
				}
			}
		}
		clientUser.setRoleNames(roleNames);
		model.addAttribute("clientUser" , clientUser);
		return reFtl;
		}
		return "/dialog/error";	
	}
	
	/**
	 * 人员管理-新增页面
	 * @param model
	 * @return
	 * @throws BusinessException
	 * 
	 */
	@RequestMapping(value = "/showAddClientUser")
	public String showAddClinetUser(Model model,String mod) throws BusinessException{
		String reFtl = "base/showAddClientUser";
		int clientId = getClientId();
		int clientUserId = getCurrentUserId();
		ClientRoles clientRole = clientRolesService.getClientRoleByRoleName(clientId, Constants.MOBI_OPTION_STORE);
		/**获取当前用户的角色等级**/
//		Byte level = clientRolesService.getUserRoleLevel(clientUserId, clientId);
//		List<ClientRoles> clientRolesList=clientRolesService.findSystemRolesByClientIdAndRoleLevel(clientId,level);
//		List<ClientRoles> clientRolesList=clientRolesService.findSystemRolesByClientId(clientId);
//		List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId(clientId);
//		List<Province> listprovince = provinceService.getAll();
		if(mod !=null && mod.equals(Constants.DATA_MOD_CONF)) {
			List<ClientBusinessDefinition> addList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_CLIENTUSER, ChannelEnum.PAGE_TPYE.ADD.getKey(), clientId);
			model.addAttribute("addList", addList);
			reFtl = "base/autoShowAddPromotions";
		}
		
//		model.addAttribute("cpList", cpList);
//		model.addAttribute("listprovince", listprovince);
		model.addAttribute("clientId", clientId);
		model.addAttribute("clientRole", clientRole);
		return reFtl;
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addClientUser", produces="application/json")
	@ResponseBody
	public Object addClientUser(ClientUser clientUser) throws BusinessException{
		clientUser.setPlainPassword(Constants.DEFAULTPWASSWORD);
		PasswordHelper.entryptPassword(clientUser);
		if (log.isDebugEnabled()) {
			log.debug("start method<addClientUser>");
		}
		clientUserService.addClientUser(clientUser);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	
	/**
	 * 删除
	 * @param clientPositionId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteClinetUser/{clientUserId}", produces="application/json")
	@ResponseBody	
	public Object deleteClinetUser(@PathVariable("clientUserId")Integer clientUserId) throws BusinessException  {
		int clientId = getClientId ();
		if (log.isDebugEnabled()) {
			log.debug("start method<deleteClinetUser>");
		}
		//clientPositionService.deleteClinetPostion(clientPositionId);
		clientUserService.deleteClientUser(clientUserId,clientId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}
	
	/**
	 * 修改
	 * @param clientPosition
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateClientUser", produces="application/json")
	@ResponseBody
	public Object updateClientUser(ClientUser clientUser, String oldRoleNames, Integer oldParentId) throws BusinessException{
		if (log.isDebugEnabled()) {
			log.debug("start method<addClientUser>");
		}				
		clientUserService.update(clientUser,oldRoleNames, oldParentId);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	
	//用户密码重置
	@RequestMapping(value = "/resetPasswd/{clientUserId}", produces="application/json")
	@ResponseBody
	public Object resetPasswd(@PathVariable("clientUserId")Integer clientUserId) throws BusinessException{
		Integer clientId = getClientId();
		ClientUser cu = clientUserService.selectByPrimaryKey(clientUserId);
		cu.setPlainPassword("8888");//重置密码为8888
		PasswordHelper.entryptPassword(cu);
//		cu.setLastLoginTime(null);
		int i = clientUserService.updatePassword(cu);
//		clientUserExpandService.updateUserLoginInfo(clientUserId, null, null, null, clientId);
		clientUserExpandService.updateUserLastLoginTime(clientUserId,null);
		if(i > 0)
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		else
			return ResultMessage.UPDATE_FAIL_RESULT;
	}

	/**
	 * 查询市的名称
	 * @param province_id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findForListProvince/{provinceId}", produces="application/json")
	@ResponseBody
	public List<City> queryForListCityName(@PathVariable("provinceId")Integer provinceId) throws BusinessException{
			return cityService.queryCityByProvinceId(provinceId);
	}
	
	/**
	 * 查询县的名称
	 * @param cityId
	 * @return list
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findForListDistrict/{cityId}", produces="application/json")
	@ResponseBody
	public List<District> queryForListDistrictName(@PathVariable("cityId")Integer cityId) throws BusinessException{
		return districtService.queryDistrictByCityId(cityId);
		
	}
	
	/**
	 * 
	 * @param model
	 * @param page
	 * @param clientUser
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showSelectLeaders")
	public String showSelectLeaders(Model model, Integer page, ClientUser clientUser, HttpServletRequest req) throws BusinessException{
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("name", clientUser.getName ());
		parameters.put("userNo", clientUser.getUserNo ());
		parameters.put("clientStructureId", clientUser.getClientStructureId ());
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		int count = clientUserService.queryClientUserCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		//parameters.put("_orderby", "CLIENT_USER_ID");
		//parameters.put("_order", "DESC");
		List<ClientUser> list = clientUserService.queryClientUserList(parameters);
		pageParam.setItems(list);
		
		model.addAttribute("clientUser", clientUser);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		return "base/showSelectLeaders";
	}
	
	/**
	 * 匹配权限 的直属上级
	 * @param clientUserId
	 * @param q
	 * @param page
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getClientUserWithoutSelf", produces="application/json")
	@ResponseBody
	public List<ClientUser> getClientUserWithoutSelf(Integer clientUserId, String q, Integer page, String childId) throws BusinessException{
		int clientId = getClientId ();
		String clientUserIdChill = null;
		String name = q;
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(getClientStructureId());
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinates(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		if(clientUserId !=null){
			clientUserIdChill=channelCommService.getSubordinates(clientUserId.toString());
		}
		List<ClientUser> list=clientUserService.getClientUserWithoutSelf(clientUserId,clientId,name, clientUserIdChill, childId,subordinates,deptIds);
		return list;
	}
	
	/**
	 * 
	 * @param clientUserId
	 * @return  
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getParentClientUser/{clientUserId}", produces="application/json")
	@ResponseBody
	public ClientUser getParentClientUser(@PathVariable("clientUserId")Integer clientUserId) throws BusinessException{
		int clientId = getClientId ();
		ClientUser clientUser = null;
		if(clientUserId != null ){
			clientUser=clientUserService.getParentClientUser(clientUserId,clientId);
		}
		return clientUser;
	}
	
	/**获取人员--职位关系数据
	 * @author Nany
	 * 2014年12月19日下午6:00:25
	 * @return
	 */
	@RequestMapping(value ="/getClientUserPosition",produces="application/json")
	@ResponseBody
	public List<ClientUser> getUserPositionWithoutSelf(Integer clientUserId,String q, Integer page, String clientPositionIds) {
		int clientId = getClientId ();
		String name = q;
		Integer clientStructureId = getClientStructureId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			clientUserId = getCurrentUserId();
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		List<ClientUser> list = clientUserService.getClientUserPosition(clientUserId,clientId,name,deptIds,subordinates,clientPositionIds);
		return list;
	}
	/**核实用户名
	 * @author Nany
	 *  2014年12月19日下午6:00:25
	 * @return
	 */
	@RequestMapping(value ="/findForLogName/{logName}",produces="application/json")
	@ResponseBody
	public ClientUser getLogName(@PathVariable("logName")String logName){
		int clientId = getClientId();
		ClientUser clientUser=clientUserService.findLogName(logName,clientId);
		return clientUser;
	}

	/**
	 * @author Nany
	 * 2014年12月31日下午3:32:10
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value="/getClientUser/{clientUserId}",produces="application/json")
	@ResponseBody
	public ClientUser getUserPositionById(@PathVariable("clientUserId")Integer clientUserId) {
		int clientId = getClientId();
		ClientUser clientUser = clientUserService.getClientUser(clientUserId,clientId);
		return clientUser;
	}
	
	/**
	 *返回修改旧密码对象 
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value="/checkoldpassword",produces="application/json")
	@ResponseBody
	public boolean checkoldpassword(String oldPassword) {
		if(oldPassword == ""){
			return false;
		}
		ClientUser clientUser= getClientUser();
		boolean isOldPassword=PasswordHelper.checkPasswd(clientUser.getLoginPwd(), clientUser.getSalt(), oldPassword);
		return isOldPassword;
	}
	
	/**
	 * 修改密码
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updatePass", produces="application/json")
	@ResponseBody
	public Object updatePass(String userPassword) throws BusinessException{
//		ClientUser clientUser= clientUserService.selectByPrimaryKey (getCurrentUserId());
		ClientUser clientUser= getClientUser();
		clientUser.setPlainPassword(userPassword);
		PasswordHelper.entryptPassword(clientUser);
		int i = clientUserService.updatePassword(clientUser);
		if(i > 0){
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal(); 
			user.clientUser.setLoginPwd(clientUser.getLoginPwd());
			user.clientUser.setSalt(clientUser.getSalt());
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		}
		else{
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
	}
	/**
	 * 查看某个人的全部下级。
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value = "/getClientUserAjax", produces="application/json")
	@ResponseBody
	public List<ClientUser> getClientUserAjax(){
		int clientId = getClientId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(getClientStructureId());
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
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subordinates", subordinates);
		parameters.put("subStructureId", deptIds);
		List<ClientUser> list = clientUserService.queryClientUserList(parameters);
		return list;
		
	}
	/***
	 * 替换上级人员-页面
	 * @param parentAndcount
	 * @return
	 */
	@RequestMapping(value = "/showBatchClientUser")
	public String showReplaceClientUser(Integer clientStructureId,Model model){
		int clientId = getClientId();
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("clientId", clientId);
		return "base/showPromotionsChildClientUser";
	}
	/**
	 * 替换人员嵌入页面
	 * @param model
	 * @param page
	 * @param name
	 * @param clientStructureId
	 * @param parentId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/replaceUpClientUser", produces="application/json")
	public String replaceUpClientUser(Model model, Integer page, String name,Integer clientStructureId,Integer clientUserId,Integer status,HttpServletRequest req){
		int clientId = getClientId ();
		clientStructureId = clientStructureId !=null ? clientStructureId : getClientStructureId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subordinates 所有下级人员","号隔开**/
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		
		ClientRoles clientRole = clientRolesService.getClientRoleByRoleName(clientId, Constants.MOBI_OPTION_STORE);
		
		/**得到长促 和 促销队 的clientPositionId*/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionService.findClientPositionByName(params);
		String 	clientPositionIds = "";
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			for (int i = 0; i < clientPositionlist.size(); i++) {
				clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
			}
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("name", name);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subordinates", subordinates);
		parameters.put("subStructureId", deptIds);
		parameters.put("parentId", clientUserId);
		parameters.put("status", status);
		parameters.put("rolesId", clientRole.getRoleId());
		parameters.put("clientPositionIds", clientPositionIds);
		int count = clientUserService.queryClientUserCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		//parameters.put("_orderby", "CLIENT_USER_ID");
		//parameters.put("_order", "DESC");
		List<ClientUser> list = clientUserService.queryClientUserList(parameters);
		pageParam.setItems(list);
		List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId(clientId);
		model.addAttribute("cpList", cpList);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("name", name);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("parentId", clientUserId);
		model.addAttribute("count", count);
		model.addAttribute("page", pageParam.getPage().toString());
		String replaceAlls = this.replaceAlls(name, clientStructureId, clientUserId,status);
		model.addAttribute("replaceAlls", replaceAlls);
		return "base/showPromotionsReplaceUpClientUser";
	}
	
	/**
	 * 修改密码
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updatePassword", produces="application/json")
	@ResponseBody
	public Object updatePassword(String oldPassword, String newPassword, String rePassword) throws BusinessException{
		Integer clientUserId = getCurrentUserId();
		ClientUser clientUser= new ClientUser();
		Date thisDate = new Date();
		//服务端校验
		clientUser.setClientUserId(clientUserId);
		clientUser.setPlainPassword(newPassword);
		//clientUser.setLastLoginTime(thisDate);
		PasswordHelper.entryptPassword(clientUser);
		int i = clientUserService.updatePassword(clientUser);
		if(i > 0) {
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal(); 
			//更新最后一次登录时间
			clientUserExpandService.updateUserLastLoginTime(clientUserId, thisDate);
			user.clientUser.setLastLoginTime(thisDate);
			user.clientUser.setLoginPwd(clientUser.getLoginPwd());
			user.clientUser.setSalt(clientUser.getSalt());
			//强制用户重新登录
			SecurityUtils.getSubject().logout();
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		} else {
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
	}
	/**
	 * 批量替换上级
	 * @param parentId
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value = "/updateParentClientUser")
	@ResponseBody
	public Object updateParentClientUser(Integer clientUserId,Integer clientUserId2,String hiddenClientUserIds){
		int clientId = getClientId ();
		clientUserRelationService.updateParentClientUser(clientId,clientUserId,clientUserId2,hiddenClientUserIds);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 人员-账号状态
	 * @return
	 */
	@RequestMapping(value = "/getIsActivation")
	@ResponseBody
	public List<Enum2Bean> getIsActivation(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		for (CLIENT_USER_ISACTIVATION s :CLIENT_USER_ISACTIVATION.values()) {
			Enum2Bean e = new Enum2Bean();
			e.setId(s.getKey().intValue());
			e.setName(s.getCnName());
			ls.add(e);
		}
		return ls;
	}
	
	/**
	 * 人员-在职状态
	 * @return
	 */
	@RequestMapping(value = "/getStatus")
	@ResponseBody
	public List<Enum2Bean> getStatus(){
		List<Enum2Bean> ls = new ArrayList<Enum2Bean>();
		for (CLIENT_USER_STATUS s :CLIENT_USER_STATUS.values()) {
			Enum2Bean e = new Enum2Bean();
			e.setId(s.getKey().intValue());
			e.setName(s.getCnName());
			ls.add(e);
		}
		return ls;
	}
	
	/**
	 * 替换所有人员的clientUserIds
	 * @param storeName
	 * @param clientStructureId
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value = "/replaceAlls")
	@ResponseBody
	public String replaceAlls(String name,Integer clientStructureId,Integer clientUserId,Integer status){
		int clientId = getClientId();
		 clientStructureId = clientStructureId != null ? clientStructureId : getClientStructureId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		
		
		ClientRoles clientRole = clientRolesService.getClientRoleByRoleName(clientId, Constants.MOBI_OPTION_STORE);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionService.findClientPositionByName(params);
		String 	clientPositionIds = "";
		if(clientPositionlist != null && !clientPositionlist.isEmpty()){
			for (int i = 0; i < clientPositionlist.size(); i++) {
				clientPositionIds+= clientPositionlist.get(i).getClientPositionId().toString()+",";
			}
		}
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("name", name);
		parameters.put("parentId", clientUserId);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		parameters.put("status", status);
		parameters.put("clientPositionIds", clientPositionIds);
		parameters.put("clientRole", clientRole.getRoleId());
		String strReplaceAlls=clientUserService.replaceAllsClientUser(parameters);
		return strReplaceAlls;
	}
	
	/**
	 * 检查人员与门店的关联关系
	 * @return
	 */
	@RequestMapping(value = "/checkUserStoreRelation")
	@ResponseBody
	public Object checkUserStoreRelation(Integer clientUserId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId",super.getClientId());
		params.put("clientUserId", clientUserId);
		params.put("isDelete",Constants.IS_DELETE_FALSE);
		int items = clientUserService.selectUserStoreRelation(params);
		if(items>0){
			return ResultMessage.USER_STORE_RELA_ERROR;
		}else{
			return ResultMessage.USER_STORE_RELA_SUCCESS;
		}
	}
	/**
	 * 促销队 和 长促
	 * @return
	 */
	@RequestMapping(value = "/marketing")
	@ResponseBody
	public Object marketing(){
		int clientId = getClientId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("promotingGrowth", Constants.PROMOTING_GROWTH);
		params.put("promotingTeam", Constants.PROMOTING_TEAM);
		List<ClientPosition> clientPositionlist = clientPositionService.findClientPositionByName(params);
		return clientPositionlist;
	}
}
