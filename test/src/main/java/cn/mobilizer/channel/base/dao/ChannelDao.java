/**
 * 
 */
package cn.mobilizer.channel.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.Chain;
import cn.mobilizer.channel.base.po.Channel;
import cn.mobilizer.channel.base.po.ClientPosition;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;

@Repository
public class ChannelDao extends MyBatisDao {
	
	public ChannelDao() {
		super("CHANNEL");
	}
	
	public List<Channel> queryChannelList (Map<String, Object> params) {
		return super.queryForList("selectByParams", params);
	}
	
	public List<TreeNodeVO> getTreeNodesByPId(Map<String, Object> paramMap) {
		return super.getList("getTreeNodesByPId", paramMap);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public List<Channel> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public Channel getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}

	
	public Integer queryChannelCount(Map<String, Object> parames){
		return super.get("queryTotalCount", parames);
	}
	
	public int insert(Channel channel){
		return super.insert ("insertSelective", channel);
		
	}
	
	public List<Channel> findChannelList(Object Params){
		return super.queryForListChannel("findByParams",Params);
		
	}
	
	public int delete(Integer id){
		return super.delete("deleteByPrimaryKey", id);
	}
	
	public int update(Channel channel){
		
		return super.update("updateByPrimaryKeySelective", channel);
	}
	
	public Channel findChannelById(int id){
		return super.get ("selectByPrimaryKey", id);
	}
	
	public String fnGetChannelChild(Integer channelId){
		return super.get("fnGetChannelChild", channelId);
	}
	
	public List<Channel> findChannelByChannelName(Map<String, Object> parames){
		return super.queryForList("findChannelByChannelName", parames); 
	}
	
	public List<Channel> findFirstChannelByChannelName(Map<String, Object> parames){
		return super.queryForList("findFirstChannelByChannelName", parames); 
	}
	
	public List<Channel> findChannelByChannelNameAndParentId(Map<String, Object> parames){
		return super.queryForListChannel("findChannelByChannelNameAndParentId", parames);
	}
	public Map<String,Channel> getChannelMap(Map<String, Object> parames){
		return super.queryForMap("getChannelMap", parames, "channelName");
	}
}
