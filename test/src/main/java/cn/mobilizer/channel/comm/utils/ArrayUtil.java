package cn.mobilizer.channel.comm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author yeeda.tian
 */
public class ArrayUtil {

	public static int[] arraySubtract(int[] array1,int[] array2){
		ArrayList<Integer> list = new ArrayList<Integer>();
		// 选出属于数组1但不属于数组2的元素
		for ( int i = 0 ; i < array1.length ; ++i ) {
			boolean bContained = false;
			for ( int j = 0 ; j < array2.length ; ++j ) {
				if (array1[i] == array2[j]) {
					bContained = true;
					break;
				}
			}
			if (!bContained) {
				list.add(array1[i]);
			}
		}

		int res[] = new int[list.size()];
		for ( int i = 0 ; i < list.size() ; ++i )
			res[i] = list.get(i);
		return res;
	}
	
	/**
	 * 返回属于数组1但不属于数组2的元素
	 * @param array1
	 * @param array2
	 * @return String[]
	 */
	public static String[] arraySubtract(String[] array1,String[] array2){
		ArrayList<String> list = new ArrayList<String>();
		String[] res = null;
		if(array1 == null || array1.length <= 0) {
			
		} else if (array2 == null || array2.length <= 0){
			res = array1;
		} else {
			for ( int i = 0 ; i < array1.length ; ++i ) {
				boolean bContained = false;
				for ( int j = 0 ; j < array2.length ; ++j ) {
					if (array1[i].equals(array2[j])) {
						bContained = true;
						break;
					}
				}
				if (!bContained) {
					list.add(array1[i]);
				}
			}
			if(list != null && list.size() > 0) {
				res= new String[list.size()];
				for ( int i = 0 ; i < list.size() ; ++i )
					res[i] = list.get(i);
			}
		}
		return res;
	}
	
	/**
	 * 返回属于数组1但不属于数组2的元素
	 * @param array1
	 * @param array2
	 * @return String
	 */
	public static String arraySubtract2Str(String[] array1,String[] array2){
		String res = "";
		if(array1 == null || array1.length <= 0) {
			
		} else if (array2 == null || array2.length <= 0){
			for ( int i = 0 ; i < array1.length ; ++i ) {
				res += array1[i] +",";
			}
		} else {
		
			for ( int i = 0 ; i < array1.length ; ++i ) {
				boolean bContained = false;
				for ( int j = 0 ; j < array2.length ; ++j ) {
	//				System.out.println(array1[i]+"====="+array2[j]);
					if (array1[i].equals(array2[j])) {
						bContained = true;
						break;
					}
				}
				if (!bContained) {
					res += array1[i] +",";
				}
			}
		}
		return res;
	}
	

	public static String arrayIntersect2Strddddddd(String[] array1,String[] array2){
		String res = "";
		
		return res;
	}
	
	/**
	 * 两个数组的交集
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static String arrayIntersect2Str(String[] arr1,String[] arr2){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		String rs = "";
		for ( String str : arr1 ) {
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}
		for ( String str : arr2 ) {
			if (map.containsKey(str)) {
				map.put(str, Boolean.TRUE);
			}
		}
		for ( Entry<String, Boolean> e : map.entrySet() ) {
			if (e.getValue().equals(Boolean.TRUE)) {
				rs += e.getKey()+",";
			}
		}

		return rs;
	}   
	
	/**
	 * 两个数组的交集
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static String[] arrayIntersect(String[] arr1,String[] arr2){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		LinkedList<String> list = new LinkedList<String>();
		for ( String str : arr1 ) {
			if (!map.containsKey(str)) {
				map.put(str, Boolean.FALSE);
			}
		}
		for ( String str : arr2 ) {
			if (map.containsKey(str)) {
				map.put(str, Boolean.TRUE);
			}
		}
		for ( Entry<String, Boolean> e : map.entrySet() ) {
			if (e.getValue().equals(Boolean.TRUE)) {
				list.add(e.getKey());
			}
		}
		
		String[] result = {};
		return list.toArray(result);
	}   
	
	/**
	 * 两个数组的合集
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static String[] arrayMerge(String[] arr1,String[] arr2){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		LinkedList<String> list = new LinkedList<String>();
		for ( String str : arr1 ) {
				map.put(str, Boolean.TRUE);
		}
		for ( String str : arr2 ) {
				map.put(str, Boolean.TRUE);
		}
		for ( Entry<String, Boolean> e : map.entrySet() ) {
				list.add(e.getKey());
		}
		
		String[] result = {};
		return list.toArray(result);
	}
	
	/**
	 * 去除数组中重复的记录
	 * @param arr
	 * @return
	 */
	public static String[] arrayUnique(String[] arr){
		List<String> list = new LinkedList<String>();  
	    for(int i = 0; i < arr.length; i++) {  
	        if(!list.contains(arr[i])) {  
	            list.add(arr[i]);  
	        }  
	    }  
	    return (String[])list.toArray(new String[list.size()]);  
	}	
}
