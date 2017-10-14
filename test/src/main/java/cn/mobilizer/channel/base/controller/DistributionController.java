package cn.mobilizer.channel.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.api.vo.MediaTypes;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
/*
 * SKU分销
 */
@Controller
@RequestMapping(value = "/distribution")
public class DistributionController extends BaseActionSupport{
	
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, String searchName, String searchUserNo, String searchClientStructureId, String structureSel, HttpServletRequest req) throws BusinessException{
	
		return "base/distributionList";
	}

	@RequestMapping(value = "import", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResultMessage importData(MultipartFile file, HttpServletRequest request) throws Exception {
		
		
		return null;
	}

}
