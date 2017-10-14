package cn.mobilizer.channel.posm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.ClientStructure;
import cn.mobilizer.channel.base.po.ClientUser;
import cn.mobilizer.channel.base.po.Province;
import cn.mobilizer.channel.base.service.CityService;
import cn.mobilizer.channel.base.service.ClientUserService;
import cn.mobilizer.channel.comm.utils.StringUtil;
import cn.mobilizer.channel.constant.ImportConstants;
import cn.mobilizer.channel.exception.BusinessException;
import cn.mobilizer.channel.posm.dao.WarehouseDao;
import cn.mobilizer.channel.posm.po.Warehouse;
import cn.mobilizer.channel.posm.service.WarehouseService;
import cn.mobilizer.channel.util.Constants;
import cn.mobilizer.channel.util.ErrorCodeMsg;
import cn.mobilizer.channel.util.MobiStringUtils;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	@Autowired
	private WarehouseDao warehouseDao;
	@Autowired
	private CityService cityService;
	@Autowired
	private ClientUserService clientUserService;

	/*
	 * (non-Javadoc)分页
	 * @see cn.mobilizer.channel.posm.service.WarehouseService#queryWarehouseList(java.util.Map)
	 */
	@Override
	public List<Warehouse> queryWarehouseList(Map<String, Object> searchParams)
			throws BusinessException {
		List<Warehouse> list = null;
		try {
			if ((searchParams != null) && (searchParams.size() > 0)) {
				list = warehouseDao.queryWarehouseList(searchParams);
			}
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;

	}
	/*
	 * (non-Javadoc)查询条数
	 * @see cn.mobilizer.channel.posm.service.WarehouseService#queryWarehouseCount(java.util.Map)
	 */
	@Override
	public Integer queryWarehouseCount(Map<String, Object> param)
			throws BusinessException {
		int count = 0;
		try {
			if ((param != null) && (param.size() > 0)) {
				count = warehouseDao.queryWarehouseCount(param);
			}
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return count;

	}
	/*
	 * (non-Javadoc)新增
	 * @see cn.mobilizer.channel.posm.service.WarehouseService#addWarehouse(int, cn.mobilizer.channel.posm.po.Warehouse)
	 */
	@Override
	public void addWarehouse(int clientId, Warehouse warehouse) {
		try {
			warehouse.setClientId(clientId);
			warehouseDao.addWarehouse(warehouse);
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCodeMsg.ERR_ADD);
		}

	}

	@Override
	public boolean saveAll(List<Warehouse> list) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Warehouse showWarehouse(int warehouseId) throws BusinessException {
		Warehouse warehouse = null;
		Map<String, Object> parames = new HashMap<String, Object>();
		try {
			parames.put("warehouseId", warehouseId);
			warehouse = warehouseDao.getWarehousebyid(parames);
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return warehouse;

	}

	@Override
	public void updateWarehouse(int clientId, Warehouse warehouse) {
		warehouse.setClientId(clientId);
		warehouseDao.updateWarehouse(warehouse);

	}

	@Override
	public void deleteWarehouse(int clientId, int warehouseId) {
		Warehouse warehouse = new Warehouse();
		warehouse.setClientId(clientId);
		warehouse.setWarehouseId(warehouseId);
		warehouse.setIsDelete(Constants.IS_DELETE_TRUE);
		warehouseDao.updateWarehouse(warehouse);
	}

	@Override
	public Warehouse findWarehouseName(String WarehouseName, Integer clientId)
			throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("WarehouseName", WarehouseName);
		parames.put("clientId", clientId);
		return warehouseDao.getWarehouseName(parames);

	}

	@Override
	public Warehouse findWarehouseNo(String WarehouseNo, Integer clientId)
			throws BusinessException {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("WarehouseNo", WarehouseNo);
		parames.put("clientId", clientId);
		return warehouseDao.getWarehouseNo(parames);
	}

	@Override
	public Map<String,Object> importWarehouse(List<String[]> values, Map<String, Province> mapProvince,Map<String, ClientStructure> mapDept, Map<String, City> mapCity, Map<String, ClientUser> mapClientUser, Integer clientId)
			throws BusinessException {
		List<String> errStrList = new ArrayList<String>();
		List<String[]> errDataList = new ArrayList<String[]>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == values || values.isEmpty() || values.size() <= 1) {
			returnMap.put("errorMsg", "导入数据不能为空");
			return returnMap;
		}
		String[] titles = values.get(0); //获取导入数据表头
		for (String title : titles) {
			if (StringUtils.isEmpty(title)) {
				returnMap.put("errorMsg", "列名不能为空");
				return returnMap;
			} else if (!MobiStringUtils.contains(
					ImportConstants.WAREHOUSE_TITLE, title)) {
				returnMap.put("errorMsg", "存在不能识别的列名：" + title);
				return returnMap;
			}
		}

		errDataList.add(titles);

		Map<String,Object> wareMap = new HashMap<String,Object>();	
		wareMap.put("clientId", clientId.toString());
		Map<String, Warehouse> mwar= warehouseDao.selectAllWarehouse(wareMap);
		List<Warehouse> insertList = new ArrayList<Warehouse>();
		List<Warehouse> updateList = new ArrayList<Warehouse>();
		//数据校验
		for (int i = 1; i < values.size(); i++) {
			String[] vals = values.get(i);
			String warehouseNo = vals[0];
			String warehouseName = vals[1];
			String structureName = vals[2];
//			String province=vals[3];
			String city = vals[3];
			/*String district=vals[6];*/
			String addr=vals[5];
			String area=vals[6];
			String contact=vals[7];
			String telephoneNo=vals[8];
			
			if(StringUtil.isEmptyString(warehouseNo)){
				errStrList.add("编号不能为空");
				errDataList.add(vals);
				continue;
			}else if(mwar.containsKey(warehouseNo)){
				errStrList.add("编号不能重复");
				errDataList.add(vals);
				continue;
			}
			
//			if(StringUtil.isEmptyString(province)){
//				errStrList.add("省份不能为空");
//				errDataList.add(vals);
//				continue;
//			}else if(!mapProvince.containsKey(province)){
//				errStrList.add("省份不存在");
//				errDataList.add(vals);
//				continue;
//			}
			if(!StringUtil.isNumber(area)){
				errStrList.add("面积只能是数字");
				errDataList.add(vals);
				continue;
			}
			if(StringUtil.isEmptyString(addr)){
				errStrList.add("地址不能为空");
				errDataList.add(vals);
				continue;
			}
			if (StringUtil.isEmptyString(contact)) {
				errStrList.add("联系人不能为空");
				errDataList.add(vals);
				continue;
			}
			if (StringUtil.isEmptyString(telephoneNo)) {
				errStrList.add("联系人电话不能为空");
				errDataList.add(vals);
				continue;
			} else if (StringUtil.isPhone1(telephoneNo)) {
				errStrList.add("电话号格式有误");
				errDataList.add(vals);
				continue;
			} else if (!StringUtil.isNumber(telephoneNo)) {
				errStrList.add("电话号格式有误");
				errDataList.add(vals);
				continue;
			}

			if (StringUtil.isEmptyString(structureName)) {
				errStrList.add("大区不能为空");
				errDataList.add(vals);
				continue;
			} else if (!mapDept.containsKey(structureName)) {
				errStrList.add("大区不存在");
				errDataList.add(vals);
				continue;
			}
			if (StringUtil.isEmptyString(city)) {
				errStrList.add("城市不能为空");
				errDataList.add(vals);
				continue;
			} else if (!mapCity.containsKey(city)) {
				errStrList.add("城市不存在");
				errDataList.add(vals);
				continue;
			}
			if (StringUtil.isEmptyString(warehouseName)) {
				errStrList.add("仓库名不能为空");
				errDataList.add(vals);
				continue;
			}else if(mwar.containsKey(warehouseName)){
				errStrList.add("仓库名不能重复");
				errDataList.add(vals);
				continue;
			}
			ClientStructure clientStructure = mapDept.get(structureName);
			City mcity = mapCity.get(city);
//			Province provincegetid=mapProvince.get(province);

			Warehouse warehouse = new Warehouse();
			warehouse.setClientId(clientId);
			warehouse.setWarehouseName(warehouseName);
			warehouse.setWarehouseNo(warehouseNo);
			warehouse.setClientStructureId(clientStructure
					.getClientStructureId());
			warehouse.setCityId(mcity.getCityId());
			warehouse.setAddr(addr);
			warehouse.setContact(contact);
			warehouse.setTelephoneNo(telephoneNo);
//			warehouse.setProvinceId(provincegetid.getProvinceId());
			if (area.equals(null) || area.equals("")) {

			} else {
				warehouse.setArea(BigDecimal.valueOf(Integer.parseInt(area)));
			}

			Map<String, Object> mapid = new HashMap<String, Object>();
			mapid.put("clientId", clientId);
			mapid.put("WarehouseNo", warehouseNo);
			Integer warehouseNo1 = warehouseDao.getWarehouseId(mapid);
			if (warehouseNo1 > 0) {
				updateList.add(warehouse);
			} else {
				insertList.add(warehouse);
			}
			;
		}
		if (null != insertList && !insertList.isEmpty()) {
			warehouseDao.insterWarehouse(insertList);
		}
		if (null != updateList && !updateList.isEmpty()) {
			warehouseDao.updateSales(updateList);
		}

		Integer successCount = insertList.size() + updateList.size();
		returnMap.put("successCount", successCount);
		returnMap.put("errorCount", errStrList.size());

		returnMap.put("errStrList", errStrList);
		returnMap.put("errDataList", errDataList);

		return returnMap;
	}

	@Override
	public List<Warehouse> queryWarehouseListall(Map<String, Object> searchParams)
			throws BusinessException {
		List<Warehouse> list = null;
		try {
			if ((searchParams != null) && (searchParams.size() > 0)) {
				list = warehouseDao.queryWarehouseListall(searchParams);
			}
		} catch (BusinessException e) {
			throw new BusinessException(ErrorCodeMsg.ERR_QUERY);
		}
		return list;
	}

	@Override
	public List<Warehouse> findWareHouseAjax(Integer clientId) {

		return warehouseDao.findWareHouseAjax(clientId);
	}
	@Override
	public List<Warehouse> findWareHouseAjax(Integer clientId, String name) {
		if(null != clientId && !StringUtil.isBlank(name)){
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != clientId){
				map.put("clientId", clientId);
			}
			if(!StringUtil.isBlank(name)){
				map.put("WarehouseName", name);
			}
			return warehouseDao.findListByid(map);
		}
		return null;
	}
	@Override
	public Warehouse findWareHouseByKey(Integer clientId, Integer wareHouseId) {
		if(null != clientId && null != wareHouseId){
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != clientId){
				map.put("clientId", clientId);
			}
			if(null != wareHouseId){
				map.put("warehouseId", wareHouseId);
			}
			List<Warehouse> list = warehouseDao.findListByid(map);
			if(null != list && list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}
	 
}

