package cn.mobilizer.channel.base.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.web.BasicService;
import cn.mobilizer.channel.exception.BusinessException;

/**
 * 
 * @author yeeda.tian
 *
 */
public interface BrandService extends BasicService<Brand>{

	/**
	 * 获取查询的总记录数
	 * @param searchParams
	 * @return
	 */
	public Integer queryBrandCount(Map<String, Object> searchParams) throws BusinessException;
	
	/**
	 * 分页查询
	 * @param searchParams
	 * @return
	 * @throws BusinessException
	 */
	public List<Brand> queryBrandList(Map<String, Object> searchParams) throws BusinessException;

	/**新增 保存品牌
	 * @author Nany
	 * 2014年12月3日下午7:07:11
	 * @param brand
	 */
	public void addBrand(Brand brand);

	/**品牌管理--编辑 ，查询数据回显
	 * @author Nany
	 * 2014年12月4日上午10:00:55
	 * @param clientId
	 * @param brandId
	 * @return
	 */
	public Brand getBrand( Integer brandId);

	/**品牌管理--编辑--保存
	 * @author Nany
	 * 2014年12月4日下午12:06:32
	 * @param brand
	 */
	public void updateBrand(Brand brand);

	/**品牌管理--删除
	 * @author Nany
	 * 2014年12月4日下午2:37:07
	 * @param brandId
	 */
	public void deleteBrand(Integer brandId);
	
	/**
	 * 
	 * @author Nany
	 * 2014年12月5日下午4:42:13
	 * @param brandId
	 * @param clientId
	 * @return
	 * @throws BusinessException
	 */
	public List<Brand> getBrandListWithOutId(Integer brandId, Integer clientId,Integer grade) throws BusinessException;

	/** 异步加载树的子结点
	 * @author Nany
	 * 2014年12月10日下午1:25:30
	 * @param clientId
	 * @param id
	 * @return
	 */
	public List<TreeNodeVO> getTreeNodes(int clientId, Integer id) throws BusinessException;

	public List<Brand> getBrandList(Map<String, Object> parames) throws BusinessException;
	
	public Brand getBrandByIdAndClientId(Integer clientId,Integer brandId) throws BusinessException;
	
	public List<Brand> selectBrandListBycustomerId(Integer clientId,Integer customerId) throws BusinessException;
	
}
