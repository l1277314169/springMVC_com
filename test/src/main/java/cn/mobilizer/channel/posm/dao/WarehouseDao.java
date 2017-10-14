package cn.mobilizer.channel.posm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.base.po.City;
import cn.mobilizer.channel.base.po.WyethContractFeedback;
import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.posm.po.Warehouse;
import cn.mobilizer.channel.posm.vo.WarehouseVo;

@Repository
public class WarehouseDao extends MyBatisDao {
	public WarehouseDao() {
		super("WAREHOUSE");
	}

	public List<Warehouse> queryWarehouseList(Map<String, Object> params) {
		return super.queryForList("findListByParams", params);
	}

	public Map<String, WarehouseVo> queryWarehouseMap(Map<String, Object> map,
			String key) {
		return super.queryForMap("queryWarehouseMap", map, key);
	}

	public List<Warehouse> queryWarehouseListall(Map<String, Object> params) {

		return super.queryForList("findListByParamsall", params);
	}

	public Integer queryWarehouseCount(Map<String, Object> params) {
		return super.get("queryTotalCount", params);
	}

	public Integer insert(Warehouse warehouse) {

		return super.insert("insertSelective", warehouse);
	}

	public int addWarehouse(Warehouse warehouse) {
		return super.insert("insertSelective", warehouse);

	}

	public int updateWarehouse(Warehouse warehouse) {
		return super.update("updateByPrimaryKeySelective", warehouse);
	}

	public Warehouse getWarehousebyid(Map<String, Object> params) {
		return super.get("findListByParams", params);
	}

	public Warehouse getWarehouseName(Map<String, Object> paramMap) {
		return super.get("selectwarehouseName", paramMap);

	}

	public Warehouse getWarehouseNo(Map<String, Object> paramMap) {
		return super.get("selectwarehouseNo", paramMap);

	}

	public Integer getWarehouseId(Map<String, Object> paramMap) {
		return super.get("selectwarehouseId", paramMap);

	}

	public void batchInsertWarehouse(List<Warehouse> warehouse){
		super.insert("batchInsertwarehouse", warehouse);
	}

	public void insterWarehouse(List<Warehouse> warehouseList) {
		for (Warehouse warehouse : warehouseList) {
			super.insert("insertSelective", warehouse);
		}

	}

	public void updateSales(List<Warehouse> warehouseList){
		for (Warehouse warehouse : warehouseList) {
			super.update("updateByPrimaryKeySelective", warehouse);
		}
	}

	public List<Warehouse> findWareHouseAjax(Integer clientId) {
		return super.queryForList("findWareHouseAjax", clientId);
	}
	
	/**
	 * 
	 * @param paramMap
	 * @return
	 * @author：wei.peng
	 * @date 2015年10月9日
	 * <pre>
	 * 	内部SQL 语句
	 * 		<sql id="Query_Column_List">
				<if test="clientId != null and clientId !=''">
					and client_id = #{clientId}
				</if>
				<if test="warehouseId !=null and warehouseId !=''">
					and warehouse_id = #{warehouseId}
				</if>
				<if test="isDelete != null and isDelete != ''">
					and is_delete = #{isDelete}
				</if>
				<if test="WarehouseNo != null and WarehouseNo != ''">
					and warehouse_no = #{WarehouseNo}
				</if>
				<if test="WarehouseName != null and WarehouseName != ''">
					and warehouse_name like CONCAT('%',#{WarehouseName},'%') 
				</if>
			</sql>
	 * </pre>
	 */
	public List<Warehouse> findListByid(Map<String, Object> paramMap) {
		return super.getList("findListByid", paramMap);
	}
	
	
	public Map<String,Warehouse> selectAllWarehouse(Map<String, Object> wareMap){
		return super.queryForMap("selectAllWarehouse",wareMap,"warehouseName");
	}
	
}