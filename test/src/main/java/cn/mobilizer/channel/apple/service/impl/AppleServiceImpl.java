package cn.mobilizer.channel.apple.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.apple.dao.AppleDao;
import cn.mobilizer.channel.apple.service.AppleService;
import cn.mobilizer.channel.apple.vo.AppleACSCVo;
import cn.mobilizer.channel.apple.vo.AppleDataVo;
import cn.mobilizer.channel.apple.vo.AppleDistribution;
import cn.mobilizer.channel.apple.vo.AppleFilter;
import cn.mobilizer.channel.apple.vo.AppleOverViewVo;
import cn.mobilizer.channel.colgate.vo.ColgateConst;

@Service
public class AppleServiceImpl implements AppleService {

	@Autowired
	private AppleDao appleDao;

	@Override
	public List<AppleOverViewVo> loadOverView(AppleFilter filter) {
		
		return appleDao.loadOverView(filter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppleACSCVo loadAppleACSC(AppleFilter filter) {
		List<?> lists = appleDao.loadAppleACSC(filter);
		AppleACSCVo appleACSCVo = new AppleACSCVo();
		AppleDistribution appleDistribution = new AppleDistribution();
		if(!lists.isEmpty()){
			for (int i = 0; i < lists.size();i+=2) {
				List<Integer> head =  (List<Integer>) lists.get(i);
				List<AppleDataVo> datas = (List<AppleDataVo>) lists.get(i+1);
				appleACSCVo.setStoresAudited(head.get(0));
				appleACSCVo.setDashboardId(ColgateConst.DashBoard.DISTRIBUTION);
				appleDistribution.setData(datas);
				appleACSCVo.setAppleDistribution(appleDistribution);
			}
		}
		return appleACSCVo;
	}
	
}
