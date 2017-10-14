package cn.mobilizer.channel;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.dao.CityDao;
import cn.mobilizer.channel.base.dao.DistrictDao;
import cn.mobilizer.channel.base.dao.ProvinceDao;
import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.District;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.base.vo.AreaVO;
import cn.mobilizer.channel.comm.jms.TopicMessageProducer;
import cn.mobilizer.channel.comm.jms.MessageFactory;
import cn.mobilizer.channel.comm.utils.MemcachedUtil;
import cn.mobilizer.channel.comm.vo.MemcachedEnum;

@Controller
public class IndexAction {
	
	protected Logger LOG = Logger.getLogger(this.getClass());
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private ClientUserService clientUserService;
//	@Resource(name="sysTestMessageProducer")
	private TopicMessageProducer sysTestMessageProducer;
//	@Resource(name="photoMessageProducer")
	private TopicMessageProducer photoMessageProducer;
	
	@RequestMapping(value = "/index")
	public String index() throws Exception {
		return "index";
	}
	
	@RequestMapping(value = "/initArea", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public AreaVO initArea() throws Exception {
		AreaVO areaVO = new AreaVO();
		
		List<Province> provinces  = provinceDao.queryAll();
		
		List<City> cities = cityDao.queryAll();
		
		List<District> districts = districtDao.queryAll();
		
		areaVO.setProvinces(provinces);
		areaVO.setCities(cities);
		areaVO.setDistricts(districts);
		
		JSONObject jSONObject  = JSONObject.fromObject(areaVO);
		
		return areaVO;
	}
	
	@RequestMapping(value = "/api/memcachedTest", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ClientUser memcachedTest() throws Exception {
		Integer clientUserId = 9;
		Object clientUserCache = MemcachedUtil.getInstance().get(MemcachedEnum.ClientUser.getKey()+clientUserId);
		if (null != clientUserCache) {
			LOG.info("get Basic data of clientUser from MemCache--YES");
			return (ClientUser) clientUserCache;
		}
		LOG.info("get Basic data of clientUser from MemCache--NO");
		ClientUser clientUser = clientUserService.findByPrimaryKey(clientUserId);
		MemcachedUtil.getInstance().set(MemcachedEnum.ClientUser.getKey()+clientUserId,MemcachedEnum.ClientUser.getSec(),clientUser);
		return clientUser;
	}
	
	@RequestMapping(value = "/api/mqTest/sysTest", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String mqSysTest() throws Exception {
		Integer testId = 111111110;
//		sysTestMessageProducer.sendMsg(MessageFactory.newProductMessage(productId, "AddGroup"));
		sysTestMessageProducer.sendMsg(MessageFactory.newSysTestCreateMessage(testId));
		return "ok";
	}
	
	@RequestMapping(value = "/api/mqTest/photo", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String mqPhotoTest() throws Exception {
		Integer testId = 11122222;
//		sysTestMessageProducer.sendMsg(MessageFactory.newProductMessage(productId, "AddGroup"));
		photoMessageProducer.sendMsg(MessageFactory.newPhotoCreateMessage(testId, "4/9/ccav.png"));
		return "ok";
	}
}
