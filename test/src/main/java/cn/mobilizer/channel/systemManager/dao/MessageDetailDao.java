package cn.mobilizer.channel.systemManager.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.systemManager.po.MessageDetails;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
@Repository
public class MessageDetailDao extends MyBatisDao{
	public MessageDetailDao() {
		super("MESSAGE_DETAILS");
	}
	
	public boolean readMark(int clientId, int clientUserId, int messageId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("messageId", messageId);
		super.update("updateByPrimaryKeySelective", params);
		return true;
	}
	
	public MessageDetails getMessageDetailByClientUserIdAndMessageId(int clientUserId, int messageId){
		return getMessageDetailByClientUserIdAndMessageId(clientUserId, messageId, Constants.IS_DELETE_FALSE);
	}
	
	public MessageDetails getMessageDetailByClientUserIdAndMessageId(int clientUserId, int messageId, Byte isDelete){
		Map<String, Object> params  = new HashMap<String, Object>();
		params.put("clientUserId", clientUserId);
		params.put("messageId", messageId);
		params.put("isDelete", isDelete);
		MessageDetails md = super.get("selectByUserIdAndMessageId", params);
		return md;
	}
	
	public int update(MessageDetails messageDetails){
		int count = super.update("updateByPrimaryKeySelective", messageDetails);
		return count;
	}
	public int insert(Map<String,Object> params){
		return super.insert("insertMessageDetails", params);
	}
	
	public int insertSelective(MessageDetails messageDetails){
		return super.insert("insertSelective", messageDetails);
	}
	public int updateIsdelete(Map<String,Object> params){
		return super.update("updateIsdelete", params);
	}
	
	public int updateMessageDetails(Map<String,Object> params){
		return super.update("deleteByMessageDetails", params);
	}
}
