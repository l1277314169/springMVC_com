package cn.mobilizer.channel.systemManager.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.po.Messages;


public interface MessagesService {
	
	public List<Messages> getNewMessages(int clientId, int clientUserId, String lastMessageTime);
	
	public boolean readMark(int clientId, int clientUserId, int messageId);
	
	/**
	 * 
	 * @param messages
	 * @return
	 * @throws BusinessException
	 */
	public int addMessages(Messages messages)throws BusinessException;
	/**
	 * 
	 * @param messages
	 * @return
	 * @throws BusinessException
	 */
	public String updateMessages(Messages messages)throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Messages findMessagesById(Integer id)throws BusinessException;
	/**
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public List<Messages> queryMessagesList(Map<String,Object> params)throws BusinessException;
	/**
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public Integer findMessagesCount(Map<String,Object> params)throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public int deleteMessages(Map<String, Object> params)throws BusinessException;
	/**
	 * 
	 * @param params
	 * @return
	 * @throws BusinessException
	 */
	public int addMessageDetails(Map<String, Object> params)throws BusinessException;
}
