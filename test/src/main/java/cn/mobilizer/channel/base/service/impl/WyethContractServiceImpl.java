/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.WyethContractDao;
import cn.mobilizer.channel.base.dao.WyethContractDetailDao;
import cn.mobilizer.channel.base.dao.WyethContractFeedbackDao;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.WyethContract;
import cn.mobilizer.channel.base.po.WyethContractDetail;
import cn.mobilizer.channel.base.service.StoreService;
import cn.mobilizer.channel.base.service.WyethContractService;
import cn.mobilizer.channel.base.vo.ContractContent;
import cn.mobilizer.channel.base.vo.ContractFeedbackAppVo;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.base.vo.ContractVo;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.export.po.DataInfo;
import cn.mobilizer.channel.export.vo.DateVoSupport;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.ExcelReader;

@Service
public class WyethContractServiceImpl implements WyethContractService {
	@Autowired
	private StoreService storeService;
	@Autowired
	private WyethContractDao wyethContractDao;
	@Autowired
	private WyethContractDetailDao wyethContractDetailDao;
	@Autowired
	private WyethContractFeedbackDao wyethContractFeedbackDao;
	/**
	 * 协议导入
	 */
	@Override
	public Object addContract(HttpServletRequest request,MultipartFile fileField, Integer clientId,
			HttpServletResponse response, Integer currentUserId) throws BusinessException{
		ExcelReader Reader = new ExcelReader(fileField);
		/**将一个sheet里面数据分成多个批次导入：每次导入2000条数据*/
		Integer eachCount = 2000;
		Integer rowCount = Reader.getRowNum(0)+1;
		int num = rowCount%eachCount==0?rowCount/eachCount:rowCount/eachCount + 1;
		List<WyethContract> allWyethContractList = new ArrayList<WyethContract>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>();
		Map<String, Store> stores = storeService.queryAllStore(clientId);
		Map<String, Store> stores2= storeService.queryAllStoreKeyName(clientId);
		Map<String, Store> stores3= storeService.queryAllStoreName(clientId);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isDelete", Constants.IS_DELETE_FALSE);
		params.put("clientId", clientId);
		Map<String, WyethContract> WyethContracts = wyethContractDao
				.queryAll(params);
		//Map<String, WyethContract> WyethContracts2 = wyethContractDao
		//		.queryAllMap(params);
		
		
		Integer[] monthIds = { 201508, 201509, 201510, 201511, 201512,201601, 201602,
				201603, 201604, 201605, 201606, 201607 };
		for(int a = 0;a < num ; a++){
			List<WyethContractDetail> wyethContractDetails = new ArrayList<WyethContractDetail>();
			List<WyethContract> wyethContractList = new ArrayList<WyethContract>();
			Integer beginRowIndex = a*eachCount;
			Integer endRowIndex = null;
			if(a==(num-1)){
				if(rowCount%eachCount==0){
					endRowIndex = ((a+1)*eachCount)-1;							//最后一个批次时减1防止数组越界，rowCount包含表头
				}else{
					endRowIndex = (a*eachCount+(rowCount%eachCount))-1;			//最后一个批次时减1防止数组越界，rowCount包含表头
				}
			}else{
				endRowIndex = ((a+1)*eachCount)-1;
			}
	
		if(a==0){
			beginRowIndex+=4;
		}
		List<String[]> values = Reader.getSubSheetData(0,beginRowIndex,endRowIndex);
		// 数据校验 	
		for (int i = 0; i < values.size(); i++) {
			WyethContract contract = new WyethContract();
			contract.setContractId(UUID.randomUUID().toString());
			contract.setClientId(clientId);
			String[] strings = values.get(i);
			String StoreCode = strings[0];
			String StoreName = strings[3];
			String storeNo=strings[0];
			contract.setCreateBy(currentUserId);
			contract.setLastUpdateBy(currentUserId);
			/*if (StringUtil.isEmptyString(StoreCode)) {
				errStrList.add("StoreCode不能为空");
				errDataList.add(strings);
				continue;
			}*/
			Date sDate=null;
			Date stDate=null;
			Date enDate=null;
			if(StringUtil.isEmptyString(storeNo)){
				
				storeNo="无编号";
			}
			if(StringUtil.isNotEmptyString(StoreName)&&StringUtil.isNotEmptyString(storeNo)){
				String key=storeNo+StoreName;
				if(!stores2.containsKey(key)){
					errStrList.add("门店信息有误");
					errDataList.add(strings);
					continue;
				}
			}
			
			
			if (StringUtil.isEmptyString(strings[5])) {
				errStrList.add("签订日期不能为空");
				errDataList.add(strings);
				continue;
			}else {
				String signDate = strings[5].replace('/', '-');
				if(!StringUtil.isDataTime(signDate)){
					errStrList.add("签订日期不正确");
					errDataList.add(strings);
					continue;
				}
				
				 sDate = DateTimeUtils.StringToDate(signDate,
						DateTimeUtils.yyyyMMdd2);
				 
				/*if(StringUtil.isEmptyString(StoreCode)){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				   String dateString = formatter.format(sDate);
				   String key=dateString+StoreName;
				if(WyethContracts.containsKey(key)){
					errStrList.add("该协议已存在");
					errDataList.add(strings);
					continue;
				}
				}
				else{
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					   String dateString = formatter.format(sDate);
					   String key=dateString+StoreCode;
					   if(WyethContracts2.containsKey(key)){
							errStrList.add("该协议已存在");
							errDataList.add(strings);
							continue;
						}
				}*/
				contract.setSignDate(sDate);
				contract.setContractNo(storeNo + signDate);
			}
			
			if (stores.containsKey(StoreCode)) {
				String storeId = stores.get(StoreCode).getStoreId();
//				if (WyethContracts.containsKey(storeId)) {
//					errStrList.add("店铺协议已存在");
//					errDataList.add(strings);
//					continue;
//				}
				contract.setStoreId(storeId);
				
			}else{ 
				boolean containName = false;
				Store store = null;
			/*	for (Entry<String, Store> s : stores.entrySet()) {
					if(s.getValue().getName()!=null){
					if(s.getValue().getName().equals(StoreName)){
						containName = true;
						store = s.getValue();
						break;
					}
					}
				}*/
				if(stores3.containsKey(StoreName)){
					containName = true;
					store=stores3.get(StoreName);
				}
				if(containName){
					contract.setStoreId(store.getStoreId());
				}else{
					errStrList.add("门店信息不存在");
					errDataList.add(strings);
					continue;
				}
				
			}
		
			if(StringUtil.isEmptyString(strings[6])){
				errStrList.add("合作起始日期不能为空");
				errDataList.add(strings);
				continue;
			}else{
				String startDate = strings[6].replace('/', '-');
				if(!StringUtil.isDataTime(startDate)){
					errStrList.add("合作起始日期不正确");
					errDataList.add(strings);
					continue;
				}
				 stDate = DateTimeUtils.StringToDate(startDate,
						DateTimeUtils.yyyyMMdd);
				contract.setStartDate(stDate);
			
			}
			

			if (StringUtil.isEmptyString(strings[7])) {
				errStrList.add("合作结束日期不能为空");
				errDataList.add(strings);
				continue;
			}else{
				String endDate = strings[7].replace('/', '-');
				if(!StringUtil.isDataTime(endDate)){
					errStrList.add("合作结束日期不正确");
					errDataList.add(strings);
					continue;
				}
				 enDate = DateTimeUtils.StringToDate(endDate,
						DateTimeUtils.yyyyMMdd);
				contract.setEndDate(enDate);
			}
			
			if(enDate.before(stDate)){
				errStrList.add("合作结束日期必须大于合作起始日期");
				errDataList.add(strings);
				continue;
			}
			if(stDate.before(sDate)){
				errStrList.add("合作起始日期必须大于签订日期");
				errDataList.add(strings);
				continue;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String stDateString = formatter.format(stDate);
			String enDateString = formatter.format(enDate);
			String key=storeNo+StoreName+stDateString+enDateString;
			if(WyethContracts.containsKey(key)){
				errStrList.add("协议已存在");
				errDataList.add(strings);
				continue;
			}
			contract.setGift(strings[56]);
			String giftPrice = strings[57];
			if (StringUtil.isEmptyString(giftPrice)) {
				contract.setGiftPrice(null);
			} else {
				if (!StringUtil.isNumberAmount(giftPrice)&&!StringUtil.isNumberAmount1(giftPrice)) {
					errStrList.add("门店价格不是整数(或2位小数)");
					errDataList.add(strings);
					continue;
				}
				BigDecimal bd = new BigDecimal(giftPrice);
				contract.setGiftPrice(bd);
			}
			contract.setOtherGift(strings[58]);
			String otherPrice = strings[59];
			if (StringUtil.isEmptyString(otherPrice)) {
				contract.setOtherPrice(null);
			} else {
				if (!StringUtil.isNumberAmount(otherPrice)&&!StringUtil.isNumberAmount1(otherPrice)) {
					errStrList.add("门店价格不是整数(或2位小数)");
					errDataList.add(strings);
					continue;
				}
				BigDecimal bd = new BigDecimal(otherPrice);
				contract.setOtherPrice(bd);
			}
			contract.setOptionsOfInvest(strings[60]);
			String valOfShelf = strings[61];
			if (StringUtil.isEmptyString(valOfShelf)) {
				contract.setValOfShelf(null);
			} else {
				if(!StringUtil.isNumberAmount(valOfShelf)&&!StringUtil.isNumberAmount1(valOfShelf)){
					errStrList.add("陈列面积不是整数(或2位小数)");
					errDataList.add(strings);
					continue;
				}
				BigDecimal bd = new BigDecimal(valOfShelf);
				contract.setValOfShelf(bd);
			}
			contract.setOtherInvest(strings[62]);
			String hasInvoice = strings[63];
			if (hasInvoice.length()>1){
				
				errStrList.add("是否能够提供发票过长");
				errDataList.add(strings);
				continue;
			}
			contract.setHasInvoice(hasInvoice);
			String invoiceType = strings[64];
			/*if (StringUtil.isEmptyString(invoiceType)) {
				errStrList.add("发票类型不能为空");
				errDataList.add(strings);
				continue;
			}*/
			contract.setInvoiceType(invoiceType);
			String otherInvoice = strings[65];
			/*if (StringUtil.isEmptyString(otherInvoice)) {
				errStrList.add("发票其他不能为空");
				errDataList.add(strings);
				continue;
			}*/
			contract.setOtherInvoice(otherInvoice);
			String licenseType = strings[66];
			/*if (StringUtil.isEmptyString(licenseType)) {
				errStrList.add("营业相关凭证类型不能为空");
				errDataList.add(strings);
				continue;
			}*/
			contract.setLicenseType(licenseType);
			String acctType = strings[67];
			/*if (StringUtil.isEmptyString(acctType)) {
				errStrList.add("账号类型不能为空");
				errDataList.add(strings);
				continue;
			}*/
			contract.setAcctType(acctType);
			String bankName = strings[68];
			/*if (StringUtil.isEmptyString(bankName)) {
				errStrList.add("开户行不能为空");
				errDataList.add(strings);
				continue;
			}*/
			contract.setBankName(bankName);
			String bankAcct = strings[69];
			/*if (StringUtil.isEmptyString(bankAcct)) {
				errStrList.add("账号不能为空");
				errDataList.add(strings);
				continue;
			}*/
			contract.setBankAcct(bankAcct);
			String acctHolder = strings[70];
		/*	if (StringUtil.isEmptyString(acctHolder)) {
				errStrList.add("收款人不能为空");
				errDataList.add(strings);
				continue;
			}*/
			contract.setAcctHolder(acctHolder);
			contract.setClientId(clientId);
			contract.setIsDelete((byte) 0);

			wyethContractDetails = readerWyethContractDetail(8, 19,
					wyethContractDetails, monthIds, strings,
					contract.getContractId());
			wyethContractDetails = readerWyethContractDetail(20, 31,
					wyethContractDetails, monthIds, strings,
					contract.getContractId());
			wyethContractDetails = readerWyethContractDetail(32, 43,
					wyethContractDetails, monthIds, strings,
					contract.getContractId());
			wyethContractDetails = readerWyethContractDetail(44, 55,
					wyethContractDetails, monthIds, strings,
					contract.getContractId());
			wyethContractList.add(contract);
			allWyethContractList.add(contract);

		}
		// wyethContractDao.saveAll(wyethContractList);
		if (!wyethContractList.isEmpty()) {
			wyethContractDao.batchAddSales(wyethContractList);
		}
		if (!wyethContractDetails.isEmpty()) {
			
			wyethContractDetailDao.batchAddSales(wyethContractDetails);
		}
	
		}
		returnMap.put("errorCount", errDataList.size());
		returnMap.put("successCount", allWyethContractList.size());
		if (errStrList != null && !errStrList.isEmpty()) {
			String excelName = "errContractExcel" + "_"
					+ System.currentTimeMillis() + ".xlsx";
			String path = request.getSession().getServletContext().getRealPath("/") +"/export_template/contract_import_error_template.xlsx";
			//Reader.importResult(response, errDataList, errStrList, clientId, excelName);
			Reader.importResultForHS(path,response, errDataList, errStrList, clientId, excelName);
			returnMap.put("errDataExcelPath", excelName);
		} else {
			returnMap.put("errDataExcelPath", "");
		}
		return returnMap;
	}
		
	
	public List<WyethContractDetail> readerWyethContractDetail(
			Integer startNum, int endNum,
			List<WyethContractDetail> wyethContractDetails, Integer[] monthIds,
			String[] strings, String contractId) {
		int a = 0;
		for (int j = startNum; j <= endNum; j++) {
			WyethContractDetail wyethContractDetail = new WyethContractDetail();
			String s = strings[j];
			if (StringUtil.isEmptyString(s)) {
				a++;
				continue;
				
			} else {
				
				if (!StringUtil.isNumberAmount(s)&&!StringUtil.isNumberAmount1(s)) {
					a++;
					continue;
				}
				BigDecimal bd = new BigDecimal(s);
				wyethContractDetail.setValue(bd);
			}
			wyethContractDetail.setDetailId(UUID.randomUUID().toString());
			wyethContractDetail.setMonthId(monthIds[a]);
			wyethContractDetail.setIsDelete((byte) 0);
			if (j >= 8 && j < 19) {
				wyethContractDetail.setDataType((byte) 1);
			} else if (j >= 20 && j < 31) {
				wyethContractDetail.setDataType((byte) 2);
			} else if (j >= 32 && j < 43) {
				wyethContractDetail.setDataType((byte) 3);
			} else if (j >= 44 && j < 55) {
				wyethContractDetail.setDataType((byte) 4);
			}
			wyethContractDetail.setContractId(contractId);
			wyethContractDetails.add(wyethContractDetail);
			a++;
		}
		return wyethContractDetails;
	}

	@Override
	public List<ContractContent> findContractsByparam(Map<String, Object> params,boolean flag) throws BusinessException {
		List<ContractContent> lists = null;
		if(flag){ //奥维斯人员
			lists = wyethContractDao.findContractsByparam(params);
		}else{
			lists = wyethContractDao.findContractsByparam2(params);
		}
		return lists;
	}

	@Override
	public Integer findContractCountByparam(Map<String, Object> params,boolean flag) throws BusinessException {
		Integer items = 0;
		if(flag){
			items = wyethContractDao.findContractCountByparam(params);
		}else{
			items = wyethContractDao.findContractCountByparam2(params);
		}
		return items;
	}

	@Override
	public WyethContract queryWyethContractById(String contractId) {
		return wyethContractDao.queryWyethContractById(contractId);
	}


	@Override
	public void deleteContract(String contractId ,Integer currentUserId, Integer clientId)
			throws BusinessException {
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("contractId", contractId);
		params.put("lastUpdateBy",currentUserId);
		params.put("isDelete", 1);
		params.put("clientId", clientId);
		wyethContractDao.deleteContract(params);
		wyethContractFeedbackDao.deleteWyethContractFeedback(params);
		wyethContractDetailDao.deleteWyethContractDetail(params);
	}

	@Override
	public void uploadContractImages(String contractPic,String contractId, Integer currentUserId,
			Integer clientId) {
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("contractPic", contractPic);
		params.put("contractId", contractId);
		params.put("lastUpdateBy", currentUserId);
		wyethContractDao.uploadContractImages(params);
	}


	@Override
	public List<ContractFeedbackVo> findCheckedWyethContract(Map<String, Object> params,boolean flag) throws BusinessException {
		List<ContractFeedbackVo> lists = null;
		if(flag){ //奥维斯人员
			lists = wyethContractDao.findCheckedWyethContract(params);
		}else{
			lists = wyethContractDao.findCheckedWyethContract2(params);
		}
		return lists;
	}

	@Override
	public Integer findCheckedWyethContractCount(Map<String, Object> params,boolean flag) throws BusinessException {
		Integer items = 0;
		if(flag){
			items = wyethContractDao.findCheckedWyethContractCount(params);
		}else{
			items = wyethContractDao.findCheckedWyethContractCount2(params);
		}
		return items;
	}

	@Override
	public List<DataInfo> selectExportData(Map<String, Object> params,boolean flag) {
		List<TreeMap<String, String>> list = null;
		if(flag){ //有核销权限
			list = wyethContractDao.selectExportData(params);
		}else{
			list = wyethContractDao.selectExportData2(params);
		}
		List<DataInfo> dataList = new ArrayList<DataInfo>();
		DataInfo data = new DataInfo();
		List<String> headVals = DateVoSupport.getHSHead();
		data.setHeads(headVals);
		data.setSheetName("总数据导出");
		data.setValues(DateVoSupport.sort3(list, data));
		dataList.add(data);
		
		return dataList;
	}

	
	@Override
	public List<DataInfo> selectExportHTData(Map<String, Object> params,Boolean flag) {
		List<TreeMap<String, String>> list=null;
		  if(flag)
		  {
			  list = wyethContractDao.selectExportHTData(params); 
		  }else{
			  list=wyethContractDao.selectExportHTData2(params);
		  }
		List<DataInfo> dataList = new ArrayList<DataInfo>();
		DataInfo data = new DataInfo();
		List<String> headVals = DateVoSupport.getHSHTHead();
		data.setHeads(headVals);
		data.setSheetName("合同导出");
		data.setValues(DateVoSupport.sort3(list, data));
		dataList.add(data);
		
		return dataList;
	}
	
	@Override
	public ContractContent findContractPicByparam(Map<String, Object> params)
			throws BusinessException {
		
		return wyethContractDao.findContractPicByparam(params);
	}

	@Override
	public ContractContent getContractsByparam(Map<String, Object> params)
			throws BusinessException {
		
		return wyethContractDao.getContractsByparam(params);
	}

	@Override
	public void updateContract(ContractContent cc) throws BusinessException {
		List<WyethContractDetail>inserts=new ArrayList<WyethContractDetail>();
		List<WyethContractDetail>updates=new ArrayList<WyethContractDetail>();
		
		List<WyethContractDetail> wcds = cc.getWcds();
		if(wcds!=null&&wcds.size()>0){
			for (WyethContractDetail wyethContractDetail : wcds) {
				if(StringUtil.isEmptyString(wyethContractDetail.getDetailId())&&wyethContractDetail.getValue()!=null){
					wyethContractDetail.setDetailId(UUID.randomUUID().toString());
					wyethContractDetail.setContractId(cc.getContractId());
					wyethContractDetail.setIsDelete((byte) (0));
					inserts.add(wyethContractDetail);
				}else if(StringUtil.isNotEmptyString(wyethContractDetail.getDetailId())){
					wyethContractDetail.setContractId(cc.getContractId());
					updates.add(wyethContractDetail);
				}
			}
		}
		wyethContractDao.updateContract(cc);
		if(inserts!=null&&inserts.size()>0){
			wyethContractDetailDao.batchAddSales(inserts);
		}
		if(updates!=null&&updates.size()>0){
		wyethContractDetailDao.updateContractDetail(updates);
		}
	}

	@Override
	public ContractFeedbackVo findCheckedWyethContractById(
			Map<String, Object> params) throws BusinessException {
		
		return wyethContractDao.findCheckedWyethContractById(params);
	}

	@Override
	public void batchDelContract(String contractIds) throws BusinessException {
		wyethContractDao.batchDelContract(contractIds);
	}

	@Override
	public List<ContractVo> getContractList(Map<String, Object> params,Boolean flag)
			throws BusinessException {
		List<ContractVo> list=null;
		if(flag){//奥维斯人员
			list= wyethContractDao.getContractList(params);
		}else{
			list=wyethContractDao.getContractList2(params);
		}
		return list;
	}

	@Override
	public List<ContractFeedbackAppVo> findApiCheckedWyethContract(Map<String, Object> params,Boolean flag) throws BusinessException {
		List<ContractFeedbackAppVo> list = null;
		if(flag){
			list = wyethContractDao.findApiCheckedWyethContract(params);
		}else{
			list = wyethContractDao.findApiCheckedWyethContract2(params);
		}
		return list;
	}

	@Override
	public Integer getCountContractList(Map<String, Object> params ,Boolean flag)
			throws BusinessException {
		Integer items = 0;
		if(flag){//奥维斯人员
			items = wyethContractDao.getCountContractList(params);
		}else{
			items = wyethContractDao.getCountContractList2(params);
		}
		return items;
		
	}
	@Override
	public Integer findApiCheckedWyethContractCount(Map<String, Object> params,Boolean flag) throws BusinessException {
		Integer count = 0;
		if(flag){
			count = wyethContractDao.findApiCheckedWyethContractCount(params);
		}else{
			count = wyethContractDao.findApiCheckedWyethContractCount2(params);
		}
		return count;
	}

	@Override
	public List<DataInfo> selectExportHSHXData(Map<String, Object> params,Boolean flag)
			throws BusinessException {
		List<TreeMap<String, String>> list=null;
		if(flag)
		{
			list =wyethContractDao.selectExportHTSXData(params);			
		}else{
			list=wyethContractDao.selectExportHTSXData2(params);
		}
		List<DataInfo> dataList = new ArrayList<DataInfo>();
		DataInfo data = new DataInfo();
		List<String> headVals = DateVoSupport.getHSHXHead();
		data.setHeads(headVals);
		data.setSheetName("核销导出");
		data.setValues(DateVoSupport.sort3(list, data));
		dataList.add(data);
		
		return dataList;
	}

	@Override
	public Integer findApiCheckedWyethContractCount(Map<String, Object> params)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Integer getCheckContract(Map<String, Object> params)
			throws BusinessException {
		
		return wyethContractDao.getCheckContract(params);
	}


	@Override
	public ContractContent findContractByPrimaryKey(Map<String, Object> params) {
		
		return wyethContractDao.findContractByPrimaryKey(params);
	}

	
}
