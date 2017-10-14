package cn.mobilizer.channel.base.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.KnowledgeDao;
import cn.mobilizer.channel.base.dao.KnowledgeattachmentDao;
import cn.mobilizer.channel.base.po.Knowledge;
import cn.mobilizer.channel.base.po.Knowledgeattachment;
import cn.mobilizer.channel.base.service.KnowledgeService;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;
@Service
public class KnowledgeServiceImpl implements KnowledgeService {

	private static final Log LOG = LogFactory.getLog(KnowledgeServiceImpl.class);

	@Autowired
	private KnowledgeDao knowledgeDao;

	@Autowired
	private KnowledgeattachmentDao knowledgeattachementDao;

	@Override
	public void addKnowledge(Knowledge knowledge) {
		knowledgeDao.addKnowledge(knowledge);
	}

	@Override
	public void insertKnowledge(Knowledge knowledge, Knowledgeattachment knowledgeattachment) {
		knowledgeDao.addKnowledge(knowledge);
		knowledgeattachment.setKnowledgeId(knowledge.getKnowledgeId());
		knowledgeattachementDao.addKnowledgeattachment(knowledgeattachment);
	}

	@Override
	public Knowledge queryKnowledgeById(Integer knowledgeId) {
		Knowledge knowledge = knowledgeDao.queryKnowledgeById(knowledgeId);
		return knowledge;
	}

	@Override
	public int updateKnowledge(Knowledge knowledge) {
		knowledgeDao.updateKnowledge(knowledge);
		return knowledge.getKnowledgeId();
	}

	@Override
	public int updateKnowledge(Knowledge knowledge, Knowledgeattachment knowledgeattachment) {
		knowledgeDao.updateKnowledge(knowledge);
		knowledgeattachment.setKnowledgeId(knowledge.getKnowledgeId());
		knowledgeattachementDao.addKnowledgeattachment(knowledgeattachment);
		return knowledge.getKnowledgeId();
	}

	@Override
	public void delKnowledge(Integer knowledgeId) throws BusinessException {
		try {
			knowledgeDao.delKnowledge(knowledgeId);
		} catch (BusinessException e) {
			LOG.error("method delKnowledge error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}

	}

	@Override
	public Integer queryKnowledgeCount(Map<String, Object> params) throws BusinessException {
		int count = 0;
		try {
			count = knowledgeDao.queryKnowledgeCount(params);
		} catch (BusinessException e) {
			LOG.error("method queryKnowledgeCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Knowledge> queryKnowledgeList(Map<String, Object> params) throws BusinessException {
		List<Knowledge> list = null;
		try {
			list = knowledgeDao.queryKnowledgeList(params);
		} catch (BusinessException e) {
			LOG.error("method queryKnowledgeList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
}
