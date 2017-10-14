package cn.mobilizer.channel.base.dao;

import cn.mobilizer.channel.base.po.SystemFeedbackDetail;

public interface SystemFeedbackDetailDao {
    int deleteByPrimaryKey(String detailId);

    int insert(SystemFeedbackDetail record);

    int insertSelective(SystemFeedbackDetail record);

    SystemFeedbackDetail selectByPrimaryKey(String detailId);

    int updateByPrimaryKeySelective(SystemFeedbackDetail record);

    int updateByPrimaryKey(SystemFeedbackDetail record);
}