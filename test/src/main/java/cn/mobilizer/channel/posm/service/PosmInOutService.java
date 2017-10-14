/**   
 * @Title: PosmInOutDaoService.java 
 * @Package cn.mobilizer.channel.posm.service 
 * @author 仪动信息技术（上海）有限公司
 * @date 2015年9月23日 下午4:14:39 
 * @version V1.0   
 */
package cn.mobilizer.channel.posm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.posm.po.PosmInOut;
import cn.mobilizer.channel.posm.vo.PosmInOutExcelVo;

/**
 * @ClassName: PosmInOutDaoService
 * @author pengwei
 * @date 2015年9月23日 下午4:14:39
 * 
 */
public interface PosmInOutService {

	/**
	 * 获取记录条数
	 * 
	 * @param map
	 * @return
	 */
	public Integer getPosmInOutCount(Map<String, Object> map);

	/**
	 * 获取所有记录
	 * 
	 * @param map
	 * @return 带分页效果
	 */
	public List<PosmInOutExcelVo> getPosmInOuts(Map<String, Object> map);

	/**
	 * 进出明细分页
	 * 
	 * @param map
	 * @return
	 */
	public Integer getPInOutListCount(Map<String, Object> map);

	/**
	 * 导入数据
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	public ResultMessage saveInputPosmInOuts(MultipartFile file,
			Integer clientId, Integer userId, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 导出的查询
	 * 
	 * @return
	 */
	public List<PosmInOutExcelVo> selectPInOutExport(
			Map<String, Object> parameter);

	/**
	 * 
	 * @param excelVo
	 *            添加明细的基本信息
	 * @param materialId
	 *            物料编号
	 * @param userId
	 *            用户编号
	 * @param clientId
	 *            客户编号
	 * @param stokRemark
	 *            保存在库存中的备注
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public ResultMessage savePosmInOut(PosmInOutExcelVo excelVo,
			Integer materialId, Integer userId, Integer clientId,
			String stokRemark);

	/**
	 * 添加 明细记录
	 * 
	 * @param posmInOut
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public boolean savePosmInOut(PosmInOut posmInOut);

	/**
	 * 修改明细记录
	 * 
	 * @param posmInOut
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public boolean updataPosmInOut(PosmInOut posmInOut);

	/**
	 * 
	 * @param inoutKey		明细编号
	 * @param userId		用户编号
	 * @param clentId		客户编号
	 * @param remark		备注
	 * @return	
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public ResultMessage deletePosmInOut(Integer inoutKey,Integer userId,Integer clentId,String remark);

	/**
	 * 根据 明细编号获取
	 * @param key
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月10日
	 */
	public PosmInOut getPosmInOutByKey(Integer key);

}
