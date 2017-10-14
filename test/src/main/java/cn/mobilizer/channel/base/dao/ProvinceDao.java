package cn.mobilizer.channel.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class ProvinceDao extends MyBatisDao{
	public ProvinceDao() {
		super("PROVINCE");
	}
	
	public Province getProvinceByName(String province){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("province", province);
		return super.get("selectbyparam", params);
	}
	
	public List<Province> queryAll(){
		Map<String, Object> params = new HashMap<String, Object>();
		return super.queryForList("selectbyparam",params);
	}
	
	public Province getProvinceNameById(Integer provinceId){
		return super.get("selectByPrimaryKey", provinceId);
		
	}
	
	/**
	 * 根据 params 参数查询 省份
	 * @param params	自动拼接 params 里的参数 ，
	 * @return	
	 * 		MAP ，key：province（省）
	 */
	public Map<String,Province> getProvinceMapByName(Map<String, Object> params){
		return super.queryForMap("selectbyparam", params, "province");
	}
	
}
