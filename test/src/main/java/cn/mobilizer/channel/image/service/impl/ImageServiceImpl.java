package cn.mobilizer.channel.image.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.dao.ClientDao;
import cn.mobilizer.channel.base.po.Client;
import cn.mobilizer.channel.image.dao.ImageDao;
import cn.mobilizer.channel.image.po.Image;
import cn.mobilizer.channel.image.service.ImageService;
import cn.mobilizer.channel.image.vo.ColgateImage;
import cn.mobilizer.channel.image.vo.ColgateImageVo;
import cn.mobilizer.channel.image.vo.ImageGlobal;
import cn.mobilizer.channel.image.vo.ImageMagick;
import cn.mobilizer.channel.image.vo.ImageView;
import cn.mobilizer.channel.image.vo.ImageVo;
import cn.mobilizer.channel.survey.vo.DefaultCollectionGroup;
import cn.mobilizer.channel.survey.vo.DefaultCollectionGroup.GroupBy;
import cn.mobilizer.channel.util.DateTimeUtils;
import cn.mobilizer.channel.util.PropertiesHelper;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;
	@Autowired
	private ClientDao clientDao;
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public List<ImageView> getImages(Map<String, Object> params) {
		List<Image> images = imageDao.selectVisitingImage(params);
		List<ImageView> imgs = new ArrayList<ImageView>();
		Map<String, Integer> maps = new HashMap<String, Integer>();
		
		int i = 0;
		if(null!=images && !images.isEmpty()){
			for (Image image : images) {
				Date date = image.getInTime();
				if(null==date){
					continue;
				}
				List<String> pics = image.getPictures();
				for (String val : pics) {
					String temp = DateTimeUtils.formatTime(date, DateTimeUtils.yyyyMMdd);
					String[] tp = val.split("@");
					
					Image img = new Image();
					img.setImageName(tp[0]);
					img.setImageShowName(tp[1]);
					img.setInTime(image.getInTime());
					img.setStoreId(image.getStoreId());
					img.setStoreName(image.getStoreName());
					
					if(maps.containsKey(temp)){
						int index = maps.get(temp);
						ImageView imageView = imgs.get(index);
						imageView.addImage(img);
					}else{
						ImageView imageView = new ImageView();
						imageView.addImage(img);
						imageView.setShowDate(temp);
						maps.put(temp,i);
						imgs.add(imageView);
						i++;
					}
				}
			}
		}
		return imgs;
	}
	
	public void processWebImage() throws Exception{
		List<Client> clients = clientDao.loadAllClient();
		String imageFolear = PropertiesHelper.getInstance().getProperty(PropertiesHelper.IMG_FOLDER);
		log.info("start cut web image ");
		List<String> imageList = Arrays.asList(ImageGlobal.Global.IMAGES);
		for (Client client : clients) {
			log.info("start client:"+client.getClientId());
			String path = imageFolear + File.separator + client.getClientId()+ File.separator + ImageGlobal.Global.WEB_DIR;
			File[] files = new File(path).listFiles();
			String thumbPath = path+File.separator+ImageGlobal.Global.SMALL_IMAGE_PATH+File.separator+"xl_";
			if(null!=files && files.length>0){
				for (File file : files) {
					int i = file.getName().indexOf(".");
					boolean flag = false;
					if(i!=-1){
						String suffix = file.getName().substring(i+1,file.getName().length()).toLowerCase();
						if(imageList.contains(suffix)){
							flag = true;
						}
					}
					if(file.isFile() && flag){
						String temp = thumbPath+file.getName();
						File f = new File(temp);
						if(f.exists()){
							log.debug("file already exists "+file.getName());
						}else{
							log.debug("cut file name "+file.getName());
							ImageMagick.cutWebImage(file.getAbsolutePath(),temp);
						}
					}
				}
			}
		}
		log.info("cut web image done");
	}

	@Override
	public Map<String, List<ColgateImageVo>> selectSurveyImageList(Map<String, Object> params) throws Exception {
		List<ColgateImage> images = imageDao.selectSurveyImageList(params);
		
		List<ColgateImageVo> imageList = new ArrayList<ColgateImageVo>();
		for (ColgateImage colgateImage : images) {
			List<String> pics = colgateImage.getPictures();
			for (String string : pics) {
				ColgateImageVo vo = new ColgateImageVo();
				vo.setImageName(string);
				vo.setImageShowName(colgateImage.getImageName());
				vo.setAddr(colgateImage.getAddr());
				vo.setStoreName(colgateImage.getStoreName());
				vo.setStoreId(colgateImage.getStoreId());
				imageList.add(vo);
			}
		}
		
		Map<String ,List<ColgateImageVo>> imageMaps = DefaultCollectionGroup.group(imageList,new GroupBy<String>() {
            @Override
            public String groupby(Object obj) {
            	ColgateImageVo colgate = (ColgateImageVo)obj;
                return colgate.getStoreId()+"@"+colgate.getStoreName()+"@"+colgate.getAddr();
            }
        });
		return imageMaps;
	}

	@Override
	public List<ImageVo> selectImageVoList(Map<String, Object> params) throws Exception {
		
		return imageDao.selectImageVoList(params);
	}

	@Override
	public Map<String, List<ColgateImageVo>> selectSurveyDefaultImage(Map<String, Object> params) throws Exception{
		List<ColgateImage> images = imageDao.selectSurveyDefaultImage(params);
		
		List<ColgateImageVo> imageList = new ArrayList<ColgateImageVo>();
		for (ColgateImage colgateImage : images) {
			List<String> pics = colgateImage.getPictures();
			for (String string : pics) {
				ColgateImageVo vo = new ColgateImageVo();
				vo.setImageName(string);
				vo.setImageShowName(colgateImage.getImageName());
				vo.setAddr(colgateImage.getAddr());
				vo.setStoreName(colgateImage.getStoreName());
				vo.setStoreId(colgateImage.getStoreId());
				imageList.add(vo);
			}
		}
		
		Map<String ,List<ColgateImageVo>> imageMaps = DefaultCollectionGroup.group(imageList,new GroupBy<String>() {
            @Override
            public String groupby(Object obj) {
            	ColgateImageVo colgate = (ColgateImageVo)obj;
                return colgate.getStoreId()+"@"+colgate.getStoreName()+"@"+colgate.getAddr();
            }
        });
		return imageMaps;
	}
	
}
