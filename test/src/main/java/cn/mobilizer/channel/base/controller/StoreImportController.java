package cn.mobilizer.channel.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.service.StoreImportService;
import cn.mobilizer.channel.comm.web.BaseActionSupport;

@Controller
@RequestMapping(value = "/storeImport")
public class StoreImportController extends BaseActionSupport{

	
	private static final long serialVersionUID = -1506725892368049461L;
	
	@Autowired
	private StoreImportService storeImportService;

	/**
	 * 门店基本信息导入总口，需要根据不同的clientId实现不同的导入逻辑
	 * @param model
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/excute")
	public void excute(Model model, MultipartFile file, HttpServletRequest request, HttpServletResponse response){
		
	}
	
}
