package cn.mobilizer.channel.comm.utils.json;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONSupport {

	@SuppressWarnings({ "unchecked", "static-access" })
	public static <T> T toList(String json,Class<?> classs){
		List<T> list = null;
		try {
			JSONArray jarr = JSONArray.fromObject(json);
			list = (List<T>) jarr.toCollection(jarr,classs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return (T)list;
	}
	
    @SuppressWarnings("unchecked")
	public static <T> T toObject(String json, Class<T> pojoCalss) {
    	Object pojo = null;
    	try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			pojo = JSONObject.toBean(jsonObject, pojoCalss);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
    	return (T)pojo;
	}
}
