/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.dao.WyethContractDao;
import cn.mobilizer.channel.base.dao.WyethContractFeedbackDao;
import cn.mobilizer.channel.base.po.WyethContract;
import cn.mobilizer.channel.base.po.WyethContractFeedback;
import cn.mobilizer.channel.base.service.WyethContractFeedbackService;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ExcelReader;

@Service
public class WyethContractFeedbackServiceImpl implements
		WyethContractFeedbackService {
	@Autowired
	private WyethContractDao wyethContractDao;
	@Autowired
	private WyethContractFeedbackDao wyethContractFeedbackDao;

	@Override
	public Map<String, Object> addWyethContractFeedback(
			HttpServletRequest request,MultipartFile fileField, Integer clientId,
			HttpServletResponse response, Integer currentUserId) {
		List<WyethContractFeedback> wyethContractFeedbacks = new ArrayList<WyethContractFeedback>();
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ExcelReader Reader = new ExcelReader(fileField);
		Map<String, Object> params = new HashMap<String, Object>();
		List<String[]> data = Reader.getAllData(0);
		Integer[] monthIds = { 201508, 201509, 201510, 201512, 201601, 201602,
				201603, 201604, 201605, 201606, 201607 };
		List<Integer> monthIdList = Arrays.asList(monthIds);
		Map<String, WyethContractFeedback> queryWyethContractFeedbackList = null;
		if (null == data || data.isEmpty() || data.size() <= 1) {
			returnMap.put("errorMsg", "导入数据不能为空");
			return returnMap;
		}
		String month= data.get(1)[4];
		String[] split = month.split("/");
		
		String s = null;
		if(split[1].length()<2){
			s = split[0] +"0" + split[1];
		}else{
			s = split[0] + split[1];
		}
		int monthId = Integer.parseInt(s);
		params.put("monthId", monthId);
		params.put("clientId", clientId);
		Map<String, WyethContract> queryWyethContractByMonthId = wyethContractDao.queryWyethContractByMonthId(params);
		Map<String, WyethContract> queryWyethContract = wyethContractDao.queryWyethContract(params);
		Map<String, WyethContractFeedback> queryWyethContractFeedbackListByStatusAndMonthId = wyethContractFeedbackDao.queryWyethContractFeedbackListByStatusAndMonthId(params);
		params.clear();
		int indexOf = monthIdList.indexOf(monthId);
		if(indexOf!=0&&indexOf!=-1){
			params.put("monthId", monthIdList.get(indexOf-1));
			queryWyethContractFeedbackList = wyethContractFeedbackDao.queryWyethContractFeedbackList(params);
		}
		for (int i = 1; i < data.size(); i++) {
			WyethContractFeedback wcf = new WyethContractFeedback();
			
			String[] strings = data.get(i);
			String storeNo = strings[0];
			String storeName=strings[1];
			String m = strings[4];
			if (StringUtil.isEmptyString(m)) {
				errStrList.add("时间不能为空");
				errDataList.add(strings);
				continue;
			}else if(!month.equals(m)){
				errStrList.add("只能导入"+month+"月份");
				errDataList.add(strings);
				continue;
			}
			
	if (!StringUtil.isEmptyString(storeNo)) {
		
			if(queryWyethContractByMonthId.containsKey(storeNo)){
				WyethContract wyethContract = queryWyethContractByMonthId.get(storeNo);
				if(queryWyethContractFeedbackListByStatusAndMonthId.containsKey(wyethContract.getContractId())){
					errStrList.add("该月核销已存在");
					errDataList.add(strings);
					continue;
				}
				if(queryWyethContractFeedbackList!=null){
					if(queryWyethContractFeedbackList.containsKey(wyethContract.getContractId())){
						errStrList.add("上月未核销");
						errDataList.add(strings);
						continue;
					}
				}
				wcf.setContractId(wyethContract.getContractId());
				wcf.setContractNo(wyethContract.getContractNo());
			}else{
				errStrList.add("该核销不在合作期内");
				errDataList.add(strings);
				continue;
			}
			}else if(!StringUtil.isEmptyString(storeName)){
				WyethContract wyethContract = queryWyethContract.get(storeName);
				if(queryWyethContractFeedbackListByStatusAndMonthId.containsKey(wyethContract.getContractId())){
					errStrList.add("该月核销已存在");
					errDataList.add(strings);
					continue;
				}
				if(queryWyethContractFeedbackList!=null){
					if(queryWyethContractFeedbackList.containsKey(wyethContract.getContractId())){
						errStrList.add("上月未核销");
						errDataList.add(strings);
						continue;
					}
				}
				wcf.setContractId(wyethContract.getContractId());
				wcf.setContractNo(wyethContract.getContractNo());
			}else{
				errStrList.add("门店协议不存在");
				errDataList.add(strings);
				continue;
			}
			wcf.setFeedbackId(UUID.randomUUID().toString());
			
			int parseInt = Integer.parseInt(s);
			wcf.setMonthId(parseInt);
			String gnum = strings[5];
			if (StringUtil.isEmptyString(gnum)) {
				errStrList.add("礼品发放数量不能为空");
				errDataList.add(strings);
				continue;
			} else if (!StringUtil.isNumber(gnum)) {
				errStrList.add("礼品发放数量不是整数");
				errDataList.add(strings);
				continue;
			}
			wcf.setNumOfGift(Integer.parseInt(gnum));
			String mnum = strings[6];
			if (StringUtil.isEmptyString(mnum)) {
				errStrList.add("会员数量不能为空");
				errDataList.add(strings);
				continue;
			} else if (!StringUtil.isNumber(mnum)) {
				errStrList.add("会员数量不是整数");
				errDataList.add(strings);
				continue;
			}
			
			String rate=strings[7];
			if (StringUtil.isEmptyString(rate)) {
				errStrList.add("电访合格率不能为空");
				errDataList.add(strings);
				continue;
			} 
		
			BigDecimal f =new BigDecimal(rate);
			wcf.setRateOfReview(f);
			String num1=strings[8];
			if (StringUtil.isEmptyString(num1)) {
				errStrList.add("不合格会员1不能为空");
				errDataList.add(strings);
				continue;
			} else if (!StringUtil.isNumber(num1)) {
				errStrList.add("不合格会员1不是整数");
				errDataList.add(strings);
				continue;
			}
			String num2=strings[9];
			if (StringUtil.isEmptyString(num2)) {
				errStrList.add("不合格会员2不能为空");
				errDataList.add(strings);
				continue;
			} else if (!StringUtil.isNumber(num2)) {
				errStrList.add("不合格会员2不是整数");
				errDataList.add(strings);
				continue;
			}
			String numof=strings[10];
			if (StringUtil.isEmptyString(numof)) {
				errStrList.add("合格核销数不能为空");
				errDataList.add(strings);
				continue;
			} else if (!StringUtil.isNumber(numof)) {
				errStrList.add("合格核销数不是整数");
				errDataList.add(strings);
				continue;
			}
			wcf.setNum1OfUnqualified(Integer.parseInt(num1));
			wcf.setNum2OfUnqualified(Integer.parseInt(num2));
			wcf.setNumOfVerification(Integer.parseInt(numof));
			wcf.setNumOfMember(Integer.parseInt(mnum));
			wcf.setStatus((byte) 0);
			wcf.setCreateBy(currentUserId);
			wcf.setLastUpdateBy(currentUserId);
			wcf.setIsDelete((byte) 0);
			wyethContractFeedbacks.add(wcf);

		}
		if(!wyethContractFeedbacks.isEmpty()){
		wyethContractFeedbackDao.addList(wyethContractFeedbacks);
		}
		returnMap.put("errorCount", errDataList.size());
		returnMap.put("successCount", wyethContractFeedbacks.size());
		
		if (errStrList != null && !errStrList.isEmpty()) {


			String excelName = "errWyethContractFeedbackExcel" + "_"+ System.currentTimeMillis() + ".xlsx";

			//Reader.importResult(response,errDataList,errStrList,clientId,excelName);
			String path = request.getSession().getServletContext().getRealPath("/") +"/export_template/contract_feedback_import_error_template.xlsx";
			Reader.importResultForHS2(path,response, errDataList, errStrList, clientId, excelName);
			returnMap.put("errDataExcelPath", excelName);
		}else{
			returnMap.put("errDataExcelPath", "");
		}
		return returnMap;
	}

	@Override
	public List<ContractFeedbackVo> getContractFeedbackVo(Map<String, Object> params) throws BusinessException {
		List<ContractFeedbackVo> lists = wyethContractFeedbackDao.getContractFeedbackVo(params);
		TreeMap<String,ContractFeedbackVo> vos = new TreeMap<String, ContractFeedbackVo>(); //key=dataType@monthId
		for (ContractFeedbackVo contractFeedbackVo : lists) {
			String key = contractFeedbackVo.getMonthId()+"";
			if(vos.containsKey(key)){
				ContractFeedbackVo vo = vos.get(key); //1-礼品发放人数,2-会员目标数量
				if(contractFeedbackVo.getDataType().equals(1)){
					vo.setRealNumOfGift(contractFeedbackVo.getNumOfGift());
					BigDecimal decimal = new BigDecimal(contractFeedbackVo.getValue()+"");
					vo.setNumOfGift(decimal.intValue());
				}else{
					vo.setRealNumOfMember(contractFeedbackVo.getNumOfMember());
					BigDecimal decimal = new BigDecimal(contractFeedbackVo.getValue()+"");
					vo.setNumOfMember(decimal.intValue());
				}
			}else{
				if(contractFeedbackVo.getDataType().equals(1)){
					contractFeedbackVo.setRealNumOfGift(contractFeedbackVo.getNumOfGift());
					BigDecimal decimal = new BigDecimal(contractFeedbackVo.getValue()+"");
					contractFeedbackVo.setNumOfGift(decimal.intValue());
				}else{
					contractFeedbackVo.setRealNumOfMember(contractFeedbackVo.getNumOfMember());
					BigDecimal decimal = new BigDecimal(contractFeedbackVo.getValue()+"");
					contractFeedbackVo.setNumOfMember(decimal.intValue());
				}
				vos.put(key,contractFeedbackVo);
			}
		}
		List<ContractFeedbackVo> results = new ArrayList<ContractFeedbackVo>();
		Set<String> keys = vos.keySet();
		for (String key : keys) {
			results.add(vos.get(key));
		}
		return results;
	}

	@Override
	public WyethContractFeedback queryBycontractId(String contractId)
			throws BusinessException {

		return wyethContractFeedbackDao.queryBycontractId(contractId);
	}


	@Override
	public void confirmContractFeedback(Map<String, Object> params)throws BusinessException {
		
		
		wyethContractFeedbackDao.confirmContractFeedback(params);
	}

	@Override
	public void updateByPrimaryKeySelective(WyethContractFeedback wyethContractFeedback) throws BusinessException {
		wyethContractFeedbackDao.updateByPrimaryKeySelective(wyethContractFeedback);
	}


	@Override
	public WyethContractFeedback findBycontractId(String feedbackId) throws BusinessException {
		return wyethContractFeedbackDao.findBycontractId(feedbackId);
	}

	@Override
	public void uploadInvoiceImages(String feedbackId, String invoicePic,
			Integer currentUserId, Integer clientId) throws BusinessException {
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("feedbackId", feedbackId);
		params.put("invoicePic", invoicePic);
		params.put("lastUpdateBy", currentUserId);
		wyethContractFeedbackDao.uploadInvoiceImages(params);
	}

	@Override
	public WyethContractFeedback findInvoicePicByParmas(
			Map<String, Object> params) throws BusinessException {
		
		return wyethContractFeedbackDao.findInvoicePicByParmas(params);
	}

	@Override
	public void updateFeeback(WyethContractFeedback contractFeedback)
			throws BusinessException {
		
		wyethContractFeedbackDao.updateFeeback(contractFeedback);
	}

	@Override
	public void batchChecked(String feedbackIds) throws BusinessException {
		wyethContractFeedbackDao.batchChecked(feedbackIds);
	}

	@Override
	public void batchDel(String feedbackIds) throws BusinessException {
		wyethContractFeedbackDao.batchDel(feedbackIds);
	}

}
