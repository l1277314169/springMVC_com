package cn.mobilizer.channel.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class DistrictDao extends MyBatisDao{
	public DistrictDao() {
		super("DISTRICT");
	}
	
	public List<District> queryAll(){
		Map<String, Object> params = new HashMap<String, Object>();
		return super.getList("select_by_param",params);
	}
	
	public List<District> queryDistrictByCityId(Object params){
		return super.getList("select_by_cityId", params);
	};
	public District getDistrictById(Integer districtId){
		return super.get("selectByPrimaryKey", districtId);
	}
	
	public Map<String,District> queryDistrictMapByCityId(Map<String, Object> params){
		return super.queryForMap("select_by_cityId", params,"district");
	};
	
}
