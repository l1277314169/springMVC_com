package cn.mobilizer.channel.visit.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.visit.po.Attendance;
@Repository
public class AttendanceDao extends MyBatisDao{
	public AttendanceDao() {
		super("ATTENDANCE");
	}
    int deleteByPrimaryKey(String attendanceId){
    	return 0;
    }

   public int insert(Attendance record){
    	return super.insert("insertSelective", record);
    }
   
   public Attendance selectAttendanceByClientUserIdAndDate(int clientId, int clientUserId, String startTime, String endTime){
	   Map<String, Object> params = new HashMap<String, Object>();
	   params.put("clientUserId", clientUserId);
	   params.put("startTime", startTime);
	   params.put("endTime", endTime);
	   params.put("clientId", clientId);
	   List<Attendance> list = super.queryForList("selectTodayAttendance", params);
	   if(list != null && list.size() > 0){
		   return list.get(0);
	   }else
		   return null;
   }

    int insertSelective(Attendance record){
    	return 0;
    }

    public Attendance selectByPrimaryKey(Integer attendanceId){
    	Map<String, Object> params = new HashMap<String, Object>();
  	   	params.put("attendanceId", attendanceId);
  	   	return super.get("selectByPrimaryKey", params);
    }

    int updateByPrimaryKeySelective(Attendance record){
    	return 0;
    }

    public int updateByPrimaryKey(Attendance record){
    	return super.update("updateByPrimaryKey", record);
    }
    
	public Integer queryCount(Map<String, Object> params) {
		return super.get("queryTotalCount", params);
	}
	
	//根据参数获取列表，可以适用所有基础数据查询
	public List<Attendance> getListByParames(Map<String, Object> parames){
		return super.queryForList("selectByParams", parames);
	}
	//根据参数获取列表，可以适用所有基础数据查询
	public Attendance getOneByParames(Map<String, Object> parames){
		return super.get("selectByParams", parames);
	}
	
	public List<Attendance> exportAttendance(Map<String, Object> parames){
		return super.queryForListForReport("selectByParams", parames);
	}
	
	public int insertBylist(List<Attendance> paramter){
		return super.insert("insertBylist", paramter);
	}
	
	public List<Attendance> findAttendance(Map<String, Object> parames){
		return super.getList("findAttendance", parames);
	}
	
	public List<Attendance> findAttendanceByTime(Map<String, Object> parames){
		return super.getList("findAttendanceByTime", parames);
	}
	
	public Integer unicharmQueryCount(Map<String, Object> parames){
		return super.get("unicharmQueryCount", parames);
	}
	
	public List<Attendance> unicharmQueryColumnList(Map<String, Object> parames){
		return super.getList("unicharmQueryColumnList", parames);
	}
}