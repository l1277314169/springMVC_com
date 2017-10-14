package cn.mobilizer.channel.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Brand;
import cn.mobilizer.channel.base.po.SkuSeries;
import cn.mobilizer.channel.base.service.BrandService;
import cn.mobilizer.channel.base.service.SkuSeriesService;
import cn.mobilizer.channel.base.vo.TreeNodeVO;
import cn.mobilizer.channel.comm.vo.Page;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.util.Constants;


/**品牌管理
 * @author Nany
 * 2014年12月3日下午2:03:39
 */
@Controller
@RequestMapping(value = "/skuSeries")
public class SkuSeriesController extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SkuSeriesService skuSeriesService;
	
	/**异步加载树的子结点
	 * @author Nany
	 * 2014年12月10日下午1:20:48
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getSkuSeriesByBrandId", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getSkuSeriesByBrandId(Integer brandId) {
		int clientId = getClientId();
		List<SkuSeries> list = skuSeriesService.selectByParams(clientId,brandId);
		return list;
	}
	@RequestMapping(value = "/getSkuSeries", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getSkuSeries(Integer brandId) {
		int clientId = getClientId();
		List<SkuSeries> list = skuSeriesService.selectByclientId(clientId);
		return list;
	}

}
