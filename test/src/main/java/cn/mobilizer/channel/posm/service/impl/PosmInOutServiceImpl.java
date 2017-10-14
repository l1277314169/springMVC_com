/**   
 * @Title: PosmInOutDaoServiceImpl.java 
 * @Package cn.mobilizer.channel.posm.service.impl 
 * @author 仪动信息技术（上海）有限公司
 * @date 2015年9月23日 下午4:18:57 
 * @version V1.0   
 */
package cn.mobilizer.channel.posm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.StoreDao;
import cn.mobilizer.channel.base.vo.StoreSearchVO;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.dao.PosmInOutDao;
import cn.mobilizer.channel.posm.dao.PromotionMaterialStockXDao;
import cn.mobilizer.channel.posm.dao.PromotionMaterialXDao;
import cn.mobilizer.channel.posm.dao.WarehouseDao;
import cn.mobilizer.channel.posm.po.PosmInOut;
import cn.mobilizer.channel.posm.po.PromotionMaterial;
import cn.mobilizer.channel.posm.po.PromotionMaterialStock;
import cn.mobilizer.channel.posm.service.MaterialStockService;
import cn.mobilizer.channel.posm.service.PosmInOutService;
import cn.mobilizer.channel.posm.service.PromotionMaterialStockService;
import cn.mobilizer.channel.posm.vo.PosmInOutExcelVo;
import cn.mobilizer.channel.posm.vo.WarehouseVo;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.ExcelReader;
import cn.mobilizer.channel.util.MobiStringUtils;
import cn.mobilizer.channel.visit.service.impl.CallPlanningServiceImpl;


/**
 * @ClassName: PosmInOutDaoServiceImpl
 * @author pengwei
 * @date 2015年9月23日 下午4:18:57
 * 
 */
@Service
public class PosmInOutServiceImpl implements PosmInOutService {
	


	private static final Log LOG = LogFactory.getLog(CallPlanningServiceImpl.class); 
	
	@Autowired
	private PosmInOutDao posmInOutDao;
	
	@Autowired
	private PromotionMaterialXDao promotionMaterialXDao;
	
	@Autowired
	private WarehouseDao warehouseDao;
	
	@Autowired
	private PromotionMaterialStockXDao promotionMaterialStockXDao;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private MaterialStockService promotionMaterialStockService;
	
	@Autowired
	private PromotionMaterialStockService materialStockService;

	@Override
	public Integer getPosmInOutCount(Map<String, Object> map) {
		return posmInOutDao.selectselectPInOutsCount(map);
	}

	@Override
	public List<PosmInOutExcelVo> getPosmInOuts(Map<String, Object> map) {
		return posmInOutDao.selectPInOuts(map);
	}

	
	/**
	 * 获取客户集合
	 * @param storeMap
	 * @return
	 * @author：wei.peng
	 * @date 2015年9月30日
	 */
	private Set<String> storeMapToSet(Map<String, StoreSearchVO> storeMap){
		if(null !=storeMap && storeMap.size() > 0){
			Set<String> set = new HashSet<String>();
			for (String key : storeMap.keySet()) {
				set.add(storeMap.get(key).getChannelName());
			}
			return set;
		}
		return null;
	}

	/**
	 * 查询导出
	 */
	@Override
	public List<PosmInOutExcelVo> selectPInOutExport(Map<String,Object> parameter)	throws BusinessException {
		List<PosmInOutExcelVo> list = null;
		try {
			list = posmInOutDao.selectPInOutExport(parameter);
		} catch (Exception e) {
			LOG.error("method exportCallPlanning error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	
	/**
	 * Excel 表头验证
	 * 
	 * @param titles
	 *            Excel表头
	 * @param verifyTitle
	 *            模板表头
	 * @return
	 */
	private ResultMessage titleVerify(String[] titles, String[] verifyTitle) {
		if (null != titles && verifyTitle != null && titles.length > 0
				&& verifyTitle.length > 0) {
			for (int i = 0; i < titles.length; i++) {
				if (StringUtil.isBlank(titles[i])) {
					ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
					rm.setMessage("列名不能为空");
					return rm;
				}
				if (!MobiStringUtils.contains(verifyTitle, titles[i])) {
					ResultMessage rm = ResultMessage.IMPORT_FAIL_RESULT;
					rm.setMessage("存在不能识别的列名：" + titles[i]);
					return rm;
				}
			}
		}
		return null;
	}

	/**
	 * 进出明细分页
	 * @param map
	 * @return
	 */
	@Override
	public Integer getPInOutListCount(Map<String, Object> map) {
		return posmInOutDao.selectPInOutListCount(map);
	}

	@Override
	public ResultMessage savePosmInOut(PosmInOutExcelVo excelVo,Integer materialId,Integer userId, Integer clientId,String stokRemark) {
		ResultMessage message = ResultMessage.WEB_OPERATE_FAIL;
			if(null == excelVo || null == excelVo.getWarehouseId() 
					|| null ==  excelVo.getQuantity()
					|| null ==  materialId){
				message = ResultMessage.ADD_FAIL_RESULT;
			}else{
				//原仓库
				PromotionMaterialStock materialStock = materialStockService.getPmsByClientIdAndMaterialIdAndWarehouseId(materialId, excelVo.getWarehouseId(), clientId);
				if(excelVo.getBillType() == 2){
					if(null != materialStock && (materialStock.getQuantity() - excelVo.getQuantity())>=0){
						if(!StringUtil.isBlank(excelVo.getStrStoreId())){//到门店
							materialStock.setQuantity(materialStock.getQuantity() - excelVo.getQuantity());
							if(materialStockService.findPromotionMaterialStock(materialStock)){
								excelVo.setQuantity( excelVo.getQuantity());
								if(savePosmInOut(excelVo.getPosmInOut(materialId, userId, clientId))){
									message = ResultMessage.WEB_OPERATE_SUCCESS;//成功
								}
							} 
						}else{//到仓库
							PromotionMaterialStock stock = materialStockService.getPmsByClientIdAndMaterialIdAndWarehouseId(materialId, excelVo.getOutWarehouseId(), clientId);

							if(null == excelVo.getBillType() && excelVo.getBillType() > 0){
								materialStock.setQuantity(materialStock.getQuantity() - excelVo.getQuantity());
								if(materialStockService.findPromotionMaterialStock(materialStock)){
									excelVo.setQuantity( excelVo.getQuantity());
									if(savePosmInOut(excelVo.getPosmInOut(materialId, userId, clientId))){
										message = ResultMessage.WEB_OPERATE_SUCCESS;//成功
									}
								}
							}else{
								materialStock.setQuantity(materialStock.getQuantity() - excelVo.getQuantity());
								stock = new PromotionMaterialStock();
								stock.setQuantity(excelVo.getQuantity());
								stock.setCreateBy(userId);
								stock.setClientId(clientId);
								stock.setMaterialId(materialStock.getMaterialId());
								stock.setWarehouseId(excelVo.getOutWarehouseId());
								materialStock.setLastUpdateBy(userId);
								stock.setLastUpdateBy(userId);
								if((true == materialStockService.findPromotionMaterialStock(materialStock))&&(true == materialStockService.savePromotionMaterialStock(stock))){
									if(savePosmInOut(excelVo.getPosmInOut(materialId, userId, clientId))){
										message = ResultMessage.WEB_OPERATE_SUCCESS;//成功
									}
								}
							}
						}
					}else{
						message = ResultMessage.POSM_MATERIAL_STOCK_ERROR;//成功
					}
				}else{//收进
					if(null != materialStock){
						materialStock.setQuantity(materialStock.getQuantity() + excelVo.getQuantity());
						if(materialStockService.findPromotionMaterialStock(materialStock)){
							excelVo.setQuantity( excelVo.getQuantity());
							if(savePosmInOut(excelVo.getPosmInOut(materialId, userId, clientId))){
								message = ResultMessage.WEB_OPERATE_SUCCESS;//成功
							}
						}
					}else{
						materialStock = new PromotionMaterialStock();
						materialStock.setMaterialId(materialId);
						materialStock.setQuantity(excelVo.getQuantity());
						materialStock.setWarehouseId(excelVo.getWarehouseId());
						materialStock.setClientId(clientId);
						materialStock.setRemark(stokRemark);
						materialStock.setCreateBy(userId);
						if(true == materialStockService.savePromotionMaterialStock(materialStock)){
							excelVo.setQuantity(excelVo.getQuantity());
							if(savePosmInOut(excelVo.getPosmInOut(materialId, userId, clientId))){
								message = ResultMessage.WEB_OPERATE_SUCCESS;//成功
							}
						}
					}
				}
			}
		return message;
	}

	@Override
	public boolean savePosmInOut(PosmInOut posmInOut) {
		if(null != posmInOut){
			if(null != posmInOut.getMaterialId() ){
				if(null != posmInOut.getWarehouseId()){
					if(null != posmInOut.getQuantity()){
						if(posmInOutDao.insertSelective(posmInOut) > 0){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean updataPosmInOut(PosmInOut posmInOut) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	@Override
	public ResultMessage deletePosmInOut(Integer inoutKey,Integer userId,Integer clientId,String remark) {
		ResultMessage message = ResultMessage.DELETE_FAIL_RESULT;
		PosmInOut posmInOut = getPosmInOutByKey(inoutKey);
		if(null != posmInOut){
			PromotionMaterialStock materialStock =materialStockService.getPmsByClientIdAndMaterialIdAndWarehouseId(posmInOut.getMaterialId(), posmInOut.getWarehouseId(), clientId);
			materialStock.setLastUpdateBy(userId);
			materialStock.setLastUpdateTime(new Date());
			materialStock.setRemark(remark);
			if(posmInOut.getBillType() == 1){//收进
				if(materialStock.getQuantity() - posmInOut.getQuantity() >= 0){
						materialStock.setQuantity(materialStock.getQuantity() - posmInOut.getQuantity());
						if(materialStockService.findPromotionMaterialStock(materialStock)){
							message = deletePosmInOut(userId, posmInOut,remark);//成功
						}
//					}
				}else{
					message = ResultMessage.POSM_MATERIAL_STOCK_ERROR; //失败，  库存不足
				}
			}
			if(posmInOut.getBillType() == 2){ //发出
				Integer quantity =  posmInOut.getQuantity();
				if(null != posmInOut.getReceivingWarehouseId() && posmInOut.getReceivingWarehouseId() > 0){
					//转到仓库
					PromotionMaterialStock stock =materialStockService.getPmsByClientIdAndMaterialIdAndWarehouseId(posmInOut.getMaterialId(), posmInOut.getReceivingWarehouseId(), clientId);
					stock.setLastUpdateBy(userId);
					stock.setLastUpdateTime(new Date());
					stock.setRemark(remark);
					if(stock.getQuantity() - quantity >= 0){
						stock.setQuantity(stock.getQuantity()- quantity);
						materialStock.setQuantity(materialStock.getQuantity() + quantity);
						if((materialStockService.findPromotionMaterialStock(materialStock) == true)&&( materialStockService.findPromotionMaterialStock(stock) == true)){
							message = deletePosmInOut(userId, posmInOut,remark);
						}
					}else{
						message = ResultMessage.POSM_MATERIAL_STOCK_ERROR;//库存不足 ，删除失败
					}
				}else{//发出到门店 、 直接添加回来
					materialStock.setQuantity(materialStock.getQuantity() + quantity);
					if(materialStockService.findPromotionMaterialStock(materialStock)){
						message = deletePosmInOut(userId, posmInOut,remark);//成功
					}
				}
			}
		}
		return message;
	}

	/**
	 * 类中使用
	 * @param userId
	 * @param posmInOut
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	private ResultMessage deletePosmInOut(Integer userId, PosmInOut posmInOut,String remark) {
		posmInOut.setLastUpdateBy(userId);
		posmInOut.setLastUpdateTime(new Date());
		posmInOut.setIsDelete((byte) 1);
		posmInOut.setRemark(remark);
		if(posmInOutDao.updateByPrimaryKeySelective(posmInOut) > 0){
			return ResultMessage.DELETE_SUCCESS_RESULT;//成功
		}else{
			return ResultMessage.DELETE_FAIL_RESULT;//失败
		}
	}

	@Override
	public PosmInOut getPosmInOutByKey(Integer key) {
		if(null != key && key > 0){
			return posmInOutDao.selectByPrimaryKey(key);
		}
		return null;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultMessage saveInputPosmInOuts(MultipartFile file,Integer clientId, Integer userId, HttpServletRequest request,HttpServletResponse response) {
		ExcelReader reader = new ExcelReader(file);
		List<String[]> tabel = reader.getAllData(0);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		Map<String, WarehouseVo> warehouseVos = warehouseDao.queryWarehouseMap(params,"warehouseNo");	// 仓库编号
		Map<String, StoreSearchVO> storeMap = storeDao.querStoreSearchVo(params,"storeNo"); 			// 门店编号
		Map<String,PromotionMaterial> materials = promotionMaterialXDao.queryPromotionMaterials(params,"codeName"); // 物料编号
		
		String key = DateUtil.getDateString("yyyyMMddhhmmsssss");		//流水号
		
		List<PosmInOut> insertPioList = new ArrayList<PosmInOut>();								//插入到数据库中的明细
		List<PromotionMaterialStock> outList = new ArrayList<PromotionMaterialStock>();	//插入到数据库中的库存
		List<PromotionMaterialStock> impList = new ArrayList<PromotionMaterialStock>();	//插入到数据库中的库存
		
		List<String[]> errorData = new ArrayList<String[]>();			//错误数据
		List<String> errorStr = new ArrayList<String>();				//错误提示信息
		String[] titles = tabel.get(0);
		errorData.add(titles);	
		ResultMessage resultMessage = titleVerify(titles,ImportConstants.POSM_IN_OUT_TITLE);
		if (null == resultMessage) {
			for (int i = 1; i < tabel.size(); i++) {
				String[] entity = tabel.get(i);
				//基本信息校验
				String basic = basicVerify(entity);
				if(null != basic){
					errorData.add(entity);
					errorStr.add(basic);
					continue;
				}
				//数据真实性校验
				String dataTrue = dataIsTrue( entity, warehouseVos, storeMap, materials);
				if(null != dataTrue){
					errorData.add(entity);
					errorStr.add(dataTrue);
					continue;
				}
				// 库存数量校验
				String quantity = quantityVerify(entity,materials,warehouseVos);
				if(null != quantity){
					errorData.add(entity);
					errorStr.add(quantity);
					continue;
				}
				PosmInOut inOut = getPosmInOut(entity, key, userId, clientId, warehouseVos, storeMap, materials);
				insertPioList.add(inOut);
				dataDispose(entity, outList,impList, warehouseVos, materials, userId, clientId);
			}
		} else {
			return resultMessage;
		}
		if( outList.size() > 0){
			promotionMaterialStockXDao.updataMaterialStocksByKey(outList);
		}
		if( impList.size() > 0 ){
			impList = clearList(impList);
			promotionMaterialStockService.updatePromotionMaterialStocks(impList, clientId, userId, "明细导入", "明细导入修改");
		}
		if( insertPioList.size() > 0 ){
			posmInOutDao.insertPosmInOuts(insertPioList);
		}
		Map<String, Object> resultMap= new HashMap<String, Object>();
		resultMap.put("errorCount", errorData.size() -1);
		resultMap.put("successCount", insertPioList.size());
		
		if (errorStr != null && !errorStr.isEmpty()) {
			String excelName = "errWorkLogExcel" + "_"+ System.currentTimeMillis() + ".xlsx";
			reader.importResult(response,errorData,errorStr,clientId,excelName);
			resultMap.put("errDataExcelPath", excelName);
		}
		ResultMessage rm = ResultMessage.IMPORT_SUCCESS_RESULT;
		rm.setAttributes(resultMap);
		return rm;
	}
	
	
	/**
	 * 基本信息校验
	 * @param entity
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月13日
	 */
	private String basicVerify(String[] entity){
		if(StringUtil.isBlank(entity[6]) || StringUtil.isBlank(entity[5])){//物料基本信息判断
			return "物料信息缺失";
		}
		if(!StringUtil.isBlank(entity[10])){
			try {
				Integer num = Integer.parseInt(entity[10].trim());
				if(num < 0){
					return "数量错误";
				}
			} catch (Exception e) {
				return "数量类型错误";
			}
		}else{
			return "数量不能为空";
		}
		if(!StringUtil.isBlank(entity[11])){
			if(DateUtil.isValidDate2(entity[11].trim(), '-') || DateUtil.isValidDate2(entity[11].trim(), '/')){
			}else{
				//时间格式错误
				return "时间格式错误";
			}
		}else{
			return "时间信息不能为空";
		}
		if(!StringUtil.isBlank(entity[8])){//收进发出判断
			if("收进".equals(entity[8]) || "发出".equals(entity[8])){
				if("收进".equals(entity[8])){
					if(StringUtil.isBlank(entity[2])){
						return "仓库编号不能为空";
					}
				}else{
					if(StringUtil.isBlank(entity[2])){
						return "仓库编号不能为空";
					}else{
						if((true == StringUtil.isBlank(entity[12]))&&(true == StringUtil.isBlank(entity[15]))){
							return "目标地冲突";
						}else if((false == StringUtil.isBlank(entity[12]))&&(false == StringUtil.isBlank(entity[15]))){
							return "目标地冲突";
						}
					}
				}
			}else{
				return "类型错误";
			}
		}else{
			return "类型信息不能为空";
		}
		return null;
	}
	/**
	 * 数据真实性校验
	 * @param entity
	 * @param warehouseVos
	 * @param storeMap
	 * @param materials
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月13日
	 */
	private String dataIsTrue(String[] entity,Map<String, WarehouseVo> warehouseVos,Map<String, StoreSearchVO> storeMap,Map<String,PromotionMaterial> materials){
		if("收进".equals(entity[8].trim()) || "1".equals(entity[8].trim())){
			if(!warehouseVos.keySet().contains(entity[2].trim())){
				return "仓库编号不存在";
			}
		}else{//发出
			if(!StringUtil.isBlank(entity[15])){
				if(!warehouseVos.keySet().contains(entity[15].trim())){
					return "目标仓库编号错误";
				}
			}
			if(!StringUtil.isBlank(entity[12])){
				if(!storeMap.keySet().contains(entity[12].trim())){
					return "门店编号错误";
				}
			}
		}
		for(String key : materials.keySet()){
			if(materials.get(key).getMaterialCode().equals(entity[6].trim())){
				return null;
			}
		}
		return "物料信息错误";
	}
	/**
	 * 库存判断
	 * @param entity		
	 * @param materials	物料信息
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月13日
	 */
	private String quantityVerify(String[] entity,Map<String,PromotionMaterial> materials,Map<String, WarehouseVo> warehouseVos){
		String ret = null;
		if("发出".equals(entity[8].trim())){
			for(String key : materials.keySet()){
				PromotionMaterial material = materials.get(key);
				if(material.getMaterialCode().equals(entity[6].trim())){
					if(material.getMaterialName().equals(entity[5].trim())){
						Integer quantity = Integer.parseInt(entity[10].trim());
//						if("发出".equals(entity[8].trim())){
							if(!StringUtil.isBlank(entity[2].trim())){		//收进  库到库
								if(material.getWarehouseCode().equals(entity[2].trim())){
									if((material.getQuantity() - quantity) < 0){
										ret = "库存数量不足";
									}else{
										material.setQuantity(material.getQuantity() - quantity);
										ret =  "true";
										break;
									}
								}else{
									ret =  "库存数量不足";
								}
							}
//						}
					}
				}
			}
		}
		return null == ret || "true".equals(ret)?null:ret;
	}
	
	
	/**
	 * 获取明细
	 * @param entity	
	 * @param key		流水号
	 * @param userId	用户编号
	 * @param clientId	客户编号
	 * @param warehouseVos	仓库集合
	 * @param storeMap	门店集合
	 * @param materials	物料集合
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月14日
	 */
	private PosmInOut getPosmInOut(String[] entity,String key ,Integer userId,Integer clientId,Map<String, WarehouseVo> warehouseVos,Map<String, StoreSearchVO> storeMap,Map<String,PromotionMaterial> materials){
		PosmInOut posmInOut = new PosmInOut();
		posmInOut.setCreateBy(userId);
		posmInOut.setClientId(clientId);
		posmInOut.setBatch(key);
		posmInOut.setHandler(StringUtil.isBlank(entity[16])?"":entity[16].trim());
		posmInOut.setRemark(entity[17].trim());
		posmInOut.setQuantity(Integer.parseInt(entity[10].trim()));
		posmInOut.setBillType((byte)(entity[8].trim().equals("收进")?1:2));
		if(-1 != entity[11].indexOf('-')){
			posmInOut.setInOutTime(DateUtil.toDate(entity[11], "yyyy-MM-dd"));
		}else{
			posmInOut.setInOutTime(DateUtil.toDate(entity[11], "yyyy/MM/dd"));
		}
		for(String mkey :materials.keySet()){
			PromotionMaterial material = materials.get(mkey);
			if(material.getMaterialName().equals(entity[5].trim()) && material.getMaterialCode().equals(entity[6].trim())){
				posmInOut.setMaterialId(material.getMaterialId());
				break;
			}
		}
		
		
		if(!StringUtil.isBlank(entity[2])){
			posmInOut.setWarehouseId(warehouseVos.get(entity[2].trim()).getWarehouseId());
		}
		if("发出".equals(entity[8].trim())){
			if(!StringUtil.isBlank(entity[15].trim())){
				posmInOut.setReceivingWarehouseId(warehouseVos.get(entity[15].trim()).getWarehouseId());
			}
			if(!StringUtil.isBlank(entity[12])){
				if(null != storeMap.get(entity[12])){
					posmInOut.setStoreId(storeMap.get(entity[12].trim()).getStrStoreId());
				}
			}
		}
		return posmInOut;
	}
	
	
	/**
	 * 
	 * @param entity
	 * @param outList		导出	（不用校验，可以直接扣除）
	 * @param impList		导入 （需要校验，有的修改，没有新增）
	 * @param warehouseMap
	 * @param materials
	 * @param userId
	 * @param clientId
	 * @author：wei.peng
	 * @date 2015年10月14日
	 */
	private void dataDispose(String[] entity,List<PromotionMaterialStock> outList,List<PromotionMaterialStock> impList ,
								Map<String, WarehouseVo> warehouseMap,Map<String,PromotionMaterial> materials,Integer userId,Integer clientId){
		if("收进".equals(entity[8].trim())){
			Integer materialId = null;
			for(String key : materials.keySet()){
				PromotionMaterial material = materials.get(key);
				if(material.getMaterialCode().equals(entity[6].trim()) && material.getMaterialName().equals(entity[5].trim())){//物料判断
					if(entity[2].trim().equals(material.getWarehouseCode())){
						PromotionMaterialStock stock = new PromotionMaterialStock();
						stock.setStockId(material.getStockId());
						stock.setWarehouseId(material.getWarehouseId());
						stock.setMaterialId(material.getMaterialId());
						stock.setQuantity(Integer.parseInt(entity[10].trim()));
						stock.setBillType((byte)1);
						impList.add(stock);
						materialId = null;
						break;
					}
					materialId = material.getMaterialId();
				}
			}
			if(null != materialId){
				PromotionMaterialStock stock = new PromotionMaterialStock();
				stock.setCreateBy(userId);
				stock.setQuantity(Integer.parseInt(entity[10].trim()));
				stock.setWarehouseId(warehouseMap.get(entity[2].trim()).getWarehouseId());
				stock.setMaterialId(materialId);
				stock.setBillType((byte)1);
				impList.add(stock);
			}
		}else{//发出
			for(String key : materials.keySet()){
				PromotionMaterial material = materials.get(key);
				if(material.getMaterialCode().equals(entity[6].trim()) && material.getMaterialName().equals(entity[5].trim())){//物料判断
					if(material.getWarehouseCode().equals(entity[2].trim())){
						PromotionMaterialStock stock = new PromotionMaterialStock();
						stock.setStockId(material.getStockId());
						stock.setWarehouseId(material.getWarehouseId());
						stock.setMaterialId(material.getMaterialId());
						stock.setQuantity(Integer.parseInt(entity[10].trim()));
						stock.setBillType((byte)2);
						outList.add(stock);
						if(StringUtil.isBlank(entity[12])){
							boolean isOut = true;
							for(String outkey : materials.keySet()){
								PromotionMaterial outmaterial = materials.get(outkey);
								if(outmaterial.getMaterialCode().equals(entity[6].trim()) && outmaterial.getMaterialName().equals(entity[5].trim())){//物料判断
									if(outmaterial.getWarehouseCode().equals(entity[15].trim())){
										PromotionMaterialStock stock2 = new PromotionMaterialStock();
										stock2.setWarehouseId(outmaterial.getWarehouseId());
										stock2.setMaterialId(material.getMaterialId());
										stock2.setStockId(material.getStockId());
										stock2.setQuantity(Integer.parseInt(entity[10].trim()));
										stock2.setBillType((byte)1);
										impList.add(stock2);
										isOut = false;
										break;
									}
								}
							}
							if(isOut){
								PromotionMaterialStock materialStock = new PromotionMaterialStock();
								materialStock.setCreateBy(userId);
								materialStock.setQuantity(Integer.parseInt(entity[10].trim()));
								materialStock.setWarehouseId(warehouseMap.get(entity[15].trim()).getWarehouseId());
								materialStock.setMaterialId(material.getMaterialId());
								materialStock.setBillType((byte)1);
								impList.add(materialStock);
							}
						}
						break;
					}
				}
			}
		}
	}
	
	private List<PromotionMaterialStock> clearList(List<PromotionMaterialStock> list){
		Map<String ,PromotionMaterialStock> map = new HashMap<String,PromotionMaterialStock>();
		for(PromotionMaterialStock materialStock:list){
			if(materialStock.getBillType() == 1){
				PromotionMaterialStock stock = map.get(materialStock.getWarehouseId()+""+materialStock.getMaterialId());
				if(null != stock){
					stock.setQuantity(stock.getQuantity() + materialStock.getQuantity());
				}else{
					map.put(materialStock.getWarehouseId()+""+materialStock.getMaterialId(), materialStock);
				}
			}
		}	
		return new ArrayList<PromotionMaterialStock>(map.values());
	}
	
}
