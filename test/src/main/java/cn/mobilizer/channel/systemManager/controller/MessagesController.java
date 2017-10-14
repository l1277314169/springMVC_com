package cn.mobilizer.channel.systemManager.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.service.ClientPositionService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.datasource.CustomerContextHolder;
import cn.mobilizer.channel.comm.service.ChannelCommService;
import cn.mobilizer.channel.comm.utils.DateUtil;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.log.service.ActivityLogService;
import cn.mobilizer.channel.log.service.SmsService;
import cn.mobilizer.channel.systemManager.po.Messages;
import cn.mobilizer.channel.systemManager.service.ClientRolesDataPrivilegesService;
import cn.mobilizer.channel.systemManager.service.MessagesService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.PropertiesHelper;

/**
 * ?????
 * 
 * @author liu.honger 2015??3??6??
 */
@Controller
@RequestMapping(value = "/message")
public class MessagesController extends BaseActionSupport {
	@Autowired
	private MessagesService messagesService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientPositionService clientPositionService;
	@Autowired
	private ChannelCommService channelCommService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private ClientRolesDataPrivilegesService clientRolesDataPrivilegesService;

	/**
	 * ??????
	 * 
	 * @author Nany 2014??12??8??????2:29:07
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, Integer page, Integer messageType, String enableDate, String expiredDate, HttpServletRequest req) {
		int clientId = getClientId();
		String publisher = getUserName().equals("admin")?null:getUserName();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("isDelete", Constants.IS_DELETE_FALSE);
		parameters.put("messageType", messageType);
		parameters.put("publisher", publisher);
		parameters.put("enableDate", DateUtil.getDayStart(enableDate));
		parameters.put("expiredDate", DateUtil.getDayStart(expiredDate));
		int count = messagesService.findMessagesCount(parameters);
		// ???
		int pagenum = page == null ? 1 : page;
		Page<Messages> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
		pageParam.buildUrl(req);
		parameters.put("_start", pageParam.getStartRows());
		parameters.put("_size", pageParam.getPageSize());
		parameters.put("_orderby", "LAST_UPDATE_TIME");
		parameters.put("_order", "DESC");
		List<Messages> messagesList = messagesService.queryMessagesList(parameters);
		pageParam.setItems(messagesList);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("messageType", messageType);
		model.addAttribute("enableDate", enableDate);
		model.addAttribute("expiredDate", expiredDate);
		model.addAttribute("page", pageParam.getPage().toString());
		return "/systemManager/messagesList";
	}

	/**
	 * ????????????
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showAddMessages")
	public String showAddMessages(Model model) throws BusinessException {
		ShiroUser user = getShiroUser();
		if (user != null && user.clientId != null) {
			int clientId = user.clientId;
			model.addAttribute("startDate", DateUtil.formatDate(new Date(), DateUtil.dateFormat));
			model.addAttribute("clientId", clientId);
			return "/systemManager/showAddMessages";
		}
		return "/dialog/error";

	}

	/**
	 * ?????????
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addMessages", produces = "application/json")
	@ResponseBody
	public Object addMessages(Messages messages) throws BusinessException {
		int clientId = getClientId();
		if (log.isDebugEnabled()) {
			log.debug("start method<addMessages>");
		}
		if (messages != null) {
			messages.setPublisher(getClientUser().getName());
			int messageId = messagesService.addMessages(messages);
			if (messages.getCheckboxId() != null && messages.getCheckboxId() != "") {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("clientId", clientId);
				parameters.put("clientUserIds", messages.getCheckboxId().split(","));
				parameters.put("messageId", messageId);
				messagesService.addMessageDetails(parameters);
			}
		}
		Map<String, Object> parame = new HashMap<String, Object>();
		parame.put("clientUserId", messages.getCheckboxId());
		parame.put("smsContent", messages.getMessageTitle());
		parame.put("smsType", Constants.INFORM);
		parame.put("clientUesrId",getCurrentUserId());
		parame.put("clientId", clientId);
		try {
			CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
			smsService.addSms(parame);
			// ?§Ý???????????
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		} catch (Exception e) {
			e.printStackTrace();
			CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
		}
		return ResultMessage.ADD_SUCCESS_RESULT;
	}

	/**
	 * ??????????
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showEditMessages/{messageId}")
	public String showEditMessages(Model model, @PathVariable("messageId") Integer messageId) throws BusinessException {
		ShiroUser user = getShiroUser();
		if (user != null && user.clientId != null) {
			int clientId = user.clientId;
			Messages messages = messagesService.findMessagesById(messageId);
			if (messages != null && messages.getCheckboxId() != null) {
				model.addAttribute("checkboxIds", messages.getCheckboxId().split(",").length);
			}
			model.addAttribute("messages", messages);
			model.addAttribute("clientId", clientId);
			return "/systemManager/showEditMessages";
		}
		return "/dialog/error";
	}

	/**
	 * ??????
	 * 
	 * @param messages
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/editMessages", produces = "application/json")
	@ResponseBody
	public Object editMessages(Messages messages) throws BusinessException {
		if (log.isDebugEnabled()) {
			log.debug("start method<editMessages>");
		}
		Integer clientId = getClientId();
		if (messages != null) {
			String smsClientuserIds = messagesService.updateMessages(messages);
			Map<String, Object> parame = new HashMap<String, Object>();
			parame.put("clientUserId", smsClientuserIds);
			parame.put("smsContent", messages.getMessageTitle());
			parame.put("smsType", Constants.INFORM);
			parame.put("clientUesrId",getCurrentUserId());
			parame.put("clientId", clientId);
			if(smsClientuserIds != null && smsClientuserIds.length() > 0){
				try {
					CustomerContextHolder.setCustomerType(CustomerContextHolder.LOG);
					smsService.addSms(parame);
					// ?§Ý???????????
					CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
				} catch (Exception e) {
					e.printStackTrace();
					CustomerContextHolder.setCustomerType(CustomerContextHolder.BASE);
				}
			}
		}
		return ResultMessage.UPDATE_SUCCESS_RESULT;
	}

	/**
	 * ????????
	 * 
	 * @param clientPositionId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteMessages/{messageId}", produces = "application/json")
	@ResponseBody
	public Object deleteMessages(@PathVariable("messageId") Integer messageId) throws BusinessException {
		int clientId = getClientId();
		if (log.isDebugEnabled()) {
			log.debug("start method<deleteMessages>");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("messageId", messageId);
		parameters.put("clientId", clientId);
		messagesService.deleteMessages(parameters);
		return ResultMessage.DELETE_SUCCESS_RESULT;
	}

	/**
	 * ??????
	 * 
	 * @param model
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "/showMessagesContents/{messageId}", produces = "application/json")
	public String showMessagesContents(Model model, @PathVariable("messageId") Integer messageId) {
		Messages messages = messagesService.findMessagesById(messageId);
		ClientUser clientUser = getClientUser();
		model.addAttribute("messages", messages);
		model.addAttribute("clientUser", clientUser);
		return "/systemManager/showMessagesContents";
	}

	/**
	 * ?????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/uploadImg", produces = "application/json")
	@ResponseBody
	public void uploadImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException {
		String savePath = PropertiesHelper.getInstance().getProperty(PropertiesHelper.KINDEDITOR_SAVEPATH);
		String saveUrl = PropertiesHelper.getInstance().getProperty(PropertiesHelper.KINDEDITOR_SAVEURL);
		ServletContext application = request.getSession().getServletContext();
		if (savePath == null || savePath == "") {
			savePath = application.getRealPath("/") + "attached_web/";
		}
		if (saveUrl == null || saveUrl == "") {
			saveUrl = request.getContextPath() + "/attached_web/";
		} else {
			saveUrl = request.getContextPath() + saveUrl;
		}

		// ?????????URL
		log.info("the savePath :" + savePath);
		log.info("the saveUrl :" + saveUrl);
		// ?????????????????????
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
//		extMap.put("media", "mp4,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("media", "mp4");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// ????????§³
		long maxSize = 1000000;
		long imgMaxSize = 1000000;
		long mediaMaxSize = 15000000;
		response.setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			String msg = getError("??????????");
			printlnJson(msg,response);
			return;
		}
		// ?????
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			String msg = getError("????????????");
			printlnJson(msg,response);
			return;
		}
		// ?????§Õ???
		if (!uploadDir.canWrite()) {
			String msg = getError("????????§Õ????");
			printlnJson(msg,response);
			return;
		}
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			String msg = getError("???????????");
			printlnJson(msg,response);
			return;
		}
		// ?????????
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			if (!item.isFormField()) {
				// ????????
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
					String msg = getError("??????????????????????????\n?????" + extMap.get(dirName) + "?????");
					printlnJson(msg,response);
					return;
				}
				// ????????§³
				if(dirName.equals("image")){
					maxSize = imgMaxSize;
				}else if(dirName.equals("media")){
					maxSize = mediaMaxSize;
				}else{

				}
				if (item.getSize() > maxSize) {
					String msg = getError("????????§³?????????");
					printlnJson(msg,response);
					return;
				}
				
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
				} catch (Exception e) {
					String msg = getError("??????????");
					printlnJson(msg,response);
					return;
				}
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				printlnJson(obj.toJSONString(),response);
			}
		}
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

	/**
	 * ?????????-????-??????????
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showMessageRelateClientUser")
	public String showMessageRelateClientUser(Model model, Integer page, String searchName, Integer clientStructureId, Integer clientPositionId, String loginName, String structureSel,
			String structureName, HttpServletRequest req,HttpServletResponse response) throws BusinessException {
		ShiroUser user = getShiroUser();
		if (user != null && user.clientId != null) {
			int clientId = user.clientId;
			clientStructureId = clientStructureId != null ? clientStructureId : getClientStructureId();
			/**?????????????¨°?????????????**/
			String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
			/**subordinates ??????????","?????**/
			String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
			/**subStructureId ??????§Ý?????§Ó???","?????**/
			String subStructureId = getClientUser().getPermissionsData();
			if(subStructureId == null || subStructureId == "" ){
				subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
			}
			String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("name", searchName);
			parameters.put("loginName", loginName);
			parameters.put("clientPositionId", clientPositionId);
			parameters.put("subordinates", subordinates);
			parameters.put("subStructureId", deptIds);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			//parameters.put("isActivation", (byte)1);                //?????????
			parameters.put("inService",(byte)0);			// ??=0  ??????????????????App?????											
			int count = clientUserService.queryClientUserCount(parameters);
			int pagenum = page == null ? 1 : page;
			Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize());
			//parameters.put("_orderby", "CLIENT_USER_ID");
			//parameters.put("_order", "DESC");
			List<ClientUser> list = clientUserService.queryClientUserList(parameters);
			pageParam.setItems(list);
			List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId(clientId);
			model.addAttribute("cpList", cpList);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("searchName", searchName);
			model.addAttribute("loginName", loginName);
			model.addAttribute("structureName", structureName);
			model.addAttribute("searchClientStructureId", clientStructureId);
			model.addAttribute("structureSel", structureSel);
			model.addAttribute("clientPositionId", clientPositionId);
			model.addAttribute("page", pageParam.getPage().toString());
			model.addAttribute("count", count);
			String 	strReplaceAlls = this.relevanceAllsClientUser(model, clientStructureId, clientPositionId, searchName,response);
			model.addAttribute("strReplaceAlls", strReplaceAlls);
			return "/systemManager/showMessageRelateClientUserList";
		}
		return "/dialog/error";
	}
	/**
	 * ?????????-??-??????????
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/showMessageEditRelateClientUser/{messageId}")
	public String showEditMessageRelateClientUser(Model model,@PathVariable("messageId")Integer messageId ,Integer page, String searchName, Integer clientStructureId, Integer clientPositionId, String loginName, String structureSel,
			String structureName, HttpServletRequest req,HttpServletResponse response) throws BusinessException {
		ShiroUser user = getShiroUser();
		if (user != null && user.clientId != null) {
			int clientId = user.clientId;
			clientStructureId = clientStructureId != null ? clientStructureId : getClientStructureId();
			/**?????????????¨°?????????????**/
			String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
			/**subordinates ??????????","?????**/
			String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
			/**subStructureId ??????§Ý?????§Ó???","?????**/
			String subStructureId = getClientUser().getPermissionsData();
			if(subStructureId == null || subStructureId == "" ){
				subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
			}
			String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("clientId", clientId);
			parameters.put("name", searchName);
			parameters.put("loginName", loginName);
			parameters.put("messageId", messageId);
			parameters.put("clientPositionId", clientPositionId);
			parameters.put("subordinates", subordinates);
			parameters.put("subStructureId", deptIds);
			parameters.put("isDelete", Constants.IS_DELETE_FALSE);
			parameters.put("isActivation", (byte)1); 
			int count = clientUserService.queryClientUserCount(parameters);
			int pagenum = page == null ? 1 : page;
			Page<ClientUser> pageParam = Page.page(count, Page.DEFAULT_PAGE_SIZE, pagenum);
			pageParam.buildUrl(req);
			parameters.put("_start", pageParam.getStartRows());
			parameters.put("_size", pageParam.getPageSize());
			List<ClientUser> list = clientUserService.findMessageClientUser(parameters);
			pageParam.setItems(list);
			List<ClientPosition> cpList = clientPositionService.findClientPositionsByClientId(clientId);
			model.addAttribute("cpList", cpList);
			model.addAttribute("pageParam", pageParam);
			model.addAttribute("searchName", searchName);
			model.addAttribute("loginName", loginName);
			model.addAttribute("structureName", structureName);
			model.addAttribute("searchClientStructureId", clientStructureId);
			model.addAttribute("structureSel", structureSel);
			model.addAttribute("clientPositionId", clientPositionId);
			model.addAttribute("page", pageParam.getPage().toString());
			model.addAttribute("count", count);
			String 	strReplaceAlls = this.relevanceAllsClientUser(model, clientStructureId, clientPositionId, searchName,response);
			model.addAttribute("strReplaceAlls", strReplaceAlls);
			return "/systemManager/showMessageRelateClientUserList";
		}
		return "/dialog/error";
	}

	/**
	 * ???????????
	 * 
	 * @param storeName
	 * @param clientStructureId
	 * @param clientUserId
	 * @return
	 */
	@RequestMapping(value = "/relevanceAllsClientUser")
	@ResponseBody
	private String relevanceAllsClientUser(Model model,Integer clientStructureId, Integer clientPositionId, String searchName,HttpServletResponse response) {
		int clientId = getClientId();
		clientStructureId = clientStructureId != null ? clientStructureId : getClientStructureId();
		/** ?????????????¨°????????????? **/
		String subAllStructureId = channelCommService.getSubStructrueIds(clientStructureId);
		/** subStructureId ??????§Ý?????§Ó???","????? **/
		String subStructureId = getClientUser().getPermissionsData();
		if (subStructureId == null || subStructureId == "") {

			subStructureId = clientRolesDataPrivilegesService.getUserPermissionsByClientUserId2String(getCurrentUserId());
		}
		/**
		 * deptIds ?????äª?????????(?????§Ò?????????????)????¨°????????§Ù?????????????subStructureId?????
		 * ??subAllStructureId?????????subStructureId?§Ö????
		 **/
		String deptIds = StringUtil.stringIntersect(subAllStructureId, subStructureId);

		String subordinates = channelCommService.getSubordinatesWithOutSelf(getCurrentUserId().toString());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("clientId", clientId);
		parameters.put("name", searchName);
		parameters.put("clientPositionId", clientPositionId);
		parameters.put("subStructureId", deptIds);
		parameters.put("subordinates", subordinates);
		String strReplaceAlls = clientUserService.relevanceAllsClientUser(parameters);
		return strReplaceAlls;
	}

	private void printlnJson(String msg,HttpServletResponse response) {
		PrintWriter writer = null;
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		try {
			writer = response.getWriter();
			writer.println(msg);
			writer.flush();
		} catch (IOException e) {
			log.warn(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					log.debug(e);
				}
			}
		}
	}
}
