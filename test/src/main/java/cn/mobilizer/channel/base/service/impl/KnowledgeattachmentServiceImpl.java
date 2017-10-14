package cn.mobilizer.channel.base.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.KnowledgeattachmentDao;
import cn.mobilizer.channel.base.po.Knowledgeattachment;
import cn.mobilizer.channel.base.service.KnowledgeattachmentService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class KnowledgeattachmentServiceImpl implements
		KnowledgeattachmentService {
	private static final Log LOG = LogFactory.getLog(KnowledgeattachmentServiceImpl.class);
	
	@Autowired
	private KnowledgeattachmentDao knowledgeattachementDao;

	@Override
	public void addKnowledgeattachment(Knowledgeattachment knowledgeattachment) {
		knowledgeattachementDao.addKnowledgeattachment(knowledgeattachment);
	}

	@Override
	public void delKnowledgeattachment(Integer knowledgeAttachmentId) {
		try{
		knowledgeattachementDao.delKnowledgeattachment(knowledgeAttachmentId);
		}catch (BusinessException e) {
			LOG.error("method delKnowledgeattachment error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
	}

	@Override
	public void updateKnowledgeattachment(
			Knowledgeattachment knowledgeattachment) {
		knowledgeattachementDao.updateKnowledgeattachment(knowledgeattachment);
	}

	@Override
	public Knowledgeattachment queryKnowledgeattachmentById(
			Integer knowledgeAttachmentId) {
		Knowledgeattachment knowledgeattachment=knowledgeattachementDao.queryKnowledgeattachmentById(knowledgeAttachmentId);
		return knowledgeattachment;
	}

}
