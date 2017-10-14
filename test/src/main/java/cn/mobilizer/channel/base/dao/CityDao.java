package cn.mobilizer.channel.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class CityDao extends MyBatisDao{
	public CityDao() {
		super("CITY");
	}
	
	public City getProvinceByName(String name, int clientId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city", name);
		return super.get("select_by_param", params);
	}
	
	public List<City> queryAll(){
		Map<String, Object> params = new HashMap<String, Object>();
		return super.getList("select_by_param",params);
	}
	
	public List<City> queryCityByProvinceId(Object params){
		
		return super.getList("select_by_provinceId", params);
	}
	
	public City getCityById(Integer cityId){
		return super.get("selectByPrimaryKey", cityId);
	}
	
	public Map<String,City> queryCityMapByProvinceId(Map<String, Object> params){
		return super.queryForMap("select_by_provinceId", params,"city");
	}
	
	public Map<String,City> selectAllCity(Map<String, Object> params){
		return super.queryForMap("selectAllCity", params,"city");
	}
	public Map<String,City> selectCityByProvince(Map<String, Object> params){
		return super.queryForMap("selectCityBy_Province", params,"city");
	}
	
}
