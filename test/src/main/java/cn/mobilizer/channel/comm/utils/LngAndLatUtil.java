package cn.mobilizer.channel.comm.utils;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LngAndLatUtil { 

	public static Map<String, Double> getBaiDuLngAndLatWith(String address){
		Map<String, Double> map = new HashMap<String, Double>();
		String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=你自己的ak值";
		String json = loadJSON(url);
		JSONObject obj = JSONObject.fromObject(json);
		if (obj.get("status").toString().equals("0")) {
			double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
			double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
			map.put("lng", lng);
			map.put("lat", lat);
			// System.out.println("经度："+lng+"---纬度："+lat);
		} else {
			// System.out.println("未找到相匹配的经纬度！");
		}
		return map;
	}

	public static String[] switchLngAndLatGPS2BaiDu(String longitude, String latitude){
		String[] arr= new String[2];
		String url = "http://api.map.baidu.com/geoconv/v1/?coords="+longitude+","+latitude+"&from=1&to=5&output=json&ak=D93a31278160293ed962f7a4c40f1fa5";
		String json = loadJSON(url);
		JSONObject obj = JSONObject.fromObject(json);
		if (obj.get("status").toString().equals("0")) {
			String lng = obj.getJSONArray("result").getJSONObject(0).getString("x");
			String lat = obj.getJSONArray("result").getJSONObject(0).getString("y");
			
			arr[0] = lng;
			arr[1] = lat;
			// System.out.println("经度："+lng+"---纬度："+lat);
		} else {
			// System.out.println("未找到相匹配的经纬度！");
		}
		return arr;
	}
	
	public static String loadJSON(String url){
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {} catch (IOException e) {}
		return json.toString();
	}
}
