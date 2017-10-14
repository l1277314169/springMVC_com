package cn.mobilizer.channel.survey.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * 集合分组
 */
public class CollectionGroup {

	/**
	 * 分組依據接口，用于集合分組時，獲取分組依據
	 * 
	 * @title GroupBy
	 * @date 2013-4-23
	 */
	public interface GroupBy<T> {
		T groupby(Object obj);
	}

	/**
	 * 
	 * @param colls
	 * @param gb
	 * @return
	 */
	public static final <T extends Comparable<T>, D> TreeMap<T, List<D>> group(Collection<D> colls, GroupBy<T> gb) {
		if (colls == null || colls.isEmpty()) {
			return null;
		}
		if (gb == null) {
			return null;
		}
		Iterator<D> iter = colls.iterator();
		TreeMap<T, List<D>> map = new TreeMap<T, List<D>>(new ComparatorVo());
		while (iter.hasNext()) {
			D d = iter.next();
			T t = gb.groupby(d);
			if (map.containsKey(t)) {
				map.get(t).add(d);
			} else {
				List<D> list = new ArrayList<D>();
				list.add(d);
				map.put(t, list);
			}
		}
		return map;
	}
	
	public static void main(String[] args) {
		/*Map<Integer, List<Sku>> skuMaps = CollectionGroup.group(skus, new GroupBy<Integer>(){
				public Integer groupby(Object obj) {
					Sku d = (Sku)obj;
		            return d.getMappingId();
		        }
			});*/
	}
	
}
