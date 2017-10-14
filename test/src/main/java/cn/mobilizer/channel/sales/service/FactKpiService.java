package cn.mobilizer.channel.sales.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.sales.po.FactKpi;

public interface FactKpiService {
	
	/**
	 * Excel导入验证
	 * @param file
	 * @param clientId
	 * @return
	 */
	public Map<String,Object> validata(MultipartFile file,Integer clientId,Integer clientUserId);
	
	/**
	 * 批量保存销量目标
	 * @param factKpis
	 */
	public void batchSaveFactKpis(List<FactKpi> factKpis);
	
	public List<FactKpi> selectByParams(Map<String,Object> params);
	
	public Integer selectByParamCount(Map<String,Object> params);
}
