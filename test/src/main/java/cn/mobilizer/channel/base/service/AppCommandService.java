package cn.mobilizer.channel.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.AppCommandDao;
import cn.mobilizer.channel.base.po.AppCommand;

@Service
public class AppCommandService {
	@Autowired
	private AppCommandDao appCommandDao;
	
	public List<AppCommand> getUnexeCommand(Integer clientUserId){
		return appCommandDao.getUnExeCommand(clientUserId);
	}
	
	public Integer exeCommand(Integer commandId, String exeResult){
		return appCommandDao.exeCommand(commandId, exeResult);
	}

}
