package cn.mobilizer.channel.sales.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.po.Store;
import cn.mobilizer.channel.base.po.StoreUserMapping;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.sales.po.FactMonthlySales;

public interface FactMonthlySalesService{
	
	public List<FactMonthlySales> selectByParams(Map<String,Object> param) throws BusinessException;
	
	public Integer selectByParamCount(Map<String,Object> param) throws BusinessException;
	
	public FactMonthlySales getFactMonthlySales(Integer ctTaskId) throws BusinessException;
	
	public String insert(FactMonthlySales factMonthlySales) throws BusinessException;
	
	public void saveFactMonthlySales(List<FactMonthlySales> FactMonthlySalesList) throws BusinessException;
	
	public List<?> getEntityListByProcedure(Map<String,Object> param) throws BusinessException;
	
	public FactMonthlySales getEntityByDataId(Map<String,Object> param) throws BusinessException;
	
	public void updateFactMonthlySales(FactMonthlySales factMonthlySales) throws BusinessException;
	
	public void addFactMonthlySales(FactMonthlySales factMonthlySales) throws BusinessException;
	
	public Object addFactMonthlySale(MultipartFile fileField,Integer clientId,Map<String, Store> mapStore,Map<String, Sku> mapSku,HttpServletResponse response) throws BusinessException;
	 
	public Map<String,Object> importDataValidata(MultipartFile file,Integer clientId,Integer clientUserId) throws BusinessException;
	
	public Map<String,Object> addFactMonthlyUnicharmSales(List<String[]> values,Map<String, Store> mapStore,Map<String, ClientStructure> mapDept,Map<String, ClientUser> mapClientUser,Map<String, StoreUserMapping> mapStoreUser,Map<String, Category> mapCategory,Integer clientId) throws BusinessException;
}
