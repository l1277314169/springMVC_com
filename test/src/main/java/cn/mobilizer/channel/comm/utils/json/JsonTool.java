package cn.mobilizer.channel.comm.utils.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.mobilizer.channel.report.po.Filter;

public class JsonTool {

	private final static Log log = LogFactory.getLog(JsonTool.class);
	
	/**
	 * JSON格式转换为对象
	 * @param json
	 * @param classs
	 * @return
	 * @throws Exception
	 */
	public static Object transToObject(String json,Class<?> classs) throws Exception{		
		Field fields[] = classs.getDeclaredFields();
		Object objectCopy = classs.getConstructor(new Class[]{}).newInstance(new Object[]{});
		log.debug("Trans JSON:"+json);
		JSONArray jsonarray = new JSONArray(json);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobj = jsonarray.getJSONObject(i);
			Iterator<?> iter = jsonobj.keys();
			while(iter.hasNext()){
				String key = (String) iter.next();
				for (Field field : fields) {
					if(key.equalsIgnoreCase(field.getName())){
						String firstLetter = field.getName().substring(0, 1).toUpperCase();
						String setMethodName = "set" + firstLetter + field.getName().substring(1);
						Method setMethod = classs.getMethod(setMethodName, new Class[]{field.getType()});
						setMethod.invoke(objectCopy, new Object[]{jsonobj.get(key)});
					}
				}
			}
		 }
		return objectCopy;
	}
	
	/**
	 * JSON转换为集合
	 * @param json
	 * @param classs
	 * @return
	 * @throws Exception
	 */
	public static List<?> transToList(String json,Class<?> classs) throws Exception{
		Field fields[] = classs.getDeclaredFields();
		List<Object> list = new ArrayList<Object>();
		log.debug("Trans JSON:"+json);
		JSONArray jsonarray = new JSONArray(json);
		Map<String,String> keys = getAllKey(jsonarray);
		
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobj = jsonarray.getJSONObject(i);
			Object objectCopy = classs.getConstructor(new Class[]{}).newInstance(new Object[]{});
			for (Field field : fields) {
				String newName = toUpperCaseFirstOne(field.getName());
				if(keys.containsKey(newName) || keys.containsKey(field.getName())){
					String firstLetter = field.getName().substring(0, 1).toUpperCase();
					String setMethodName = "set" + firstLetter + field.getName().substring(1);
					Method setMethod = classs.getMethod(setMethodName, new Class[]{field.getType()});
					//System.out.println("columnName==>"+field.getType());
					
					if(jsonobj.has(newName)){
						setMethod.invoke(objectCopy, new Object[]{jsonobj.get(newName)});
					}else{
						Class<?> _classs = field.getType();
						if(_classs.getName().equals("int")){
							setMethod.invoke(objectCopy, new Object[]{-1});
						}else{
							setMethod.invoke(objectCopy, new Object[]{""});
						}
					}
				}
			}
			list.add(objectCopy);
		 }
		return list;
	}
	
	/**
	 * 获取所有的key
	 * @param jsonarray
	 * @return
	 * @throws Exception
	 */
	private static Map<String,String> getAllKey(JSONArray jsonarray) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobj = jsonarray.getJSONObject(i);
			Iterator<?> iter = jsonobj.keys();
			while(iter.hasNext()){
				String key = (String) iter.next();
				String val = key.toLowerCase();
				map.put(key, val);
			}
		}
		return map;
	}
	
	
	/**
	 * 去重
	 * @param iter
	 * @return
	 */
	private static Map<String,String> removal(Iterator<?> iter){
		Map<String, String> map = new HashMap<String, String>();
		while(iter.hasNext()){
			String key = (String) iter.next();
			String val = key.toLowerCase();
			map.put(key, val);
		}
		return map;
	}
	
	
	/**
	 * 首字母大写
	 * @param s
	 * @return
	 */
	private static String toUpperCaseFirstOne(String s) {
        if(Character.isUpperCase(s.charAt(0))){
        	return s;
        }else{
        	return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
	
	
	/**
	 * 首字母小写
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0))){
        	 return s;
        }else{
        	return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
	
	public static void main(String[] args) throws Exception {
		String json = "[{\"Label\":\"部门\",\"Type\":1,\"Arg\":\"arg_structure_id\"},{\"Label\":\"部门2\",\"Type\":3,\"Arg\":\"arg_structure_id2\"}]";
		//ReportFilter rf = (ReportFilter) JsonTool.transToObject(json,ReportFilter.class);
		//System.out.println(rf.getLabel()+","+rf.getType()+","+rf.getArg());
		
		List<Filter> list = (List<Filter>) JsonTool.transToList(json,Filter.class);
		for (Filter re : list) {
			System.out.println(re.getLabel()+","+re.getType()+","+re.getArg());
		}
		
	}
	
}
