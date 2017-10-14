package cn.mobilizer.channel.importData.validata;

import java.util.HashMap;
import java.util.Map;

public class ImportDataValidataManager {
	
	private Map<String, ImportValidata> validataManager = new HashMap<String, ImportValidata>();

	public Map<String, ImportValidata> getValidataManager() {
		return validataManager;
	}

	public void setValidataManager(Map<String, ImportValidata> validataManager) {
		this.validataManager = validataManager;
	}
	
	public ImportValidata getValidata(String key){
		return validataManager.get(key);
	}
	
}
