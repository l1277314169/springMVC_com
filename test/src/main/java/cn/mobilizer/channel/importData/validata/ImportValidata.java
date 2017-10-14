package cn.mobilizer.channel.importData.validata;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.systemManager.po.ClientBusinessDefinition;

/**
 * @author liuyong
 *
 */
public abstract class ImportValidata<T> {
	
	public Map<String,Object> validata(List<ClientBusinessDefinition> importDefinitionList,MultipartFile file,Integer clientId) {
		return null;
	}
	
	public void saveImportData(List<T> list,Integer clientId){
		
	}
	
}
