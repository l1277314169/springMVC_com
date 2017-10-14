package cn.mobilizer.channel.base.dao;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Knowledgeattachment;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class KnowledgeattachmentDao extends MyBatisDao{
	public KnowledgeattachmentDao() {
		super("KNOWLEDGE_ATTACHMENT");
	}
	
	/**
	 * 添加
	 * @param knowledgeattachment
	 */
	public void addKnowledgeattachment(Knowledgeattachment knowledgeattachment)
	{
		super.insert("insertSelective",knowledgeattachment);
	}
	
	
	/**
	 * 删除
	 * @param knowledgeattachmentid
	 */
	public void delKnowledgeattachment(Integer knowledgeAttachmentId)
	{
		super.delete("deleteByPrimaryKey",knowledgeAttachmentId);
	}
	
	/**
	 * 修改
	 * @param knowledgeattachment
	 */
	public void updateKnowledgeattachment(Knowledgeattachment knowledgeattachment)
	{
		super.update("updateByPrimaryKeySelective",knowledgeattachment);
	}
	
	/**
	 * 根据Id进行查找
	 * @param knowledgeAttachmentId
	 * @return
	 */
	public Knowledgeattachment queryKnowledgeattachmentById(Integer knowledgeAttachmentId)
	{
		Knowledgeattachment knowledgeattachment=super.get("selectByPrimaryKey",knowledgeAttachmentId);
		return knowledgeattachment;
	}
}
