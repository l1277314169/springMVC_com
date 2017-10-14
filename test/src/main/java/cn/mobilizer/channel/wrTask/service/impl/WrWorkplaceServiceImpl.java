package cn.mobilizer.channel.wrTask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.wrTask.dao.WrWorkplaceDao;
import cn.mobilizer.channel.wrTask.po.WrWorkplace;
import cn.mobilizer.channel.wrTask.service.WrWorkPlaceService;

@Service
public class WrWorkplaceServiceImpl implements WrWorkPlaceService{

	@Autowired
	private WrWorkplaceDao wrWorkplaceDao;
	
	@Override
	public List<WrWorkplace> selectByParams(Map<String, Object> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectByParamCount(Map<String, Object> param)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer workplaceId) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(WrWorkplace record) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(WrWorkplace record) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WrWorkplace selectByPrimaryKey(Integer workplaceId)throws BusinessException {
		return wrWorkplaceDao.selectByPrimaryKey(workplaceId);
	}

	@Override
	public int updateByPrimaryKeySelective(WrWorkplace record)
			throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(WrWorkplace record) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
