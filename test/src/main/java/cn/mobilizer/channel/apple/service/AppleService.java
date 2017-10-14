package cn.mobilizer.channel.apple.service;

import java.util.List;

import cn.mobilizer.channel.apple.vo.AppleACSCVo;
import cn.mobilizer.channel.apple.vo.AppleFilter;
import cn.mobilizer.channel.apple.vo.AppleOverViewVo;

public interface AppleService {

	
	public List<AppleOverViewVo> loadOverView(AppleFilter filter);
	
	public AppleACSCVo loadAppleACSC(AppleFilter filter);
	
}
