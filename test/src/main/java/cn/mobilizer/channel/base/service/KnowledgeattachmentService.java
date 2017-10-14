package cn.mobilizer.channel.base.service;

import cn.mobilizer.channel.base.po.Knowledgeattachment;

public interface KnowledgeattachmentService {
	/**
	 * 添加
	 * @param knowledgeattachment
	 */
	public void addKnowledgeattachment(Knowledgeattachment knowledgeattachment);
	
	/**
	 * 删除
	 * @param knowledgeAttachmentId
	 */
	public void delKnowledgeattachment(Integer knowledgeAttachmentId);
	
	/**
	 * 修改
	 * @param knowledgeattachment
	 */
	public void updateKnowledgeattachment(Knowledgeattachment knowledgeattachment);
	
	/**
	 * 根据Id进行查找
	 * @param knowledgeAttachmentId
	 * @return
	 */
	public Knowledgeattachment queryKnowledgeattachmentById(Integer knowledgeAttachmentId);

}
