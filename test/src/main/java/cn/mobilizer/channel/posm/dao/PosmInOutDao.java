package cn.mobilizer.channel.posm.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.posm.po.PosmInOut;
/**
 * 
* @ClassName: PosmInOutDao 
* @Description: 
* @author  pengwei
* @date 2015年9月25日 下午4:10:59 
*
 */
import cn.mobilizer.channel.posm.vo.PosmInOutExcelVo;

@Repository
public class PosmInOutDao extends MyBatisDao{

	public PosmInOutDao(){
		super("POSMINOUT");
	}
	/**
	 * 根据明细Key  获取 明细实例
	 * @param inOutId
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public PosmInOut selectByPrimaryKey(Integer inOutId){
		return super.get("selectByPrimaryKey", inOutId);
	}
	
	public Integer selectselectPInOutsCount(Map<String, Object> map){
		return super.get("selectselectPInOutsCount");
	}
	
	public List<PosmInOutExcelVo> selectPInOuts(Map<String, Object> map){
		return super.getList("selectPInOuts", map);
	}
	
	/**
	 * 进出明细分页
	 * @param map
	 * @return
	 */
	public Integer selectPInOutListCount(Map<String, Object> map){
		return super.get("selectPInOutListCount",map);
	}
	
	
	/**
	 * 批量插入数据
	 * @param list
	 * @return
	 * @author：wei.peng
	 * @date 2015年9月25日
	 */
	public Integer insertPosmInOuts(List<PosmInOut> list){
		return super.insert("insertPosmInOuts", list);
	}
	
	/**
	 * 查询导出
	 */
	public List<PosmInOutExcelVo> selectPInOutExport(Map<String,Object> parameter) {
		return super.getList("selectPInOutExport",parameter);
	}
	
	
	public Integer insertSelective(PosmInOut object){
		return super.insert("insertSelective", object);
	}
	/**
	 * 修改 
	 * @param object
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public Integer updateByPrimaryKeySelective(PosmInOut object){
		return super.update("updateByPrimaryKeySelective", object);
	}
}
