package cn.mobilizer.channel.image.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.image.po.Image;
import cn.mobilizer.channel.image.vo.ColgateImage;
import cn.mobilizer.channel.image.vo.ImageVo;

@Repository
public class ImageDao extends MyBatisDao{

	
	public ImageDao(){
		super("IMAGE");
	}
	
	public List<Image> selectVisitingImage(Map<String, Object> params){
		return super.getList("selectVisitingImage",params);
	}
	
	public List<ColgateImage> selectSurveyImageList(Map<String, Object> params){
		return super.getList("selectSurveyImageList",params);
	}
	
	public List<ImageVo> selectImageVoList(Map<String, Object> params){
		return super.getList("selectImageVoList",params);
	}
	
	public List<ColgateImage> selectSurveyDefaultImage(Map<String, Object> params){
		return super.getList("selectSurveyDefaultImage",params);
	}
}