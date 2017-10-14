package cn.mobilizer.channel.systemManager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.comm.utils.ArrayUtil;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.systemManager.dao.MessageDetailDao;
import cn.mobilizer.channel.systemManager.dao.MessagesDao;
import cn.mobilizer.channel.systemManager.po.MessageDetails;
import cn.mobilizer.channel.systemManager.po.Messages;
import cn.mobilizer.channel.systemManager.service.MessagesService;
import cn.mobilizer.channel.util.Constants;
@Service
public class MessagesServiceImpl implements MessagesService {
	@Autowired
	private MessagesDao messagesDao;
	@Autowired
	private MessageDetailDao messageDetailDao;
	
	@Override
	public List<Messages> getNewMessages(int clientId, int clientUserId, String lastMessageTime){
		return messagesDao.getNewMessages(clientId, clientUserId, lastMessageTime);
	}

	@Override
	public boolean readMark(int clientId, int clientUserId, int messageId) {
		return false;
	}
	
	@Override
	public int addMessages(Messages messages) throws BusinessException {
		return messagesDao.insert(messages);
	}

	@Override
	public String updateMessages(Messages messages) throws BusinessException{
		String smsClientuserIds = "";
		messagesDao.update(messages);
		/** 修改消息关联人员 */
//		Messages oldMessages = messagesDao.findMessagesById(messages.getMessageId());
		String[] oldstr = (messages.getOldCheckboxId() != null && messages.getOldCheckboxId() != "") ? messages.getOldCheckboxId().split(",") : null;
		String[] newStr = (messages.getCheckboxId() != null && messages.getCheckboxId() != "") ? messages.getCheckboxId().split(",") : null;

		Map<String, Object> params = new HashMap<String, Object>();
		Integer messageId = messages.getMessageId();
		Integer clientId = messages.getClientId();
		params.put("messageId", messageId);
		params.put("clientId", clientId);
		
		if (oldstr == null) {
			if (newStr != null) {
				// 如果以前的人员为空,那么全部为新增
				smsClientuserIds = addMesaage(messageId, clientId, newStr);
			}
		} else {
			if (newStr == null) {// 如果以前的人员不为空，当前人员为空，那么全部删除
				messageDetailDao.updateIsdelete(params);
			} else {// 如果以前和当前的人员都不为空
				/** 获取old中存在而new中不存在的人员，删除 **/
				String clientUserIds = ArrayUtil.arraySubtract2Str(oldstr, newStr);
				if (clientUserIds != null && clientUserIds != "") {
					params.put("clientUserIds", clientUserIds);
					messageDetailDao.updateMessageDetails(params);
				}
				/** 获取new中存在而old中不存在的人员，新增 **/
				String[] newStrbu = ArrayUtil.arraySubtract(newStr, oldstr);
				if (newStrbu != null && newStrbu.length > 0) {
					smsClientuserIds = addMesaage(messageId, clientId, newStrbu);
				}
			}
		}
		return smsClientuserIds;
	}

	@Override
	public Messages findMessagesById(Integer id) throws BusinessException {
		return messagesDao.findMessagesById(id);
	}

	@Override
	public List<Messages> queryMessagesList(Map<String, Object> params) throws BusinessException {
		return messagesDao.queryMessages(params);
	}

	@Override
	public Integer findMessagesCount(Map<String, Object> params) throws BusinessException {
		return messagesDao.queryMessagesCount(params);
	}

	@Override
	public int deleteMessages(Map<String, Object> params) throws BusinessException {
		
		return messagesDao.deleteMessages(params);
	}

	@Override
	public int addMessageDetails(Map<String, Object> params) throws BusinessException {
		
		return messageDetailDao.insert(params);
	}
	
	private String addMesaage(Integer messageId, Integer clientId, String[] clientUserIds) {
		String smsClientuserIds = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("messageId", messageId);
		params.put("clientId", clientId);
		
		/**查询数据库是否曾经存在关联关系，如果有修改状态**/
		if(clientUserIds !=null && clientUserIds.length >0) {
			for ( int i = 0 ; i < clientUserIds.length ; i++ ) {
				Integer clientUserId = Integer.valueOf(clientUserIds[i]);
				MessageDetails messageDetails = messageDetailDao.getMessageDetailByClientUserIdAndMessageId(clientUserId, messageId, null);
				if(messageDetails == null){
					smsClientuserIds += clientUserId+",";
					messageDetails = new MessageDetails();
					messageDetails.setClientId(clientId);
					messageDetails.setClientUserId(clientUserId);
					messageDetails.setIsDelete(Constants.IS_DELETE_FALSE);
					messageDetails.setIsRead(Constants.MSG_IS_READ_FALSE);
					messageDetails.setMessageId(messageId);
					messageDetailDao.insertSelective(messageDetails);
				} else {
					messageDetails.setIsDelete(Constants.IS_DELETE_FALSE);
					messageDetails.setIsRead(Constants.MSG_IS_READ_FALSE);
					messageDetails.setLastUpdateTime(null);
					messageDetailDao.update(messageDetails);
				}
			}
		}
		return smsClientuserIds;
	}
}
