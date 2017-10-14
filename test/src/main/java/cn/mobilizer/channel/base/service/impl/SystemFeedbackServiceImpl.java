package cn.mobilizer.channel.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.SystemFeedbackDao;
import cn.mobilizer.channel.base.po.SystemFeedback;
import cn.mobilizer.channel.base.service.SystemFeedbackService;
@Service
public class SystemFeedbackServiceImpl implements SystemFeedbackService {
	@Autowired
	private SystemFeedbackDao systemFeedbackDao;
	public int insert(SystemFeedback systemFeedback){
		return systemFeedbackDao.insert(systemFeedback);
	}
}
