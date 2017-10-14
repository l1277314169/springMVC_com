package cn.mobilizer.channel.base.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.ClientUserRelation;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class ClientUserRelationDao extends MyBatisDao{
	public ClientUserRelationDao() {
		super("CLIENT_USER_RELATION");
	}

    public int insertSelective(ClientUserRelation clientUserRelation){
    	return super.insert ("insertSelective", clientUserRelation);
    	
    }
   
    
    public int updateClientUserRelation(ClientUserRelation clientUserRelation){
    	return super.update("updateByPrimaryKeySelective", clientUserRelation);
    	
    }
    
    public int updateIsdelete(Integer  id){
    	return super.update("updateIsdelete", id);
    }
    
    public ClientUserRelation selectClientUserRelation(Map<String,Object> parames){
    	ClientUserRelation clientUserRelation=null;
    	List<ClientUserRelation> ls= super.getList("selectOnById",parames);
    	if(ls !=null && ls.size() >0){
    		clientUserRelation = ls.get(0);
		}
    	return clientUserRelation;
    	
    }
    
    public int selectSubordinate(Integer clientUserId){
    	return super.update("selectSubordinate", clientUserId);
    }
    
    public int updateUpdownIsdelete(Integer clientUserId){
    	return super.update("selectSubordinate", clientUserId);
    }
    
    public String getSubordinates(String ids){
    	return super.get("selectSubordinates",ids);
    }
    
    public String getSubordinatesWithOutSelf(String ids){
    	return super.get("getSubordinatesWithOutSelf",ids);
    }
    
    public String queryClientUserByParentId(Integer parentId){
    	
    	return super.get("selectSubordinates",parentId);
    }
    public ClientUserRelation getClientUserRelation(Map<String,Object> parames){
    	return super.get("getClientUserRelation", parames);
    }
    
    public int findClientUserRelationByParentId(Map<String,Object> parames){
    	return super.update("findClientUserRelationByParentId", parames);
    }
    public ClientUserRelation findchildByParentId(Map<String,Object> parames){
    	return super.get("findchildByParentId", parames);
    }
    public String findDirectChilds(Map<String,Object> parames){
    	
    	return super.get("findDirectChilds", parames);
    }
    
    public int updateAllIsdelete(Map<String,Object> parmas){
    	return super.update("updateAllIsdelete", parmas);
    }
    
    public ClientUserRelation findClientUserRelation(Map<String,Object> parmas){
    	return super.get("findClientUserRelation", parmas);
    }
    
    public List<ClientUserRelation> findClientUserRelationList(Map<String,Object> parmas){
    	return super.queryForList("findClientUserRelationList", parmas);
    }
    
    public int insertList(List<ClientUserRelation> list){
    	return super.insert("insertList", list);
    }
    
    public String findUserIdsByParentId(Integer clientUserId){
    	return super.get("findUserIdsByParentId", clientUserId);
    }


}