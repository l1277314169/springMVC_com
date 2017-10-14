package cn.mobilizer.channel.survey.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.survey.po.MsiQuestionGroup;
import cn.mobilizer.channel.survey.vo.MsiQuestionVO;

/**
 * @author liuyong
 *
 */
public interface MsiQuestionGroupService {
	
	public List<MsiQuestionGroup> findEntitysByMsiSurveyId(Integer clientId,Integer msiSurveyId,Integer parentGroupId) throws BusinessException;
}
