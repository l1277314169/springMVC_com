package cn.mobilizer.channel.systemManager.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.mobilizer.channel.comm.mybatis.MyBatisDao;
import cn.mobilizer.channel.systemManager.po.MobileVersion;

@Repository
public class MobileVersionDao extends MyBatisDao {

	public MobileVersionDao() {
		super("MOBILE_VERSION");
	}

	public MobileVersion getNewMobileVersion(Integer clientId, Integer clientUserId, String version, Integer appCode) {
		MobileVersion mv = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clientId", clientId);
		paramMap.put("clientUserId", clientUserId);
		paramMap.put("version", version);
		paramMap.put("appCode", appCode);
		List<MobileVersion> rlist = super.queryForList("getNewVersion", paramMap);
		if (rlist != null && rlist.size() > 0){
			Collections.sort(rlist, new VersionStringComparator());
			//排序，版本号依次从小到大排列
			MobileVersion nv = rlist.get(rlist.size() - 1);
			if(compareVersion(nv.getVersion(), version) > 0)
				mv = nv;
		}
		return mv;
	}

	private static class VersionStringComparator implements Comparator<MobileVersion> {
		public int compare(MobileVersion v1, MobileVersion v2) {
			return compareVersion(v1.getVersion(), v2.getVersion());
		}
	}
	/**
	 * s1 > s2 re = 1, s1 = s2, re = 0, s1 < s2, re = -1
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static int compareVersion(String s1, String s2) {
		if (s1 == null && s2 == null)
			return 0;
		else if (s1 == null)
			return -1;
		else if (s2 == null)
			return 1;

		String[] arr1 = s1.split("[^a-zA-Z0-9]+"), arr2 = s2.split("[^a-zA-Z0-9]+");

		int i1, i2, i3;

		for (int ii = 0, max = Math.min(arr1.length, arr2.length); ii <= max; ii++) {
			if (ii == arr1.length)
				return ii == arr2.length ? 0 : -1;
			else if (ii == arr2.length)
				return 1;

			try {
				i1 = Integer.parseInt(arr1[ii]);
			} catch (Exception x) {
				i1 = Integer.MAX_VALUE;
			}

			try {
				i2 = Integer.parseInt(arr2[ii]);
			} catch (Exception x) {
				i2 = Integer.MAX_VALUE;
			}

			if (i1 != i2) {
				return i1 - i2;
			}

			i3 = arr1[ii].compareTo(arr2[ii]);

			if (i3 != 0)
				return i3;
		}

		return 0;
	}
	
//	public static void main(String[] args) {
//		int i = compareVersion("1.10.10", "1.0.1");
//		System.out.println(i);
//	}

	public Integer queryMobileVersionCount(Map<String, Object> parames) {
		return super.get("queryTotalCount", parames);
	}

	public int insert(MobileVersion mobileVersion) {
		return super.insert("insertSelective", mobileVersion);

	}

	public List<MobileVersion> findMobileVersionList(Object Params) {
		return super.queryForList("selectByParams", Params);

	}

	public int delete(Integer id) {
		return super.delete("deleteByPrimaryKey", id);
	}

	public int update(MobileVersion mobileVersion) {

		return super.update("updateByPrimaryKeySelective", mobileVersion);
	}

	public MobileVersion findByPrimaryKey(Integer id) {
		return super.get("selectByPrimaryKey", id);

	}
	
	public String queryUrlByParams(Map<String, Object> parames){
		return super.get("queryUrlByParams", parames);
	}

}
