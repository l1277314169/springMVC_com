package cn.mobilizer.channel.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.CategoryDao;
import cn.mobilizer.channel.base.po.Category;
import cn.mobilizer.channel.base.service.CategoryService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;

@Service
public class CategoryServiceImpl implements CategoryService {
	private static final Log LOG = LogFactory.getLog(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public Integer queryCategoryCount(Map<String, Object> param) throws BusinessException{
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = categoryDao.queryCategoryCount(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryCategoryCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;
	}

	@Override
	public List<Category> queryCategoryList(Map<String, Object> param) throws BusinessException{
		List<Category> list = null;
		try {
			if ((param != null) && (param.size() > 0)) {
				list = categoryDao.queryCategoryList(param);
			}
		} catch (BusinessException e) {
			LOG.error("method queryCategoryCount error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public void addCategory(Category category) {
		categoryDao.addCategory(category);
		
	}

	@Override
	public Category getCategory(Integer categoryId) {
		Category category = categoryDao.getCategory(categoryId);
		return category;
	}

	@Override
	public void updateCategory(Category category) throws BusinessException {
		try {
			if(category.getGrade()==1){
				category.setParentId(null);
				categoryDao.updateCategory(category);
			}else{
				categoryDao.updateCategory(category);
			}
			
		} catch (BusinessException e) {
			LOG.error("method updateCategory error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_UPDATE);
		}
	}

	@Override
	public void deleteCategory(Integer categoryId) throws BusinessException {
		try {
			categoryDao.delteCategory(categoryId);
		} catch (BusinessException e) {
			LOG.error("method deleteCategory error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_DELETE);
		}
	}

	@Override
	public List<Category> getCategoryListWithOutId(Integer categoryId,int clientId)throws BusinessException {
		try {
			Integer grade = 1;
			Map<String,Object> parames = new HashMap<String, Object>();
			parames.put("categoryId", categoryId);
			parames.put("clientId", clientId);
			parames.put("grade", grade);
			parames.put("isDelete", Constants.IS_DELETE_FALSE);
			List<Category> categoryList = categoryDao.getCategoryListWithOutId(parames);
			return categoryList;
		} catch (Exception e) {
			LOG.error("method getCategoryListWithOutId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		
		
	}

	@Override
	public List<TreeNodeVO> getTreeNodes(int clientId, Integer id)throws BusinessException {
		List<TreeNodeVO> ls = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			paramMap.put("clientId", clientId);
			ls = categoryDao.getTreeNodes(paramMap);
		} catch (BusinessException e) {
			LOG.error("method getCategoryListWithOutId error, ", e);
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return ls;
	}

	@Override
	public Category getCategoryByClientIdAndId(Integer clientId,Integer categoryId) {	
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("categoryId", categoryId);
		return categoryDao.getCategoryByClientIdAndId(params);
	}

	@Override
	public Map<String, Category> getCategoryMap(Integer clientId) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		return categoryDao.getCategoryMap(params);
	}
	
}
