package cn.mobilizer.channel.colgate.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.colgate.vo.FilterVo;
import cn.mobilizer.channel.colgate.vo.OverViewVo;

public interface ColgateService {

	public List<ClientStructure> getStructureByParentId(Map<String, Object> params);
	
	public List<OverViewVo> loadOverView(FilterVo filterVo);
	
	public Object loadColgateDashBoardData(FilterVo filterVo);
}
