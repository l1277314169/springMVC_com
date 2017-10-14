package cn.mobilizer.channel.api.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.mobilizer.channel.api.po.AppDataChecklist;
import cn.mobilizer.channel.api.service.AppCheckDataService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.exception.BusinessException;

@RestController
@RequestMapping(value = "/api/checkdata")
public class AppCheckDataController {
	private static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private AppCheckDataService appCheckDataService;
	
	@RequestMapping(value = "/checkTableCount", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public List<AppDataChecklist> checkTableCount(Integer clientId, Integer clientUserId, String ckeckDate, String tableNames, String ckeckData){
		List<AppDataChecklist> tlist = new ArrayList<AppDataChecklist>();
		List<AppDataChecklist> reList = new ArrayList<AppDataChecklist>();
		try {
			if(StringUtil.isNotEmptyString(ckeckData)) {
				tlist = mapper.readValue(ckeckData, new TypeReference<List<AppDataChecklist>>() {});
			}
			if(tlist != null && tlist.size() > 0 && StringUtil.isNotEmptyString(tableNames)) {
				reList = appCheckDataService.saveCheckTableCount(clientId,clientUserId,ckeckDate, tableNames, tlist);
			}
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reList;
	}
}
