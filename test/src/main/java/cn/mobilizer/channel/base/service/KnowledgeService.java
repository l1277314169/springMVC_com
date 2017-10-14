package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Knowledge;
import cn.mobilizer.channel.base.po.Knowledgeattachment;

public interface KnowledgeService {

	/**
	 * 添加
	 * 
	 * @param knowledge
	 */
	public void addKnowledge(Knowledge knowledge);

	/**
	 * 添加
	 * 
	 * @param knowledge
	 */
	public void insertKnowledge(Knowledge knowledge, Knowledgeattachment knowledgeattachment);

	/**
	 * 根据Id进行查找
	 * 
	 * @param knowledgeId
	 * @return
	 */
	public Knowledge queryKnowledgeById(Integer knowledgeId);

	/**
	 * 修改
	 * 
	 * @param knowledge
	 */
	public int updateKnowledge(Knowledge knowledge);
	
	
	/**
	 * 修改
	 * 
	 * @param knowledge
	 */
	public int updateKnowledge(Knowledge knowledge, Knowledgeattachment knowledgeattachment);

	/**
	 * 删除
	 * 
	 * @param knowledgeId
	 */
	public void delKnowledge(Integer knowledgeId);

	/**
	 * 
	 * @param params
	 * @return 总条数
	 */
	public Integer queryKnowledgeCount(Map<String, Object> params);
	public List<Knowledge> queryKnowledgeList(Map<String, Object> params);
}
