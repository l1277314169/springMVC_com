package cn.mobilizer.channel.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.api.dao.ReportDao;
import cn.mobilizer.channel.api.po.VisitingCount;
import cn.mobilizer.channel.api.po.VisitingDetail;

@Service
public class ReportService {
	@Autowired
	private ReportDao reportDao;
	
	public List<VisitingCount> getVisitingCount(int clientId,int clientUserId,String startTime,String endTime){
		return reportDao.getVisitingCount(clientId, clientUserId, startTime, endTime);
	}
	
	public List<VisitingDetail> getVisitingDetail(int clientId,int clientUserId,String startTime,String endTime){
		return reportDao.getVisitingDetail(clientId, clientUserId, startTime, endTime);
	}
}
