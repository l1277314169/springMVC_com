package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Knowledge;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class KnowledgeDao extends MyBatisDao {
	public KnowledgeDao() {
		super("KNOWLEDGE");
	}
	
	/**
	 * 增加
	 * @param knowledge
	 */
	
	public void addKnowledge(Knowledge knowledge)
	{
		super.insert("insertSelective",knowledge);
	}
	
	/**
	 * 根据id进行查找
	 * @param knowledgeid
	 * @return
	 */
	public Knowledge  queryKnowledgeById(Integer knowledgeId)
	{
		Knowledge knowledge=super.get("selectByPrimaryKey",knowledgeId);
		return  knowledge;
	}
	
	/**
	 * 修改
	 * @param knowledge
	 */
	public int updateKnowledge(Knowledge knowledge)
	{
		super.update("updateByPrimaryKeySelective",knowledge);
		return  knowledge.getKnowledgeId();
	}
	
	/**
	 * 删除
	 * @param knowledgeid
	 */
	public void  delKnowledge(Integer knowledgeId)
	{
		super.delete("deleteByPrimaryKey",knowledgeId);
	}
	
	
	/**
	 * 
	 * @param params
	 * @return 总条数
	 */
	public Integer queryKnowledgeCount(Map<String, Object> params) 
	{
		return super.get("queryTotalCount", params);
	}
	
	public List<Knowledge> queryKnowledgeList(Map<String, Object> params){
		
		return super.queryForList("findListByParams",params);
	}
}