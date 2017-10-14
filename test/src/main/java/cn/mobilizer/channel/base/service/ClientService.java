package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.base.vo.ClientVO;


public interface ClientService {
	
	/**
	 * 
	 * @param clientName
	 * @return
	 */
	public Client findClientByClientName(String clientName);
	
	/**
	 * 
	 * @return
	 */
	public List<Client> findAll();
	
	/**
	 * 
	 * @return
	 */
	public List<ClientVO> findClientVOAll();
	
	/**
	 * 
	 * @param cliengId
	 * @return
	 */
	public Client findByParams(Map<String, Object> params);
	
	/**
	 * 
	 * @param clientName
	 * @return
	 */
	public Client findByClientName(String clientName);
}
