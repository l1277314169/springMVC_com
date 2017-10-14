package cn.mobilizer.channel.survey.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.survey.dao.MsiQuestionGroupDao;
import cn.mobilizer.channel.survey.po.MsiQuestionGroup;
import cn.mobilizer.channel.survey.service.MsiQuestionGroupService;

@Service
public class MsiQuestionGroupServiceImpl implements MsiQuestionGroupService{

	@Autowired
	private MsiQuestionGroupDao msiQuestionGroupDao;
	
	@Override
	public List<MsiQuestionGroup> findEntitysByMsiSurveyId(Integer clientId,Integer msiSurveyId,Integer parentGroupId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("msiSurveyId", msiSurveyId);
		params.put("parentId", parentGroupId);
		List<MsiQuestionGroup> list = msiQuestionGroupDao.findEntitysByMsiSurveyId(params);
		if(list != null && list.size()>0){
			for(MsiQuestionGroup msiQuestionGroup : list){
				params.put("parentId", msiQuestionGroup.getQuestionGroupId());
				List<MsiQuestionGroup> childrenList = msiQuestionGroupDao.findEntitysByMsiSurveyId(params);
				msiQuestionGroup.setChildrenList(childrenList);
			}
		}
		return list;
	}
	
}
