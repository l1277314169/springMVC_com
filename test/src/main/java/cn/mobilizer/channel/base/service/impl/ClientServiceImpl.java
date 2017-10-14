package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientDao;
import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.service.ClientService;
import cn.mobilizer.channel.base.vo.ClientVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class ClientServiceImpl implements ClientService{
	private static final Log LOG = LogFactory.getLog(ClientServiceImpl.class);
			
	@Autowired
	private ClientDao clientDao;
	
	@Override
	public Client findClientByClientName(String clientName){
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("clientName", clientName);
		return clientDao.findByClientName(para);
	}
	
	@Override
	public List<Client> findAll(){
		List<Client> list = null;
		try {
			list = clientDao.queryAll();
		} catch (Exception e) {
			LOG.error("method findAll error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	@Override
	public List<ClientVO> findClientVOAll(){
		List<ClientVO> list = null;
		try {
			list = clientDao.queryClientVOAll();
		} catch (Exception e) {
			LOG.error("method findAll error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}
	
	@Override
	public Client findByParams(Map<String, Object> params){
		return clientDao.findClientByParams(params);
	}

	/**
	 * 根据 clientName 查询 Client 
	 *
	 */
	@Override
	public Client findByClientName(String clientName){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clientName", clientName);
		return clientDao.findByClientName(paramMap);
	}

}
