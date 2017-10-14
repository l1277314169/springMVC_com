package cn.mobilizer.channel.survey.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 集合分组
 */
public class DefaultCollectionGroup {

	public interface GroupBy<T> {
        T groupby(Object obj) ;
    }   
     
    /**
     * 
     * @param colls
     * @param gb
     * @return
     */
    public static final <T extends Comparable<T> ,D> Map<T ,List<D>> group(Collection<D> colls ,GroupBy<T> gb){
        if(colls == null || colls.isEmpty()) {
            return null ;
        }
        if(gb == null) {
            return null ;
        }
        Iterator<D> iter = colls.iterator() ;
        Map<T ,List<D>> map = new HashMap<T, List<D>>() ;
        while(iter.hasNext()) {
            D d = iter.next() ;
            T t = gb.groupby(d) ;
            if(map.containsKey(t)) {
                map.get(t).add(d) ;
            } else {
                List<D> list = new ArrayList<D>() ;
                list.add(d) ;
                map.put(t, list) ;
            }
        }
        return map ;
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
