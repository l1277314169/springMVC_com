package cn.mobilizer.channel.systemManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.Messages;
@Repository
public class MessagesDao extends MyBatisDao{
	public MessagesDao(){
		super("MESSAGES");
	}
	
	public List<Messages> getNewMessages(int clientId, int clientUserId, String lastMessageTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("lastMessageTime", lastMessageTime);
		return super.queryForList ("getNewMessages", params);
	}
	
	public boolean readMark(int clientId, int clientUserId, int messageId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("clientUserId", clientUserId);
		params.put("messageId", messageId);
		super.update("updateByPrimaryKeySelective", params);
		return true;
	}
	
	public int insert(Messages messages){
		super.insert("insertSelective", messages);
		return messages.getMessageId();
	}
	
	public int update(Messages messages){
		return super.update("updateByPrimaryKeySelective", messages);
	}
	
	public Messages findMessagesById(Integer id){
		return super.get("selectByPrimaryKeyleftDetails", id);
	}
	
	public List<Messages> queryMessages(Map<String,Object> param){
		return super.queryForList("queryMessages", param);
		
	}
	
	public Integer queryMessagesCount(Map<String, Object> parames){
		return super.get("queryCount", parames);
	}
	public int deleteMessages(Map<String, Object> parames){
		return super.update("deleteMessages", parames);
	}
	
	
}
