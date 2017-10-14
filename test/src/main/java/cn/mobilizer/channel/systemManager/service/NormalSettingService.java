package cn.mobilizer.channel.systemManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.systemManager.dao.NormalSettingDao;
import cn.mobilizer.channel.systemManager.po.NormalSettings;
@Service
public class NormalSettingService {
	@Autowired
	private NormalSettingDao normalSettingDao;
	
	public NormalSettings getByKeyCode(Integer clientId, String keyCode){
		return normalSettingDao.getByKeyCode(clientId, keyCode);
	}

}
