package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.BrandDao;
import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class BrandServiceImpl implements BrandService {
	private static final Log LOG = LogFactory.getLog(BrandServiceImpl.class);
	
	@Autowired
	private BrandDao brandDao;
	
	@Override
	public Integer queryBrandCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = brandDao.queryBrandCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryBrandCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Brand> queryBrandList(Map<String, Object> param) throws BusinessException{
		List<Brand> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = brandDao.queryBrandList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryBrandCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<Brand> getObjectList(Map<String, Object> parames) {
		List <Brand> listBrand = brandDao.queryBrandList(parames);
		return listBrand;
	}

	@Override
	public Brand getObject(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBrand(Brand brand) {
		
		brandDao.addBrand(brand);
	}

	@Override
	public Brand getBrand( Integer brandId) {
		Brand brand = brandDao.getBrand(brandId);
		return brand;
	}

	@Override
	public void updateBrand(Brand brand) throws BusinessException {
		try {
			if(brand.getGrade()==1){
				brand.setParentId(null);
				brandDao.updateBrand(brand);
			}else{
				brandDao.updateBrand(brand);
			}
			
		} catch (BusinessException e) {
			LOG.error("method updateBrand error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}

	@Override
	public void deleteBrand(Integer brandId)throws BusinessException {
		
		try {
			brandDao.deleteBrand(brandId);
		} catch(BusinessException e) {
			LOG.error("method deleteBrand error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
	}
	
	Map<String,Object> parames = new HashMap<String, Object>();

	@Override
	public List<Brand> getBrandListWithOutId(Integer brandId, Integer clientId,Integer grade) throws BusinessException {
		
		try {
		Map<String,Object> parames = new HashMap<String, Object>();
		parames.put("brandId", brandId);
		parames.put("clientId", clientId);
		parames.put("grade", grade);
		parames.put("isDelete", Constants.IS_DELETE_FALSE);
		return brandDao.getBrandListWithOutId(parames);
		} catch (BusinessException e) {
			LOG.error("method getBrandListWithOutId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		
	}

	@Override
	public List<TreeNodeVO> getTreeNodes(int clientId, Integer id)throws BusinessException {
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("clientId", clientId);
			paramMap.put("id", id);
			ls = brandDao.getTreeNodesByPId(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getTreeNodes error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public List<Brand> getBrandList(Map<String, Object> parames)
			throws BusinessException {
		List<Brand> ls = null;
		try {
			ls = brandDao.getBrandList(parames);
		} catch (BusinessException e) {
			LOG.error("method getBrandList error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public Brand getBrandByIdAndClientId(Integer clientId, Integer brandId) throws BusinessException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("brandId", brandId);
		return brandDao.getBrandByIdAndClientId(params);
	}

	@Override
	public List<Brand> selectBrandListBycustomerId(Integer clientId,Integer customerId) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("customerId", customerId);
		List<Brand> brands = brandDao.selectBrandListBycustomerId(params);
		return brands;
	}
		
}
