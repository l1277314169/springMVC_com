package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.mobilizer.channel.autho.ShiroDbRealm.ShiroUser;
import cn.mobilizer.channel.base.po.Sku;
import cn.mobilizer.channel.base.vo.SkuPriceVO;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface SkuService extends BasicService<Sku>{

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer querySkuCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Sku> querySkuList(Map<String, Object> searchParams) throws BusinessException;
	
	public boolean saveAll(List<Sku> list) throws Exception;

	/**产品管理--新增--保存
	 * @author Nany
	 * 2014年12月10日下午4:53:44
	 * @param clientId
	 * @param sku
	 */
	public void addSku(int clientId, Sku sku);

	/**产品管理--编辑
	 * @author Nany
	 * 2014年12月12日下午4:31:09
	 * @param skuId
	 * @param clientId
	 * @return
	 */
	public Sku getSku(Integer skuId);

	/**产品管理--编辑--保存
	 * @author Nany
	 * 2014年12月12日下午6:08:46
	 * @param sku
	 */
	public void updateSku(Sku sku,int clientId);

	/**产品管理--删除
	 * @author Nany
	 * 2014年12月12日下午6:27:08
	 * @param clientId
	 * @param skuId
	 */
	public void deleteSku(int clientId, Integer skuId);
	
	public List<Sku> getSkuByBarcode(Map<String, Object> parameters) throws BusinessException;
	
	public List<Sku> getSkuByName(Map<String, Object> parames) throws BusinessException;
	
	public List<Sku> findSkuByClientId(Integer clientId) throws BusinessException;
	/**
	 * 关联所有sku
	 * @param parameters
	 * @return
	 * @throws BusinessException
	 */
	public String selectAllSkuIds(Map<String, Object> parameters) throws BusinessException;
	
	public Sku getSkuByIdAndClientId(Integer clientId,Integer skuId);
	/**
	 * 选中所有产品
	 * @return
	 * @throws BusinessException
	 */
	public List<SkuPriceVO>  getSkuPriceJson(Integer clientId,Integer brandId,Integer categoryId,String skuName,String skuNo)throws BusinessException;
	/**
	 * select2控件展示sku
	 * @return
	 * @throws BusinessException
	 */
	public List<Sku> selectSkuToSelectTwo(Integer clientId) throws BusinessException;
	
	public Map<String, Sku> getSkuNoMap(Integer clientId) throws BusinessException;
	
	/**
	 * 高露洁批量新增
	 * @param skulist
	 * @return
	 * @throws BusinessException
	 */
	public int  insertlist(List<Sku> skulist)throws BusinessException;
	
	/**
	 * 高露洁导入
	 * @param file
	 * @param request
	 * @param resp
	 * @throws BusinessException
	 */
	public ResultMessage importColgate(MultipartFile file, HttpServletRequest request,HttpServletResponse resp)throws BusinessException;
	
	/**
	 * sku导入
	 * @param file
	 * @param request
	 * @param resp
	 * @return
	 * @throws BusinessException
	 */
	public ResultMessage importSKU(MultipartFile file, HttpServletRequest request,HttpServletResponse resp,ShiroUser shiroUser)throws BusinessException;
	
	
}
