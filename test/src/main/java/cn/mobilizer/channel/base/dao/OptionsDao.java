package cn.mobilizer.channel.base.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Options;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class OptionsDao extends MyBatisDao {
	
	public OptionsDao() {
		super("OPTIONS");
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<Options> findOptionsByOptionName(Map<String, Object> params){
		return super.queryForList ("selectByParams", params);
	}

	public String getOptionText(Map<String, Object> parames) {
		return super.get("findOptionTextByParas", parames);
	}
	
	public List<Options> findAllOptions(){
		return super.getList("findAllOptions");
	}
	
	public Byte getOptionValue(Map<String,Object> parmarter){
		return super.get("getOptionValue", parmarter);
	}
	
	public List<Options> selectOptionsList(Map<String,Object> paramrter){
		return super.queryForList("selectOptionsList",paramrter);
	}
	
	public Map<String,Options> findOptionValueByKey(Map<String,Object> parmarter){
		return super.queryForMap("findOptionValueByKey", parmarter, "optionText");
	}

}