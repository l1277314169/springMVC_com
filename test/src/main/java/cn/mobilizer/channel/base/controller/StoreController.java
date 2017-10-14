/**
 * 
 */
package cn.mobilizer.channel.base.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.po.BaiduLocationJson;
import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreGroup;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.base.service.ChainService;
import cn.mobilizer.channel.base.service.ChannelService;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.ClientStructureService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.DistrictService;
import cn.mobilizer.channel.base.service.ImportStoreService;
import cn.mobilizer.channel.base.service.OptionsService;
import cn.mobilizer.channel.base.service.ProvinceService;
import cn.mobilizer.channel.base.service.StoreGroupService;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.service.StoreUserMappingService;
import cn.mobilizer.channel.base.vo.GeoVo;
import cn.mobilizer.channel.base.vo.GeoVo.Bounds;
import cn.mobilizer.channel.base.vo.GeoVo.Point;
import cn.mobilizer.channel.base.vo.ImportVO;
import cn.mobilizer.channel.base.vo.StoreSearchVO;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.init.InitOptionsBean;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ChannelEnum;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.vo.KeyValueVo;
import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.util.PropertiesHelper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yeeda.tian
 * 用户管理Controller
 * 2014年11月12日15:39:50
 */

@Controller
@RequestMapping(value = "/store")
public class StoreController extends BaseActionSupport {

	private static final long	serialVersionUID	= -3822293984997940577L;
	
	@Autowired
	private StoreService storeService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private ClientStructureService clientStructureService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ChainService chainService;
	@Autowired
	private OptionsService optionsService;
	@Autowired
	private StoreGroupService storeGroupService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private StoreUserMappingService storeUserMappingService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	@Autowired
	private ClientPositionService clientPositionService;
	@Autowired
	private ImportStoreService importStoreService;
	/** 
	 * 门店管理-查询列表
	 * @author Nany
	 * 2014年12月18日下午3:27:19
	 * @param model
	 * @param page
	 * @param storeSearchVO
	 * @param req
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, String mod, Integer page,StoreSearchVO storeSearchVO, HttpServletRequest req) throws BusinessException{
		String reFtl = "/base/storeList";
		int clientId = getClientId();
		Integer clientStructureId = storeSearchVO.getClientStructureId () != null ? storeSearchVO.getClientStructureId () : getClientStructureId();
		storeSearchVO.setClientStructureId(clientStructureId);
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		@SuppressWarnings("unused")
		int clientUserId = getCurrentUserId();
		if(subStructureId == null || subStructureId == "" ){
			
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		if(!StringUtil.isEmptyString(deptIds) && deptIds.endsWith(",")){
			deptIds = deptIds.substring(0, deptIds.length()-1);
		}
		String subordinates = channelCommService.getSubordinates(getCurrentUserId().toString());
		if(!StringUtil.isEmptyString(subordinates) && subordinates.endsWith(",")){
			subordinates = subordinates.substring(0, subordinates.length()-1);
		}
		/**如果为配置模式-获取client_business_definition配置信息**/
		if(mod != null && mod.equals(Constants.DATA_MOD_CONF)) {
			List<ClientBusinessDefinition> queryList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_STORE, ChannelEnum.PAGE_TPYE.QUERY.getKey(), clientId);
			List<ClientBusinessDefinition> viewList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_STORE, ChannelEnum.PAGE_TPYE.LIST.getKey(), clientId);
			model.addAttribute("queryList", queryList);
			model.addAttribute("viewList", viewList);
			reFtl = "/base/autoStoreList";
		}
		String channelIds = channelService.findChannelChild(storeSearchVO.getChannelId());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("storeName", storeSearchVO.getStoreName ());
		parameters.put("storeNo", storeSearchVO.getStoreNo ());
		parameters.put("channelId", channelIds);
		parameters.put("clientUserId", storeSearchVO.getClientUserId ());
		parameters.put("distributorId", storeSearchVO.getDistributorId ());
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		parameters.put("status", storeSearchVO.getStatus());
		parameters.put("storeType", Constants.STORE_TYPE);
		int count;
		count = storeService.queryStoreCount(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<Store> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		List<Store> list = storeService.queryStoreList (parameters);
		pageParam.setItems(list);
		if(storeSearchVO.getClientUserId () != null){
			model.addAttribute("count", count);
		}
		//内存中获取store_status
		Map<String,Map<String,Map<String,String>>> optionMap = InitOptionsBean.optionMap;
		String store_status = Constants.OPTION_NAME_STORE_STATUS;
		Map<String,String> storeStatus =  (optionMap.get(String.valueOf(clientId)) !=null && optionMap.get(String.valueOf(clientId)).get(store_status) != null)? optionMap.get(String.valueOf(clientId)).get(store_status):null;
		model.addAttribute("count", count);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("storeSearchVO", storeSearchVO);
		model.addAttribute("storeStatus", storeStatus);
		model.addAttribute("page", pageParam.getPage().toString());
		model.addAttribute("mod", mod);
		return reFtl;
	}

	@RequestMapping(value = "showImportDialog")
	public String showImportDialog(Model model){
		model.addAttribute("clientId", getClientId());
        
		return "base/importStore";
	}
	
	/**
     * <p>Description: 门店导入</p>
	 * @throws Exception 
	 * 单位	客户名称	客户代码	地址	电话
     */
	@RequestMapping(value = "import", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public Object importStore(Model model, MultipartFile file, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		
		List<Store> storeList = new ArrayList<Store>();
		ExcelReader reader = new ExcelReader(file);
		List<String[]> values = reader.getAllData(0);
		String[] titles = values.get(0);
		//列名校验
		for (int i = 0; i < titles.length; i++) {
			if(StringUtils.isEmpty(titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("列名不能为空");
				return rm;
			}
			if(!MobiStringUtils.contains(ImportConstants.STORE_TITLE, titles[i])){
				ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
				rm.setMessage("存在不能识别的列名：" +titles[i]);
				return rm;
			}
		}
		//数据校验
		//拼装数据
		List<Province> provices = provinceService.getAll();
		List<City> citys = cityService.getAll();
		List<District> districts = districtService.getAll();
		List<ClientStructure> structures = clientStructureService.getObjectList(this.getBaseParams());
		List<Channel> channels = channelService.getObjectList(getBaseParams());
		List<Chain> chains = chainService.getObjectList(getBaseParams());
		String[] ctitles = values.get(0);
		
		for (int i = 1; i < values.size(); i++) {
			Store nstroe = new Store();
			Method[] methods = nstroe.getClass().getMethods();
			String[] vv = values.get(i);
			for (int j = 0; j < vv.length; j++) {	
				String kvalue = vv[j];//值
				String cvalue = null;//列名
				if(StringUtils.isEmpty(kvalue)){
					continue;
				}
				for (int k = 0; k < ImportConstants.STORE_TITLE.length; k++) {
					if(ImportConstants.STORE_TITLE[k].equals(ctitles[j])){
						cvalue = ImportConstants.STORE_COLUMN[k];
						break;
					}
				}
				
				for (int k = 0; k < methods.length; k++) {
					if(methods[k].getName().equalsIgnoreCase("set" + cvalue)){
						methods[k].invoke(nstroe, kvalue);
						break;
					}
				}
				if(ImportConstants.PROVINCE_NAME.equals(cvalue)){
					Province provice = null;
					for (Province p : provices) {
						if(p.getProvince().equals(kvalue)){
							provice = p;
							break;
						}
					}
					if(provice != null){
						nstroe.setProvinceId(provice.getProvinceId());
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
						nstroe.setCityId(city.getCityId());
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
						nstroe.setDistrictId(district.getDistrictId());;
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
						nstroe.setClientStructureId(clientStructure.getClientStructureId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知部门名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.CHANNEL_NAME.equals(cvalue)){
					Channel channel = null;
					for (Channel c : channels) {
						if(c.getChannelName().equals(kvalue)){
							channel = c;
							break;
						}
					}
					if(channel != null){
						nstroe.setChannelId(channel.getChannelId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知渠道名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.CHAIN_NAME.equals(cvalue)){
					Chain chain = null;
					for (Chain c : chains) {
						if(c.getChainName().equals(kvalue)){
							chain = c;
							break;
						}
					}
					if(chain != null){
						nstroe.setChainId(chain.getChainId());
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知连锁名称：" + kvalue);
						return rm;
					}
				}else if(ImportConstants.I_USER.equals(cvalue)){
					ClientUser cu = clientUserService.findByName(kvalue, getClientId());
					if(cu != null){
						nstroe.addUser(cu);
					}else{
						ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
						rm.setMessage("未知人员姓名：" + kvalue);
						return rm;
					}
				}
			}
			//设置关联属性
			nstroe.setStoreId(UUID.randomUUID().toString());
			nstroe.setClientId(getClientId());
			storeList.add(nstroe);
		}
		ImportVO ivo = storeService.saveAll(storeList);
		if(ivo.getResult())
			return ResultMessage.IMPORT_SUCCESS_RESULT;
		else{
			ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
			rm.setMessage(ivo.getMsg());
			return rm;
		}
	}
	
	/**
	* 门店管理-新增门店-渠道
	*@author Nany
	*2014年12月1日下午4:38:52
	*/
	@RequestMapping(value = "/getTreeNode", produces="application/json")
	@ResponseBody
	public Object getTreeNode(@RequestParam(value = "id",defaultValue="-1") Integer id) {
		int clientId = getClientId();
		List<TreeNodeVO> list = channelService.getTreeNodes(clientId, id);
		return list;
	}
	
	/**
	 * 门店管理-编辑(查询出相关数据，用于数据回显)
	 * @author Nany
	 * 2014年11月26日下午5:48:26
	 * @return
	 */
	@RequestMapping(value = "/showEditStore/{storeId}", produces="application/json")
	public String  showEditStore(@PathVariable("storeId")String storeId,Model model,String mod) {
		String reFtl = "base/showEditStore";
		int clientId = getClientId();
		Store store = storeService.findStoreByStoreId(storeId);
		
		if(mod !=null && mod.equals(Constants.DATA_MOD_CONF)) {
			List<ClientBusinessDefinition> editList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_STORE, ChannelEnum.PAGE_TPYE.EDIT.getKey(), clientId);
			model.addAttribute("editList", editList);
			reFtl = "base/autoShowEditStore";
		}
		
		model.addAttribute("clientId", clientId);
		model.addAttribute("store", store);
		return reFtl;
		
	}
	
	@RequestMapping(value = "/information")
	public String  information(Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("datepicker",sdf.format(new Date()));
		model.addAttribute("cityId","48");
		model.addAttribute("cityName","上海市");	
		return "report/storeInformation";		
	}	
	
	/**
	 * 门店管理-编辑门店坐标
	 * @return
	 */
	@RequestMapping(value = "/showEditStoreLocation/{storeId}", produces="application/json")
	public String  showEditStoreLocation(@PathVariable("storeId")String storeId, Model model) {
		String reFtl = "base/showEditStoreLocation";
		Store store = storeService.selectByPrimaryKey(storeId);
		model.addAttribute("store", store);
		return reFtl;
	}
	
	/**验证门店编号
	 * @author Nany
	 * 2015年1月23日下午2:55:45
	 * @param storeNo
	 * @return
	 */
	@RequestMapping(value="/validateStoreNo")
	@ResponseBody
	public Object validateStoreNo(String storeNo) {
 		int clientId = getClientId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeNo", storeNo);
		params.put("isDelete",Constants.IS_DELETE_FALSE);
		List<Store> storeList = storeService.findStoreByStoreNo(params);
		if(storeList!= null && storeList.size() > 0){
			return ResultMessage.NO_EXITE_FAIL;
		}else{
			return ResultMessage.NO_EXITE_SUCCESS;
		}
	}
	
	/**
	 * 门店管理-编辑-保存
	 * @author Nany
	 * 2014年11月27日下午5:37:11
	 */
	@RequestMapping(value = "/updateStore", produces="application/json")
	@ResponseBody
	public Object updateStore(Store store,String oldBusinessman, String oldPromotions, String oldStoreNo) throws BusinessException{
		try {
			int clientId = getClientId();
			String storeId = store.getStoreId();
			/**服务端验证storeNo的唯一性**/
			String storeNo = store.getStoreNo();
			Byte status = store.getStatus();
			if(StringUtils.isNotEmpty(storeNo) && !StringUtils.equals(storeNo, oldStoreNo)){
				Store s = storeService.findStoreByStoreNo(storeNo,clientId);
				if(s != null){
					return ResultMessage.NO_EXITE_FAIL;
				}
			}
			/**服务端验证-门店关闭的情况下是否还有人店对应关系**/
			if(status != null && status.equals(Constants.STORE_STATUS_WX)){
//				List<StoreUserMapping> ls = storeUserMappingService.getStoreUserMappingByStoreIdAndClientId(storeId, clientId);
//				if(ls !=null && ls.size() >0 ){
//					return ResultMessage.STORe_USER_MAPPING_EXITE_FAIL;
//				}
				if(StringUtil.isNotEmptyString(store.getBusinessman()) || StringUtil.isNotEmptyString(store.getPromotions())){
					return ResultMessage.STORE_USER_MAPPING_EXITE_FAIL;
				}
			}
			String str = "";
			String[] oldClientUserIds = null;
			str = StringUtil.joinWithComma(oldBusinessman, oldPromotions);
			if(StringUtils.isNotEmpty(str)){
				oldClientUserIds = ArrayUtil.arrayUnique(str.split(","));
			}
			store.setClientId(clientId);
			storeService.updateStore(store,oldClientUserIds);
			/**删除未拜访的计划*/
			storeService.deleteCallPalnning(store, oldBusinessman, oldPromotions,clientId);
			return ResultMessage.UPDATE_SUCCESS_RESULT;
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResultMessage.UPDATE_FAIL_RESULT;
		}
	}
	
	/**
	* 门店管理-删除（非物理删除，仅仅是改变状态码）
	* @author Nany
	* 2014年11月27日下午6:37:51
	*/
	@RequestMapping(value = "/deleteStore/{storeId}", produces="application/json")
	@ResponseBody	
	public Object deleteStore(@PathVariable("storeId")String storeId) {
		
		storeService.deleteStore(storeId);
		return ResultMessage.DELETE_SUCCESS_RESULT;
		 
	}
	
	/**门店-分布情况
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/distribute")
	public String distribute(Model model) throws BusinessException{
		model.addAttribute("structureId", super.getClientStructureId());
		return "base/showStoreDistribute";
	}
	
	/**
	 * 加载地图上面的坐标点
	 * @return
	 */
	@RequestMapping(value = "/loadDistributeMarker",produces="application/json")
	@ResponseBody
	public Object loadDistributeMarker(String slng,String slat,String nlng,String nlat,Integer zoom){
		int clientId = getClientId ();
		GeoVo geoVo = new GeoVo();
		Bounds bounds = geoVo.getBounds();
		
		Point southWest = geoVo.getPoint();
		southWest.setLat(new BigDecimal(slat));
		southWest.setLng(new BigDecimal(slng));
		
		Point northEast = geoVo.getPoint();
		northEast.setLat(new BigDecimal(nlat));
		northEast.setLng(new BigDecimal(nlng));
		
		log.debug("slng="+slng+",slat="+slat+",nlng="+nlng+",nlat="+nlat);
		bounds.setSouthWest(southWest);
		bounds.setNorthEast(northEast);
		
		List<Store> storeList = storeService.getGeoStoreList(clientId, bounds,zoom);
		log.info("loadDistributeMarker size: "+storeList.size());
		return storeList;
	}
	
	@RequestMapping(value = "/loadStoreList")
	public Object loadStoreList(Model model,String storeNo,String storeId,String structureId,Integer page){
		page = page==null?1:page;
		int _start = (page-1)*8;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", super.getClientId());
		params.put("storeId", storeId);
		params.put("storeNo", storeNo);
		params.put("structureId", structureId);
		params.put("_start", _start);
		params.put("_size", 8);
		Integer items = storeService.loadStoreListItems(params);
		List<Store> stores = storeService.loadStoreList(params);
		Integer allPages = Page.getPageCount(items, 8);
		model.addAttribute("page", page);
		model.addAttribute("allPages", allPages);
		model.addAttribute("stores", stores);
		return "/base/mapStoreList";
	}
	
	/**
	 * 获取门店详细信息
	 * @param storeId
	 * @return
	 */
	@RequestMapping(value="/getStoreInfo/{storeId}",produces="application/json")
	@ResponseBody
	public Store getStoreInfo(@PathVariable("storeId")String storeId){
		Store store = storeService.selectByPrimaryKey(storeId);
		return store;
	}
	
	
	/**门店-分布情况
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/distributeX")
	public String distributeX(Model model,String datepicker,String projectName,String cityName,String cityId) throws BusinessException{
		int clientId = getClientId ();
		List<Store> stroeList = storeService.getStoreCoordinatesList(clientId);
		model.addAttribute("clientId", clientId);
		model.addAttribute("stroeList", stroeList);
		
		/*int city  = RandomUtil.random(1000, 9999);
		int store = RandomUtil.random(1000, 9999);
		int promoter = RandomUtil.random(1000, 9999);
		int project = RandomUtil.random(1000, 9999);
		
		model.addAttribute("city",city);
		model.addAttribute("store",store);
		model.addAttribute("promoter",promoter);
		model.addAttribute("project",project);*/
		if(StringUtil.isEmptyString(datepicker)){
			Date date = DateTimeUtils.getCurrentDate();
			String d = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd);
			datepicker = d;
		}
		
		/*if(StringUtil.isEmptyString(projectName)){
			projectName = "1";
		}
		
		if(StringUtil.isEmptyString(cityId)){
			cityId = "321";
		}
		if(StringUtil.isEmptyString(cityName)){
			cityName = "上海";
		}*/
		
		model.addAttribute("cityName",cityName);
		model.addAttribute("cityId",cityId);
		model.addAttribute("projectName",projectName);
		model.addAttribute("datepicker",datepicker);
		return "base/showStoreDistributeX";
	}
	
	/**
	 * 显示KPI信息
	 * @return
	 */
	@RequestMapping(value = "/showKPIList")
	public String showKPIList(Model model,String datepicker,String projectName,String cityName,String cityId){
		if(StringUtil.isEmptyString(datepicker)){
			Date date = DateTimeUtils.getCurrentDate();
			String d = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd);
			datepicker = d;
		}
		
		if(StringUtil.isEmptyString(cityId)){
			cityId = "321";
		}
		if(StringUtil.isEmptyString(cityName)){
			cityName = "上海";
		}
		if(StringUtil.isEmptyString(projectName)){
			projectName = "1";
		}
		
		model.addAttribute("cityName",cityName);
		model.addAttribute("cityId",cityId);
		model.addAttribute("projectName",projectName);
		model.addAttribute("datepicker",datepicker);
		return "base/showKPIList";
	}
	
	
	/**门店-分布情况
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showKPI")
	public String showKPI(Model model,String datepicker,String projectName,String cityName,String cityId,String KPI) throws BusinessException{
		if(StringUtil.isEmptyString(datepicker)){
			Date date = DateTimeUtils.getCurrentDate();
			String d = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd);
			datepicker = d;
		}
		
		if(StringUtil.isEmptyString(cityId)){
			cityId = "321";
		}
		if(StringUtil.isEmptyString(cityName)){
			cityName = "上海";
		}
		if(StringUtil.isEmptyString(projectName)){
			projectName = "1";
		}
		
		if(StringUtil.isEmptyString(KPI)){
			KPI = "4";
		}
		
		model.addAttribute("cityName",cityName);
		model.addAttribute("cityId",cityId);
		model.addAttribute("projectName",projectName);
		model.addAttribute("datepicker",datepicker);
		model.addAttribute("KPI",KPI);
		return "base/showKPI";
	}
	

	/**门店管理-新增门店 (查询出所有相关联的值,比如渠道等)
	 * @author Nany
	 * 2014年11月26日上午10:15:11
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddStore")
	public String showAddStore(Model model, String mod) throws BusinessException{
		String reFtl = "base/showAddStore";
		int clientId = getClientId ();
		if(mod !=null && mod.equals(Constants.DATA_MOD_CONF)) {
			List<ClientBusinessDefinition> addList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_STORE, ChannelEnum.PAGE_TPYE.ADD.getKey(), clientId);
			model.addAttribute("addList", addList);
			reFtl = "base/autoShowAddStore";
		}
		model.addAttribute("clientId", clientId);
			
		return reFtl;
	}
	
	/**
	  * 门店管理-新增页面-保存
	  */
	@RequestMapping(value = "/addStore", produces="application/json")
	@ResponseBody
	public Object addStore(Store store) throws BusinessException{
		Byte status = store.getStatus();
		/**服务端验证-门店关闭的情况下是否还有人店对应关系**/
		if(status != null && status.equals(Constants.STORE_STATUS_WX)){
			if(StringUtil.isNotEmptyString(store.getBusinessman()) || StringUtil.isNotEmptyString(store.getPromotions())){
				return ResultMessage.STORE_USER_MAPPING_EXITE_FAIL;
			}
		}
		storeService.addStore(store);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	
	/**批量更换业务员
	 * @author Nany
	 * 2015年1月7日上午9:24:59
	 * @param clientUserId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showBatchClientUser")
	public String showBatchClientUser(Integer page,StoreSearchVO storeSearchVO,Model model, HttpServletRequest req) {
		Integer clientStructureId = storeSearchVO.getClientStructureId () != null ? storeSearchVO.getClientStructureId () : getClientStructureId();
		model.addAttribute("clientStructureId", clientStructureId);
		return "/base/showBatchClientUser";
	}
	
	/**
	 * 业务代表批量选择门店
	 */	
	@RequestMapping(value="/showBatchChoiceStore")
	public String batchChoiceStore(StoreSearchVO storeSearchVO,Model model) {
		Integer clientStructureId = storeSearchVO.getClientStructureId () != null ? storeSearchVO.getClientStructureId () : getClientStructureId();
		Integer clientUserId = storeSearchVO.getClientUserId() != null ? storeSearchVO.getClientUserId () : null;
		model.addAttribute("clientStructureId", clientStructureId);	
		model.addAttribute("clientUserId", clientUserId);	
		return "/base/showBatchChoiceStore";
	}
	
	@RequestMapping(value="/updateClientUser")
	@ResponseBody
	public Object updateClientUser(String hiddenStoreId,Integer clientUserId2,Integer clientUserId) {
		Integer clientId = getClientId();
		//storeUserMappingService.updateClientUser(clientId,clientUserId,clientUserId2);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("storeIds", hiddenStoreId);
		params.put("clientUserId", clientUserId);
		storeUserMappingService.deleteUserAndStoreMapping(params);
		String[] str=(hiddenStoreId != null && hiddenStoreId != "")? hiddenStoreId.split(","):null;
		if(str != null){
			storeUserMappingService.batchUpdateStoreUserMapping(str,clientUserId2,clientId);	
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="/batchChoiceStoreUser")
	@ResponseBody
	public Object batchStoreUserUpdate(String hiddenStoreId,String oldStoreIdStrs,Integer clientUserId){
		storeUserMappingService.updateStoreUser(hiddenStoreId,oldStoreIdStrs,clientUserId,getClientId());
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	@RequestMapping(value="getBatchCheckStoreUser")
	@ResponseBody
	public String getBatchCheckStoreUser(Integer clientStructureId,Integer clientUserId,Model model){
		clientStructureId = clientStructureId != null ? clientStructureId : getClientStructureId();
		clientUserId = clientUserId !=null ? clientUserId : getCurrentUserId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){			
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(clientUserId);
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);	
		Map<String,Object> params = new HashMap<String,Object>();	
		params.put("clientId", getClientId());
		params.put("clientUserId", clientUserId);
		String storeIds = storeService.getBatchCheckStoreUser(params);	
		if(StringUtils.isEmpty(storeIds)){
			storeIds="";
		}
		return storeIds;
	}

	
	@RequestMapping(value="/updateStorePosition")
	@ResponseBody
	public Object updateStoreAddr() {
		int clientId = getClientId();
		List<Store> storeList = storeService.findNoAddrStore(clientId);
		int i = 0, j = 0, all = 0;
		ObjectMapper mapper = new ObjectMapper();
		for (Store store : storeList) {
			all++;
			log.debug(all+"/"+storeList.size());
			String result = request(store.getAddr());
			if(!StringUtils.isEmpty(result) && result.contains("location")){
				try {
					BaiduLocationJson blj = mapper.readValue(result, BaiduLocationJson.class);
					if(blj.getResult() != null && blj.getResult().getLocation() != null &&  blj.getResult().getLocation().getLng() != null){
						i++;
						store.setLongitude(blj.getResult().getLocation().getLng().toString());
						store.setLatitude(blj.getResult().getLocation().getLat().toString());
						storeService.updateStoreInfo(store);
					}else
						j++;
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.debug("Sucess="+i+",Fail="+j);
		return null;
	}
	
	private String request(String addr) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String content = null;
		try {
			String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=D93a31278160293ed962f7a4c40f1fa5", addr);
			// 创建 httpUriRequest 实例
			HttpGet httpGet = new HttpGet(url);
			// 执行 get 请求
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// 获取响应实体
			HttpEntity httpEntity = httpResponse.getEntity();
			// 打印响应状态
			//System.out.println(httpResponse.getStatusLine());
			if (httpEntity != null) {
				// 响应内容的长度
				long length = httpEntity.getContentLength();
				// 响应内容
				content = EntityUtils.toString(httpEntity);
				
//				System.out.println(addr);
//				System.out.println("Response content:" + content);
			}
			// 有些教程里没有下面这行
			httpGet.abort();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接，释放资源
			httpClient.getConnectionManager().shutdown();
		}
		return content;
	}
	/**查看门店详细信息
	 * @author Nany
	 * 2015年2月2日下午7:00:13
	 * @param storeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showStoreDetail/{storeId}")
	private String showStoreDetail(@PathVariable("storeId")String storeId,Model model,String mod) {
		int clientId = getClientId();
		Store store = storeService.findStoreByStoreId(storeId);
		String reFtl = "/base/showStoreDetail";
		if(mod !=null && mod.equals(Constants.DATA_MOD_CONF)) {
			List<ClientBusinessDefinition> seeList = channelCommService.getModBusinessDefinition(Constants.TABLENAME_STORE, ChannelEnum.PAGE_TPYE.SEE.getKey(), clientId);
			model.addAttribute("seeList", seeList);
			reFtl = "base/autoShowStoreDetail";
		}
		/**获取门店关联的userId**/
		List<StoreUserMapping> sumList= storeUserMappingService.getStoreUserMappingByStoreId(clientId,storeId);
		
		/**获取省份信息**/
		Province province = null;
		if (store.getProvinceId() != null) {
			province = provinceService.getPrivinceById(store.getProvinceId());
		}
		/**获取城市信息**/
		City city = null;
		if (store.getCityId() != null) {
			city = cityService.getCityById(store.getCityId());
		}
		/**获取区县信息**/
		District district = null;
		if(store.getDistributorId() != null){
			district = districtService.getDistrictById(store.getDistrictId());
		}
		
		/**终端状态(关联options表,option_name=store_status)**/
		List<Options> optionsList = optionsService.findOptionsByOptionName(Constants.OPTION_STORE_NAME, clientId);
		
		/**获取管理渠道(门店类型)**/
		List<Options> storeTypeList = optionsService.findOptionsByOptionName("store_type", clientId);
		
		/**门店分组**/
		List<StoreGroup> storeGroupList = storeGroupService.getObjectList(getBaseParams() );
		
		model.addAttribute("store", store);
		model.addAttribute("optionsList",optionsList);
		model.addAttribute("storeGroupList", storeGroupList);
		model.addAttribute("storeTypeList", storeTypeList);
		model.addAttribute("sumList", sumList);
		model.addAttribute("province", province);
		model.addAttribute("city", city);
		model.addAttribute("district", district);
		return reFtl;
	}
	
	/**门店数据导出
	 * @author Nany
	 * 2015年2月3日下午7:06:04
	 * @param param
	 * @param structureName
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value="/exportStore")
	//var param = "structureId="+structureId+",storeNo="+storeNo+",storeName="+storeName+",clientUserId="+clientUserId+",channelId="+channelId+",distributorId="+distributorId;
	public void exportStore(StoreSearchVO storeSearchVO,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Integer structureId = storeSearchVO.getClientStructureId() == null?getClientStructureId():storeSearchVO.getClientStructureId ();
		String storeNo = storeSearchVO.getStoreNo();
		String storeName = storeSearchVO.getStoreName();
		Integer clientUserId = storeSearchVO.getClientUserId();
		Integer channelId = storeSearchVO.getChannelId();
		Integer distributorId = storeSearchVO.getDistributorId();

		int clientId = getClientId();
		/**获取组织架构级别该部门的所有子部门**/
		String subAllStructureId = channelCommService.getSubStructrueIds(structureId);
		/**subStructureId 从权限中获取所有部门","号隔开**/
		String subStructureId = getClientUser().getPermissionsData();
		if(subStructureId == null || subStructureId == "" ){
			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**deptIds 通过页面传过来的部门(搜索列表选定的部门条件)获取该部门下所有符合权限的部门，是subStructureId的子集
		 * 从subAllStructureId中找出存在subStructureId中的部门
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
		if(!StringUtil.isEmptyString(deptIds) && deptIds.endsWith(",")){
			deptIds = deptIds.substring(0, deptIds.length()-1);
		}
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		if(!StringUtil.isEmptyString(subordinates) && subordinates.endsWith(",")){
			subordinates = subordinates.substring(0, subordinates.length()-1);
		}
		String channelIds = channelService.findChannelChild(channelId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("clientId", clientId);
		parameters.put("storeName", storeName);
		parameters.put("storeNo", storeNo);
		parameters.put("channelId", channelIds);
		parameters.put("clientUserId", clientUserId);
		parameters.put("distributorId", distributorId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		parameters.put("storeType", Constants.STORE_TYPE);
		
		List<Store> storelist = storeService.queryForListForReport(parameters);
		//门店状态
		if (storelist != null && storelist.size() > 0) {
			for (Store store : storelist) {
				String storeStatusText = null;
				if (store != null) {
					Byte status = store.getStatus();
					storeStatusText = optionsService.getOptionText(Constants.OPTION_STORE_NAME,status,clientId);
					if (storeStatusText != null) {
						store.setStoreStatusText(storeStatusText);
					}
				}
			}
		}
		//门店分组
		if (storelist != null && storelist.size() > 0) {
			for (Store store : storelist) {
				if (store != null) {
					Integer  storeGroupId = store.getStoreGroupId();
					StoreGroup storeGroup = storeGroupService.getStoreGroupByStoreGroupId(storeGroupId);
					if (storeGroup != null) {
						store.setStoreGroupName(storeGroup.getGroupName());
					}
				}
			}
		}
		//门店省市县
		if (storelist != null && storelist.size() > 0) {
			for (Store store : storelist) {
				if (store != null) {
					Province province = provinceService.getPrivinceById(store.getProvinceId());
					if(province != null){
						store.setProvinceName(province.getProvince());
					}
					City city = cityService.getCityById(store.getCityId());
					if(city != null){
						store.setCityName(city.getCity());
					}
					District district = districtService.getDistrictById(store.getDistrictId());
					if(district != null){
						store.setDistrictName(district.getDistrict());
					}
				}
			}
		}
		//业务员拼接职位信息
//		if (storelist != null && storelist.size() > 0) {
//			for (Store store : storelist) {
//					String storeId = store.getStoreId();
//					String name = "";
//					String namePosition = null;
//					List<StoreUserMapping> storeUserMappingList = storeUserMappingService.getStoreUserMappingByStoreId(clientId, storeId);
//					if (storeUserMappingList != null && storeUserMappingList.size() > 0) {
//						for (StoreUserMapping storeUserMapping : storeUserMappingList) {
//							Integer clientUserId1 = storeUserMapping.getClientUserId();
//							ClientUser clientUser = clientUserService.getClientUser(clientUserId1, clientId);
//							if (clientUser != null) {
//								name += clientUser.getName()+",";
//							}
//						}
//					}
//					if (name.length() > 0) {
//						namePosition = name.substring(0, name.length()-1);
//					}
//					store.setName(namePosition);
//			}
//		}
		
		
		ClientStructure clientStructure = clientStructureService.getClientStructureById(structureId);
		String structureName = null;
		if(clientStructure != null){
			structureName = clientStructure.getStructureName();
		}
		/**创建一个Excel文件*/
		HSSFWorkbook  wb = new HSSFWorkbook();
		/**导出Excel文档名字*/
		String excelName = structureName+"--门店信息"+"_"+System.currentTimeMillis();
		resp.addHeader("Content-Disposition", "attachment;filename="+new String(excelName.getBytes("gb2312"), "iso8859-1")+".xls");
		resp.setContentType("application/vnd.ms-excel");
		
		/**在we中创建一个sheet*/
		HSSFSheet wTSSheet = wb.createSheet("门店信息");
		/**在wTSSheet值添加表头(第0行)*/
		HSSFRow row = wTSSheet.createRow((int)0);
		/**创建单元格，并设置表头，设置表头居中,背景颜色*/
		HSSFCellStyle style = this.createHeaderStyle(wb);
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFCell cell = row.createCell(0);  
		cell.setCellStyle(style);
		cell.setCellValue("门店编号");
		cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue("门店名称");
		
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("门店自编号");
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("门店分组");
		
		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue("所属渠道");
		cell = row.createCell(5);
		cell.setCellStyle(style);
		cell.setCellValue("所属经销商");
		cell = row.createCell(6);
		cell.setCellStyle(style);
		cell.setCellValue("所属部门");
		cell = row.createCell(7);
		cell.setCellStyle(style);
		cell.setCellValue("所属连锁");
		cell = row.createCell(8);
		cell.setCellStyle(style);
		cell.setCellValue("门店状态");
		cell = row.createCell(9);
		cell.setCellStyle(style);
		cell.setCellValue("一级渠道");
		cell = row.createCell(10);
		cell.setCellStyle(style);
		cell.setCellValue("管理渠道");
		cell = row.createCell(11);
		cell.setCellStyle(style);
		cell.setCellValue("联系人");
		cell = row.createCell(12);
		cell.setCellStyle(style);
		cell.setCellValue("手机");
		cell = row.createCell(13);
		cell.setCellStyle(style);
		cell.setCellValue("联系电话");
		cell = row.createCell(14);
		cell.setCellStyle(style);
		cell.setCellValue("传真");
		cell = row.createCell(15);
		cell.setCellStyle(style);
		cell.setCellValue("促销员/业务代表/主管");
		cell = row.createCell(16);
		cell.setCellStyle(style);
		cell.setCellValue("地址");
		cell = row.createCell(17);
		cell.setCellStyle(style);
		cell.setCellValue("所在省");
		cell = row.createCell(18);
		cell.setCellStyle(style);
		cell.setCellValue("所在市");
		cell = row.createCell(19);
		cell.setCellStyle(style);
		cell.setCellValue("所在县");
		cell = row.createCell(20);
		cell.setCellStyle(style);
		cell.setCellValue("备注信息");
		if (storelist != null && storelist.size() >0) {
			for (int i = 0; i < storelist.size(); i++) {
				row = wTSSheet.createRow((int) i + 1); 
				Store store = storelist.get(i);
				
				row.createCell(0).setCellValue(store.getStoreNo() == null?"":store.getStoreNo());
				row.createCell(1).setCellValue(store.getStoreName() == null?"":store.getStoreName());
				row.createCell(2).setCellValue(store.getExternalNo() == null?"":store.getExternalNo());
				row.createCell(3).setCellValue(store.getStoreGroupName() == null?"":store.getStoreGroupName());
				row.createCell(4).setCellValue(store.getChannelName() == null?"":store.getChannelName());
				row.createCell(5).setCellValue(store.getDistributorName() == null?"":store.getDistributorName());
				row.createCell(6).setCellValue(store.getStructureName() == null?"":store.getStructureName());
				row.createCell(7).setCellValue(store.getChainName() == null?"":store.getChainName());
				row.createCell(8).setCellValue(store.getStoreStatusText() == null?"":store.getStoreStatusText());
				row.createCell(9).setCellValue(store.getUpChannelName() == null?"":store.getUpChannelName());
				row.createCell(10).setCellValue(store.getStoreTypeName() == null?"":store.getStoreTypeName());
				row.createCell(11).setCellValue(store.getContact() == null?"":store.getContact());
				row.createCell(12).setCellValue(store.getMobileNo() == null?"":store.getMobileNo());
				row.createCell(13).setCellValue(store.getTelephoneNo() == null?"":store.getTelephoneNo());
				row.createCell(14).setCellValue(store.getFax() == null?"":store.getFax());
				row.createCell(15).setCellValue(store.getNames() == null?"":store.getNames());
				row.createCell(16).setCellValue(store.getAddr() == null?"":store.getAddr());
				row.createCell(17).setCellValue(store.getProvinceName() == null?"":store.getProvinceName());
				row.createCell(18).setCellValue(store.getCityName() == null?"":store.getCityName());
				row.createCell(19).setCellValue(store.getDistrictName() == null?"":store.getDistrictName());
				
				row.createCell(20).setCellValue(store.getRemark() == null?"":store.getRemark());
			}
			
		}
		try {
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 门店嵌入页面
	 * @param page
	 * @param storeSearchVO
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addBatchClientUser")
	public String addBatchClientUser(Integer page,String storeName,Integer clientStructureId,Integer clientUserId, Model model, HttpServletRequest req){
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
		
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("storeName", storeName);
		parameters.put("clientUserId", clientUserId);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		int count = storeService.replaceStore(parameters);
		
		int pagenum = page == null ? 1 : page;
		Page<Store> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Store> list = storeService.replaceStoreList(parameters);
		pageParam.setItems(list);
		model.addAttribute("count", count);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/batchClientUser";
	}
	
	/**
	 * 门店嵌入页面
	 * @param page
	 * @param storeSearchVO
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addBatchChoiceStore")
	public String addBatchChoiceStore(Integer page,Integer clientStructureId,Integer clientUserId,String isChecked,String storeNo,String storeName,Model model, HttpServletRequest req){
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
		
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		parameters.put("storeNo", storeNo);
		parameters.put("storeName", storeName);
		int count = 0;
		if(StringUtils.isNotEmpty(isChecked)){
			count = storeService.getBatchUnStoreUserCount(parameters);
		}else{
			count = storeService.batchChoiceStoreUserStore(parameters);	
		}
		int pagenum = page == null ? 1 : page;
		Page<Store> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize ());
		List<Store> list = null;
		if(StringUtils.isNotEmpty(isChecked)){
			list = storeService.getBatchUnStoreUser(parameters);
		}else{
			list = storeService.batchChoiceStoreUserList(parameters);
		}
		int checkCount = 0;
		if(StringUtils.isEmpty(isChecked)){
			for(Store store : list){
				if(store.getIsChecked()==1){
					checkCount++;
				}
			}
		}
		model.addAttribute("checkCount", checkCount);
		pageParam.setItems(list);
		model.addAttribute("count", count);
		model.addAttribute("isChecked", isChecked);
		model.addAttribute("clientStructureId", clientStructureId);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/base/batchChoiceStore";
	}
	
	@RequestMapping(value="getBatchStore")
	@ResponseBody
	public Map<String,Object> getBatchStore(Integer clientStructureId,Integer clientUserId,String hiddenStoreId,String isChecked,Model model){
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
		
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("clientUserId", clientUserId);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		parameters.put("isChecked", isChecked);
		String storeIds="";
		if(StringUtils.isNotEmpty(isChecked)){
			storeIds = storeService.getBatchStoreIdsUnStoreUser(parameters);
		}else{
			storeIds = storeService.getBatchStore(parameters);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(hiddenStoreId)){
			//获取两个oldStoreIdStrs与storeIds的交集
			String subStoreIds2 =StringUtil.stringIntersect(hiddenStoreId,storeIds);
			if(StringUtils.isEmpty(subStoreIds2)){    //如果没有交集 
				hiddenStoreId = StringUtil.removeStrComma(hiddenStoreId);  
				storeIds = storeIds +","+hiddenStoreId;
				storeIds = StringUtil.removeStrComma(storeIds); 
			}else{
				//获取hiddenStoreId中存在的，而storeIds中不存在的Id
				String subStoreIds = ArrayUtil.arraySubtract2Str(hiddenStoreId.split(","),storeIds.split(","));
				subStoreIds = StringUtil.removeStrComma(subStoreIds); 
				String subStoreIds3 = ArrayUtil.arraySubtract2Str(storeIds.split(","),hiddenStoreId.split(","));
				subStoreIds3 = StringUtil.removeStrComma(subStoreIds3);  
				storeIds = subStoreIds3 +","+subStoreIds+","+subStoreIds2;
				storeIds = StringUtil.removeStrComma(storeIds); 
				map.put("storeIdsCheckAll",subStoreIds);
			}
		}
		map.put("storeIds",storeIds);
		return map;
	}
	
	/**
	 * 替换所有人员
	 * @param storeName
	 * @param clientStructureId
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value = "/replaceAlls")
	@ResponseBody
	public String replaceAlls(String storeName,Integer clientStructureId,Integer clientUserId){
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
		
		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("storeName", storeName);
		parameters.put("clientUserId", clientUserId);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		String strReplaceAlls=storeService.replaceAllClientUser(parameters);
		return strReplaceAlls;
	}
	
	/**
	 * 门店-状态
	 * @return
	 */
	@RequestMapping(value = "/getStatus")
	@ResponseBody
	public List<Options> getStatus(){
		Integer clientId = getClientId();
		/**终端状态(关联options表,option_name=store_status)**/
		List<Options> ls = optionsService.findOptionsByOptionName(Constants.OPTION_STORE_NAME, clientId);
		return ls;
	}

	/**
	 * 门店-管理渠道
	 * @return
	 */
	@RequestMapping(value = "/getStoreType")
	@ResponseBody
	public List<Options> getStoreType(){
		Integer clientId = getClientId();
		/**获取管理渠道(门店类型)**/
		List<Options> ls = optionsService.findOptionsByOptionName("store_type", clientId);
		return ls;
	}
	
	/**
	 * 门店-分组
	 * @return
	 */
	@RequestMapping(value = "/getStoreGroup")
	@ResponseBody
	public List<StoreGroup> getStoreGroup(){
		//门店分组
		List<StoreGroup> ls = storeGroupService.getObjectList(getBaseParams());
		return ls;
	}
	
	/**
	 * 门店关联
	 * @return
	 */
	@RequestMapping(value = "/initStoreMappingData",produces="application/json")
	@ResponseBody
	public List<StoreUserMapping> initStoreMappingData(String storeId, String initData){
		Integer clientId = getClientId();
//		String str = storeUserMappingService.initStoreMappingData(clientId, storeId, initData);
		/**获取门店关联的userId**/
		List<StoreUserMapping> sumList= storeUserMappingService.getStoreUserMappingByStoreId(clientId,storeId,initData);
		return sumList;
	}
	
	/**
	 * 门店定位
	 * @return
	 */
	@RequestMapping(value="saveStoreLocation")
	@ResponseBody
	public Object saveStoreLocation(String storeId,String longitude,String latitude){
		Store store = new Store();
		store.setStoreId(storeId);
		store.setLongitude(longitude);
		store.setLatitude(latitude);
		storeService.updateStoreInfo(store);
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}
	
	/**
	 * 获取门店信息
	 * @param storeName
	 * @return
	 */ 
	@RequestMapping(value = "getStoreInfoByName")
	@ResponseBody
	public List<Store> getStoreInfoByName(String q, Integer page){
		int clientId = getClientId (); 
		String storeName = q;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("storeName", storeName);
		List<Store> stores = storeService.getStoreInfoByName(parameters);
 		return stores;
	}
	
	@RequestMapping(value="/getStore/{storeId}",produces="application/json")
	@ResponseBody
	public Store getStore(@PathVariable("storeId")String storeId){
		Store store = storeService.getStore(storeId);
		return store;
	}
	
	@RequestMapping(value="/getStoreAjax", produces="application/json")
	@ResponseBody
	public List<Store> getStoreAjax(){
		Integer clientId = getClientId();
		List<Store> stores = storeService.findStoreByClientId(clientId);
		return stores;
	}
	
	
	/**
	 * 获取有任务的门店信息
	 * @param storeName
	 * @return
	 * @throws Exception 
	 */ 
	@RequestMapping(value = "getStoreByName")
	@ResponseBody
	public List<Store> getStoreByName(String q,Integer page) throws Exception{
		int clientId = getClientId (); 
		ClientUser clientUser = clientUserService.selectByPrimaryKey(getCurrentUserId());
		String subAllStructureId = channelCommService.getSubStructrueIds(clientUser.getClientStructureId());/**获取组织架构级别该部门的所有子部门**/
		String storeName = q;		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("storeName", storeName);
		parameters.put("subAllStructureId", subAllStructureId);
		List<Store> stores = storeService.getStoreByName(parameters);
 		return stores;
	}
	/**
	 * 加载门店编号
	 * @return
	 */
	@RequestMapping(value = "loadStoreToSelectTwo")
	@ResponseBody
	public  List<Store> loadStoreToSelectTwo(){
		int clientId = getClientId ();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		List<Store> storeList = storeService.loadStoreToSelectTwo(parameters);
		return storeList;
	}
	
	/**
	 * 高露洁导入门店
	 * @param fileField
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/importColgateStore")
	@ResponseBody
	public Object importColgateStore(MultipartFile fileField, HttpServletRequest req) throws Exception{
		Integer clientId = getClientId();
		Map<String,Object> map = importStoreService.validataColgateStore(fileField, clientId);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//错误提示文件导出
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		XSSFCell cell = null;
		int cellNum = 1;
		XSSFCell errCell = row.createCell(0);  
		errCell.setCellValue("错误提示");
		for (String title : ImportConstants.STORE_COLGATE_TITLE) {
			cell = row.createCell(cellNum);
			cell.setCellValue(title);
			cellNum++;
		}
		List<String[]> errDataList = (List<String[]>) map.get("errDataList");
		List<String> errStrList = (List<String>) map.get("errStrList");
		List<Store> successList = (List<Store>) map.get("successList");
		
		if (errDataList != null && errDataList.size() >0) {
			for (int i = 0; i < errDataList.size(); i++) {
				row = sheet.createRow((int) i + 1); 
				String[] cellValues = errDataList.get(i);
				for (int j = 0;j<cellValues.length+1; j++) {	
					  XSSFCell cellData = row.createCell(j);
					  if(j == 0){   								//批注加在第一列
						  cellData.setCellValue(errStrList.get(i));
					  }else{							  
						  cellData.setCellValue(cellValues[j-1]);   //第一列是错误批注   j-1 
					  }										  
				}													 
			}
			try {
				  /**导出Excel文档名字*/
				 String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);		
				  File fileMkdir = new File(errDataExcelPath+getClientId());
				  if(!fileMkdir.exists()){
					  fileMkdir.mkdir();
				  }
				  String excelName = "errDataStoreExcel"+"_"+System.currentTimeMillis()+".xlsx";
				  returnMap.put("errDataExcelPath",excelName);
				  File file = new File(fileMkdir.getPath()+File.separator+excelName);		   
				  FileOutputStream fos = new FileOutputStream(file);			  
				  wb.write(fos);
				  fos.flush();
				  fos.close();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else{
			returnMap.put("errDataExcelPath","");
		}
		if(map.containsKey("UnknownColumnName")){
			returnMap.put("UnknownColumnName",map.get("UnknownColumnName"));
		}
		returnMap.put("successCount",successList.size());
		returnMap.put("errorCount", errStrList.size());	
		return returnMap;
	}
	
	/**
	 * 苹果门店导入
	 * @param fileField
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/importAppleStore")
	@ResponseBody
	public Object importAppleStore(MultipartFile fileField, HttpServletRequest req) throws Exception{
		Integer clientId = getClientId();
		Map<String,Object> map = importStoreService.validataAppleStore(fileField, clientId);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//错误提示文件导出
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		XSSFCell cell = null;
		int cellNum = 1;
		XSSFCell errCell = row.createCell(0);  
		errCell.setCellValue("错误提示");
		for (String title : ImportConstants.STORE_COLGATE_TITLE) {
			cell = row.createCell(cellNum);
			cell.setCellValue(title);
			cellNum++;
		}
		List<String[]> errDataList = (List<String[]>) map.get("errDataList");
		List<String> errStrList = (List<String>) map.get("errStrList");
		List<Store> successList = (List<Store>) map.get("successList");
		
		if (errDataList != null && errDataList.size() >0) {
			for (int i = 0; i < errDataList.size(); i++) {
				row = sheet.createRow((int) i + 1); 
				String[] cellValues = errDataList.get(i);
				for (int j = 0;j<cellValues.length+1; j++) {	
					  XSSFCell cellData = row.createCell(j);
					  if(j == 0){   								//批注加在第一列
						  cellData.setCellValue(errStrList.get(i));
					  }else{							  
						  cellData.setCellValue(cellValues[j-1]);   //第一列是错误批注   j-1 
					  }										  
				}													 
			}
			try {
				  /**导出Excel文档名字*/
				 String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);		
				  File fileMkdir = new File(errDataExcelPath+getClientId());
				  if(!fileMkdir.exists()){
					  fileMkdir.mkdir();
				  }
				  String excelName = "errDataStoreExcel"+"_"+System.currentTimeMillis()+".xlsx";
				  returnMap.put("errDataExcelPath",excelName);
				  File file = new File(fileMkdir.getPath()+File.separator+excelName);		   
				  FileOutputStream fos = new FileOutputStream(file);			  
				  wb.write(fos);
				  fos.flush();
				  fos.close();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else{
			returnMap.put("errDataExcelPath","");
		}
		if(map.containsKey("UnknownColumnName")){
			returnMap.put("UnknownColumnName",map.get("UnknownColumnName"));
		}
		returnMap.put("successCount",successList.size());
		returnMap.put("errorCount", errStrList.size());	
		return returnMap;
	}
	/**
	 * 百威英博门店
	 * @param fileField
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/importBwybStore")
	@ResponseBody
	public Object importBwybStore(MultipartFile fileField, HttpServletRequest req) throws Exception{
		Integer clientId = getClientId();
		Map<String,Object> map = importStoreService.validataBwybStore(fileField, clientId);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//错误提示文件导出
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		XSSFCell cell = null;
		int cellNum = 1;
		XSSFCell errCell = row.createCell(0);  
		errCell.setCellValue("错误提示");
		for (String title : ImportConstants.STORE_BWYB_TITLE) {
			cell = row.createCell(cellNum);
			cell.setCellValue(title);
			cellNum++;
		}
		List<String[]> errDataList = (List<String[]>) map.get("errDataList");
		List<String> errStrList = (List<String>) map.get("errStrList");
		List<Store> successList = (List<Store>) map.get("successList");
		
		if (errDataList != null && errDataList.size() >0) {
			for (int i = 0; i < errDataList.size(); i++) {
				row = sheet.createRow((int) i + 1); 
				String[] cellValues = errDataList.get(i);
				for (int j = 0;j<cellValues.length+1; j++) {	
					  XSSFCell cellData = row.createCell(j);
					  if(j == 0){   								//批注加在第一列
						  cellData.setCellValue(errStrList.get(i));
					  }else{							  
						  cellData.setCellValue(cellValues[j-1]);   //第一列是错误批注   j-1 
					  }										  
				}													 
			}
			try {
				  /**导出Excel文档名字*/
				 String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);		
				  File fileMkdir = new File(errDataExcelPath+getClientId());
				  if(!fileMkdir.exists()){
					  fileMkdir.mkdir();
				  }
				  String excelName = "errDataStoreExcel"+"_"+System.currentTimeMillis()+".xlsx";
				  returnMap.put("errDataExcelPath",excelName);
				  File file = new File(fileMkdir.getPath()+File.separator+excelName);		   
				  FileOutputStream fos = new FileOutputStream(file);			  
				  wb.write(fos);
				  fos.flush();
				  fos.close();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else{
			returnMap.put("errDataExcelPath","");
		}
		if(map.containsKey("UnknownColumnName")){
			returnMap.put("UnknownColumnName",map.get("UnknownColumnName"));
		}
		returnMap.put("successCount",successList.size());
		returnMap.put("errorCount", errStrList.size());	
		return returnMap;
	}
	
	@RequestMapping(value="/importWorkLogStore")
	@ResponseBody
	public Object importWorkLogStore(MultipartFile fileField, HttpServletRequest req)throws Exception{
		Integer clientId= getClientId();
		Map<String,Object> map = importStoreService.validataWorkStore(fileField, clientId);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//错误提示文件导出
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.AQUA.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		XSSFCell cell = null;
		int cellNum = 1;
		XSSFCell errCell = row.createCell(0);  
		errCell.setCellValue("错误提示");
		for (String title : ImportConstants.STORE_WORKLOG_TITLE) {
			cell = row.createCell(cellNum);
			cell.setCellValue(title);
			cellNum++;
		}
		List<String[]> errDataList = (List<String[]>) map.get("errDataList");
		List<String> errStrList = (List<String>) map.get("errStrList");
		List<Store> successList = (List<Store>) map.get("successList");
		
		if (errDataList != null && errDataList.size() >0) {
			for (int i = 0; i < errDataList.size(); i++) {
				row = sheet.createRow((int) i + 1); 
				String[] cellValues = errDataList.get(i);
				for (int j = 0;j<cellValues.length+1; j++) {	
					  XSSFCell cellData = row.createCell(j);
					  if(j == 0){   								//批注加在第一列
						  cellData.setCellValue(errStrList.get(i));
					  }else{							  
						  cellData.setCellValue(cellValues[j-1]);   //第一列是错误批注   j-1 
					  }										  
				}													 
			}
			try {
				  /**导出Excel文档名字*/
				 String errDataExcelPath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.ERRDATAEXCE_LPATH);		
				  File fileMkdir = new File(errDataExcelPath+getClientId());
				  if(!fileMkdir.exists()){
					  fileMkdir.mkdir();
				  }
				  String excelName = "errDataStoreExcel"+"_"+System.currentTimeMillis()+".xlsx";
				  returnMap.put("errDataExcelPath",excelName);
				  File file = new File(fileMkdir.getPath()+File.separator+excelName);		   
				  FileOutputStream fos = new FileOutputStream(file);			  
				  wb.write(fos);
				  fos.flush();
				  fos.close();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}else{
			returnMap.put("errDataExcelPath","");
		}
		if(map.containsKey("UnknownColumnName")){
			returnMap.put("UnknownColumnName",map.get("UnknownColumnName"));
		}
		returnMap.put("successCount",successList.size());
		returnMap.put("errorCount", errStrList.size());	
		return returnMap;
	}
	
	/**
	 * 设置表头样式
	 * 
	 * @param style
	 */
	private HSSFCellStyle createHeaderStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中

		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 13);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setFont(font);
		return style;
	}
	/**
	 * 费列罗导入
	 * @param file
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/importFerreroStore")
	@ResponseBody
	public  Object importFerreroStore(MultipartFile fileField, HttpServletRequest request,HttpServletResponse resp){
		int clientId = getClientId();
		if(fileField != null){
			Object rtMessage = storeService.addFerreroStore(fileField, request, resp, clientId);
			return rtMessage;
		}
		return null;
	}
	/**
	 * 门店是否成功访问(opiton_name=’success_or_not’)
	 * @return
	 */
	@RequestMapping(value="/getstoreOptionsType")
	@ResponseBody
	public List<Options> getStoreTypeByOptionName(){
		int clientId = getClientId();
		Map<String,Object> parmater = new HashMap<String,Object>();
		parmater.put("clientId", clientId);
		parmater.put("optionName", Constants.STORE_TYPE_OPTION);
		List<Options> optionlist =  optionsService.selectOptionsList(parmater);
		return optionlist;
	}
	@RequestMapping(value="/queryStoreListByNumber")
	@ResponseBody
	public List<Store> queryStoreListByNumber(String q,Integer page){
		int clientId = getClientId();
		Map<String,Object> parmater = new HashMap<String,Object>();
		Integer clientStructureId = super.getClientStructureId();
		String deptIds = super.getDeptIds(clientStructureId);
		parmater.put("clientId", clientId);
		parmater.put("storeNo", "%"+q+"%");
		parmater.put("clientStructureId", deptIds);
		parmater.put("isDelete", Constants.IS_DELETE_FALSE);
		List<Store> stores=storeService.queryStoreListByNumber(parmater);
		
		return stores;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStoresKeyValue",produces="application/json")
	public List<KeyValueVo> getStores(String name){
		List<Store> list = storeService.queryStoresByClientId(getClientId(),name);
		if(null != list && list.size() > 0){
			List<KeyValueVo> retList =new ArrayList<KeyValueVo>();
			for(Store store : list){
				retList.add(new KeyValueVo(store.getStoreName(),store.getStoreId()));
			}
			return retList;
		}
		return null;
	}
	
	
	
		/**
		 * 导入页面
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "/showimportStore")
		public String showimportStore(Model model){
			int clientId = getClientId();
			model.addAttribute("clientId", clientId);
			return "base/importStore";
		}
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "mixImport", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
		@ResponseBody
		public Object mixImport(MultipartFile fileField, HttpServletRequest request,HttpServletResponse response) throws Exception {
			Map<String,Object> message = new HashMap<String, Object>();
			try {
				if(null==fileField){
					message.put("errorMsg", "导入模板不能为空");
				}else{
					ExcelReader reader = new ExcelReader(fileField);
					List<String[]> values = reader.getAllData(0);
					//由于查询夸库了，不能在service层切换数据库，所有将此方法写在controller层
				 
					Map<String, ClientStructure> mapDept = clientStructureService.getClientStructureMapByName(super.getClientId());//根据部门名称获取部门信息
					Map<String, Chain> mapChain =  chainService.queryChainByclientId(super.getClientId());
					Map<String, Channel> mapChannel =  channelService.queryChannelByclientId(super.getClientId());
					Map<String,ClientUser> mapUser =clientUserService.getClientUserByClientName(super.getClientId()); 
					
					message = storeService.importstore(values,mapUser, mapDept,mapChannel, mapChain,super.getClientId());
					Integer id=super.getClientId();
				    List<String> errStrList = (List<String>) message.get("errStrList");
					List<String[]> errDataList = (List<String[]>) message.get("errDataList"); 
					if(null!=errDataList && !errDataList.isEmpty() && errDataList.size()>1){
						String excelName = "errUnicharmSalesExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
						reader.importResult(response, errDataList, errStrList, id, excelName);
						message.put("errDataExcelPath",excelName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.put("errorMsg", "导入失败");
			}finally{
				CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			}
			return  message;
		}
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "importHuishi", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
		@ResponseBody
		public Object importHuishi(MultipartFile fileField, HttpServletRequest request,HttpServletResponse response) throws Exception {
			Map<String,Object> message = new HashMap<String, Object>();
			try {
				if(null==fileField){
					message.put("errorMsg", "导入模板不能为空");
				}else{
					ExcelReader reader = new ExcelReader(fileField);
					List<String[]> values = reader.getAllData(0);
					//由于查询夸库了，不能在service层切换数据库，所有将此方法写在controller层
				 
					Map<String, ClientStructure> mapDept = clientStructureService.getClientStructureMapByName(super.getClientId());//根据部门名称获取部门信息
					Map<String, Chain> mapChain =  chainService.queryChainByclientId(super.getClientId());
					Map<String, Channel> mapChannel =  channelService.queryChannelByclientId(super.getClientId());
					Map<String,ClientUser> mapUser =clientUserService.getClientUserByClientName(super.getClientId()); 
					message = storeService.importHuishiStore(values,mapUser, mapDept,mapChannel, mapChain,super.getClientId());
					Integer id=super.getClientId();
				    List<String> errStrList = (List<String>) message.get("errStrList");
					List<String[]> errDataList = (List<String[]>) message.get("errDataList"); 
					if(null!=errDataList && !errDataList.isEmpty() && errDataList.size()>1){
						String excelName = "errUnicharmSalesExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
						reader.importResult(response, errDataList, errStrList, id, excelName);
						message.put("errDataExcelPath",excelName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.put("errorMsg", "导入失败");
			}finally{
				CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			}
			return  message;
	     }
	
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "importColgate2", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
		@ResponseBody
		public Object importColgate2(MultipartFile fileField, HttpServletRequest request,HttpServletResponse response) throws Exception {
			Map<String,Object> message = new HashMap<String, Object>();
			try {
				if(null==fileField){
					message.put("errorMsg", "导入模板不能为空");
				}else{
					ExcelReader reader = new ExcelReader(fileField);
					List<String[]> values = reader.getAllData(0);
					//由于查询夸库了，不能在service层切换数据库，所有将此方法写在controller层
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("isDelete", 0);
					Map<String, Province> mapProvince =provinceService.selectAllprovince(params1);
					Map<String, ClientStructure> mapDept = clientStructureService.getClientStructureMapByName(super.getClientId());//根据部门名称获取部门信息
					Map<String, Channel> mapChannel =  channelService.queryChannelByclientId(super.getClientId());
					Map<String,Options> mapOptionsType = optionsService.selectOptionsListbyType(super.getClientId());
					Map<String,Options> mapOptionsYue = optionsService.selectOptionsListbyYue(super.getClientId());
					
					message = storeService.importColgate2Store(values,mapProvince, mapDept,mapChannel, mapOptionsType,mapOptionsYue,super.getClientId());
					Integer id=super.getClientId();
				    List<String> errStrList = (List<String>) message.get("errStrList");
					List<String[]> errDataList = (List<String[]>) message.get("errDataList"); 
					if(null!=errDataList && !errDataList.isEmpty() && errDataList.size()>1){
						String excelName = "errUnicharmSalesExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
						reader.importResult(response, errDataList, errStrList, id, excelName);
						message.put("errDataExcelPath",excelName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.put("errorMsg", "导入失败");
			}finally{
				CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
			}
			return  message;
		}
		
		@RequestMapping(value = "getUserMappingStoreList")
		@ResponseBody
		public List<Store> getUserMappingStoreList(String q,Integer page){
			Map<String, Object> params = new HashMap<String, Object>();
			String subStructureId = super.getDeptIds(super.getClientStructureId());
			params.put("subStructureId", subStructureId);
			params.put("clientId", super.getClientId());
			params.put("storeNo", q);
			List<Store> stores = storeService.getUserMappingStoreList(params);
			return stores;
		}
		
}
