package cn.mobilizer.channel.image.service;

import java.util.List;
import java.util.Map;

import cn.mobilizer.channel.image.vo.ColgateImage;
import cn.mobilizer.channel.image.vo.ColgateImageVo;
import cn.mobilizer.channel.image.vo.ImageView;
import cn.mobilizer.channel.image.vo.ImageVo;

public interface ImageService {

	public List<ImageView> getImages(Map<String, Object> params) throws Exception;
	
	public void processWebImage() throws Exception;
	
	public Map<String, List<ColgateImageVo>> selectSurveyImageList(Map<String, Object> params) throws Exception;
	
	public List<ImageVo> selectImageVoList(Map<String, Object> params) throws Exception;

	public Map<String, List<ColgateImageVo>> selectSurveyDefaultImage(Map<String, Object> params) throws Exception;
}
