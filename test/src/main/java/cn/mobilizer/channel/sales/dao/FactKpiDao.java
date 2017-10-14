package cn.mobilizer.channel.sales.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.sales.po.FactKpi;

@Repository
public class FactKpiDao extends MyBatisDao{
	
	public FactKpiDao(){
		super("FACT_KPI");
	}
    
	public int deleteByPrimaryKey(Long dataId){
		return super.delete("deleteByPrimaryKey", dataId);
	}

    public int insert(FactKpi record){
    	return super.insert("insert", record);
    }

    public int insertSelective(FactKpi record){
    	return super.insert("insertSelective", record);
    }

    public FactKpi selectByPrimaryKey(Long dataId){
    	return super.get("selectByPrimaryKey", dataId);
    }

    public int updateByPrimaryKeySelective(FactKpi record){
    	return super.update("updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(FactKpi record){
    	return super.update("updateByPrimaryKey", record);
    }
    
    public void batchSaveFactkpi(List<FactKpi> factKpis){
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("factKpis", factKpis);
    	super.insert("batchSaveFactkpi", params);
    }
    
    public List<FactKpi> selectByParams(Map<String,Object> params){
    	return super.queryForList("selectByParams",params);
    }
    
    public Integer selectByParamCount(Map<String,Object> params){
    	return super.get("selectByParamCount",params);
    }
}