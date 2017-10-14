/**
 * @author linwenpeng
 *
 */
package cn.mobilizer.channel.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.base.service.WyethContractFeedbackService;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;



@Controller
@RequestMapping("/contractFeedback")
public class WyethContractFeedbackController extends BaseActionSupport {
	@Autowired
	private WyethContractFeedbackService wyethContractFeedbackService;
	/**
	 * 核销导入
	 * @param fileField
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value = "import", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public Object importWyethContract(MultipartFile fileField,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> message = new HashMap<String,Object>();
		try {
			if(null==fileField){
				message.put("errorMsg","导入模板不能为空");
			}else{
				
				message = (Map<String,Object>)wyethContractFeedbackService.addWyethContractFeedback(request,fileField,super.getClientId(),response,super.getCurrentUserId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.put("errorMsg","导入失败");
		}
		return message;
	}
	@RequestMapping(value = "/confirmContractFeedback/{feedbackId}")
	@ResponseBody	
	public Object deleteClinetUser(@PathVariable("feedbackId")String feedbackId) throws BusinessException  {
		try {
			Map<String, Object>params=new HashMap<String, Object>();
			params.put("feedbackId", feedbackId);
			params.put("lastUpdateBy",super.getCurrentUserId());
			params.put("status", 1 );
			params.put("confirmBy", super.getCurrentUserId());
			wyethContractFeedbackService.confirmContractFeedback(params);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.WEB_OPERATE_FAIL;
		}
		return ResultMessage.WEB_OPERATE_SUCCESS;
	}
}
