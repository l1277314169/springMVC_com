/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.generic.InstructionConstants.Clinit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.api.vo.ResultEntity;
import cn.mobilizer.channel.api.vo.ResultEntityWithList;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.WyethContractDetail;
import cn.mobilizer.channel.base.po.WyethContractFeedback;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.service.WyethContractDetailService;
import cn.mobilizer.channel.base.service.WyethContractFeedbackService;
import cn.mobilizer.channel.base.service.WyethContractService;
import cn.mobilizer.channel.base.vo.ContractFeedbackAppVo;
import cn.mobilizer.channel.base.vo.ContractFeedbackVo;
import cn.mobilizer.channel.base.vo.ContractVo;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.ClientRolesPrivileges;
import cn.mobilizer.channel.systemManager.po.ClientRolesUserMapping;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesPrivilegesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesService;
import cn.mobilizer.channel.systemManager.service.ClientRolesUserMappingService;

@Controller
@RequestMapping(value = "/api/contract")
public class ContractController {
	@Autowired
	private WyethContractService wyethContractService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private WyethContractFeedbackService wyethContractFeedbackService;
	@Autowired
	private WyethContractDetailService wyethContractDetailService;
	@Autowired
	private ClientRolesPrivilegesService clientRolesPrivilegesService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private ClientRolesService clientRolesService;
	@Autowired
	private ClientRolesUserMappingService clientRolesUserMappingService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;
	
	/**
	 * 合同列表
	 * @param clientUserId(必填)
	 * @param startTime
	 * @param endTime
	 * @param keyWord
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/getContractList", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithList<ContractVo> getContractList(Integer clientUserId,
			String startTime, String endTime, String keyWord, Integer page,Integer pageSize) {
		try {
			if(clientUserId==null){
				ResultEntityWithList<ContractVo> re=new ResultEntityWithList<ContractVo>(false);
				re.setResultMSG("参数绑定错误");
				return re;
			}
			ClientUser clientUser = clientUserService.findByPrimaryKey(clientUserId);
			if(clientUser == null){
				ResultEntityWithList<ContractVo> re = new ResultEntityWithList<ContractVo>(false);
				re.setResultMSG("参数绑定错误");
				return re;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("keyWord", keyWord);
			params.put("clientId", clientUser.getClientId());
			params.put("startDate", startTime);
			params.put("endDate", endTime);
			params.put("clientUserId", clientUserId);
			Boolean flag = isHasPermission(clientUser);
			String subAllStructureId = channelCommService.getSubStructrueIds(clientUser.getClientStructureId());
			String subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(clientUserId);
			String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
			params.put("subStructureId",deptIds);
			//Integer count=wyethContractService.getCountContractList(params,flag);
			
			//params.put("clientUserId", clientUserIds);
			page = page == null ? 1 : page;	
			pageSize = pageSize == null ? 10 : pageSize;	
			
			params.put("_start",(page-1)*pageSize);
			params.put("_size", pageSize);
			params.put("_orderby", "a.create_time");
			params.put("_order", "desc");
			List<ContractVo> contractVoList= wyethContractService.getContractList(params,flag);
			for (ContractVo contractVo : contractVoList) {
				List<WyethContractDetail> wyethContractDetails = wyethContractDetailService.queryWyethContractDetailByContractId(contractVo.getContractId());
				contractVo.setWyethContractDetails(wyethContractDetails);
			}
			ResultEntityWithList<ContractVo> re=new ResultEntityWithList<ContractVo>(true);
			re.setDataInfo(contractVoList);
			return re;
		} catch (BusinessException e) {
			ResultEntityWithList<ContractVo> re=new ResultEntityWithList<ContractVo>(false);
			e.printStackTrace();
			return re;
		}

	}
	/**
	 * 合同校验
	 * @param storeId(必填)
	 * @param clientId(必填)
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/checkContract", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithList<Integer> getCheckContract(String storeId,Integer clientId ,
			String startTime, String endTime) {
		try {
			if(storeId==null){
				ResultEntityWithList<Integer> re=new ResultEntityWithList<Integer>(false);
				re.setResultMSG("参数绑定错误");
				return re;
			}
			if(clientId==null){
				ResultEntityWithList<Integer> re=new ResultEntityWithList<Integer>(false);
				re.setResultMSG("参数绑定错误");
				return re;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDate", startTime);
			params.put("endDate", endTime);
			params.put("storeId", storeId);
			params.put("clientId", clientId);
			Integer num=wyethContractService.getCheckContract(params);
			ResultEntityWithList<Integer> re=new ResultEntityWithList<Integer>(true);
			List<Integer>nums=new ArrayList<Integer>();
			nums.add(num);
			re.setDataInfo(nums);
			return re;
		} catch (BusinessException e) {
			ResultEntityWithList<Integer> re=new ResultEntityWithList<Integer>(false);
			e.printStackTrace();
			return re;
		}

	}
	
	
	/**
	 * 查询核销列表
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value = "/getCheckList", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResultEntityWithList<ContractFeedbackAppVo> getCheckList(Integer clientUserId,Integer status,String keyWord,Integer page,Integer pageSize) {
		try {
			if(clientUserId == null){
				ResultEntityWithList<ContractFeedbackAppVo> resultEntity = new ResultEntityWithList<ContractFeedbackAppVo>(false);
				resultEntity.setResultMSG("参数绑定错误");
				return resultEntity;
			}
			ClientUser clientUser = clientUserService.findByPrimaryKey(clientUserId);
			if(clientUser == null){
				ResultEntityWithList<ContractFeedbackAppVo> resultEntity = new ResultEntityWithList<ContractFeedbackAppVo>(false);
				resultEntity.setResultMSG("参数绑定错误");
				return resultEntity;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clientId", clientUser.getClientId());
			params.put("keyWord",keyWord);
			params.put("status", status);
			Boolean permissionFlag = isHasPermission(clientUser);
			String subAllStructureId = channelCommService.getSubStructrueIds(clientUser.getClientStructureId());
			String subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(clientUserId);
			String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
			params.put("subStructureId", deptIds);
			params.put("clientUserId", clientUserId);
			page = page == null ? 1 : page;	
			pageSize = pageSize == null ? 10 : pageSize;	
			
			params.put("_start",(page-1)*pageSize);
			params.put("_size", pageSize);
			List<ContractFeedbackAppVo> contractFeedbackVos = wyethContractService.findApiCheckedWyethContract(params,permissionFlag);
			ResultEntityWithList<ContractFeedbackAppVo> resultEntity = new ResultEntityWithList<ContractFeedbackAppVo>(true);
			resultEntity.setDataInfo(contractFeedbackVos);
			return resultEntity;
		} catch (BusinessException e) {
			ResultEntityWithList<ContractFeedbackAppVo> resultEntity=new ResultEntityWithList<ContractFeedbackAppVo>(false);
			e.printStackTrace();
			return resultEntity;
		}
	}
	
	@RequestMapping(value="/confirmCheck")
	@ResponseBody
	public Object confirmCheck(Model model,String feedbackId,Integer clientUserId,Integer yesNo,String reason,String invoicePics){
		if(clientUserId == null || yesNo == null){
			ResultEntityWithList<ContractFeedbackVo> resultEntity = new ResultEntityWithList<ContractFeedbackVo>(false);
			resultEntity.setResultMSG("参数绑定错误");
			return resultEntity;
		}
		if(yesNo.intValue() == 0 && StringUtil.isEmptyString(reason)){
			ResultEntityWithList<ContractFeedbackVo> resultEntity = new ResultEntityWithList<ContractFeedbackVo>(false);
			resultEntity.setResultMSG("参数绑定错误");
			return resultEntity;
		}
		ClientUser clientUser = clientUserService.findByPrimaryKey(clientUserId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientUser.getClientId());
		params.put("feedbackId", feedbackId);
		WyethContractFeedback wyethContractFeedback = wyethContractFeedbackService.findBycontractId(feedbackId);
		if(yesNo.intValue() == 0){
			wyethContractFeedback.setStatus((byte)2);			//2为已申诉
		}else{
			wyethContractFeedback.setStatus((byte)yesNo.intValue());
		}
		if(StringUtil.isNotEmptyString(invoicePics)){
			wyethContractFeedback.setInvoicePic(invoicePics);
		}
		if(StringUtil.isNotEmptyString(reason)){
			wyethContractFeedback.setReason(reason);
		}
		wyethContractFeedback.setLastUpdateBy(clientUserId);
		wyethContractFeedback.setLastUpdateTime(new Date());
		wyethContractFeedbackService.updateByPrimaryKeySelective(wyethContractFeedback);
		ResultEntity resultEntity = new ResultEntity(true);
		return resultEntity;
	}
	
	public Boolean isHasPermission(ClientUser clientUser){
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("clientId", clientUser.getClientId());
		parameterMap.put("privCode","C2F1");
		Boolean flag = null;
		List<ClientRolesUserMapping> clientRolesUserMappings = clientRolesUserMappingService.findClientRolesListByClientUserId(clientUser.getClientUserId(),clientUser.getClientId());
		if(clientRolesUserMappings!=null && clientRolesUserMappings.size()>0){
			for(ClientRolesUserMapping clientRolesUserMapping : clientRolesUserMappings){
				parameterMap.put("roleId", clientRolesUserMapping.getRoleId());
				ClientRolesPrivileges clientRolesPrivileges = clientRolesPrivilegesService.findClientRolesPrivilegesByCode(parameterMap);
				if(clientRolesPrivileges!=null){
					flag = true;
					break;
				}else{
					flag = false;
				}
			}
		}else{
			flag = false;
		}
		return flag;
	}
}
