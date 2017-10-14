package cn.mobilizer.channel.report.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import cn.mobilizer.channel.report.po.Column;

public class FerreroReportSupport {

	public static Map<String, String> MAPS = new HashMap<String, String>();
	public static Map<String, String> LEGEND = new HashMap<String, String>();
	
	static{
		MAPS.put("65", "0@品类"); //销售分析
		LEGEND.put("65","1@年度");
		
		MAPS.put("72", "0@品类");
		LEGEND.put("72","1@区域");
		
		MAPS.put("73", "0@区域");
		LEGEND.put("73","1@品类");
		
		MAPS.put("66", "0@品牌");
	}
	
	public static String getKey(String code){
		return MAPS.get(code);
	}
	
	public static String getLegendKey(String code){
		return LEGEND.get(code);
	}
	
	public static List<Column> getColumn(List<Column> columns,String key){
		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);
			if(column.getArgName().equals(key)){
				columns.remove(i);
				break;
			}
		}
		return columns;
	}
	
	public static List<TreeMap<String, String>> getDataList(){
		List<TreeMap<String, String>> lists = new ArrayList<TreeMap<String, String>>();
		lists.add(create("Kinder健达","Y1314"));
		lists.add(create("Kinder健达","Y1313"));
		lists.add(createGR("Kinder健达","GR%"));
		
		lists.add(create("Ferrero TTL","Y1314"));
		lists.add(create("Ferrero TTL","Y1313"));
		lists.add(createGR("Ferrero TTL","GR%"));
		
		lists.add(create("Kinder","Y1314"));
		lists.add(create("Kinder","Y1313"));
		lists.add(createGR("Kinder","GR%"));
		
		return lists;
	}
	
	public static List<TreeMap<String, String>> getHeads(){
		List<TreeMap<String, String>> lists = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> m1 = new TreeMap<String, String>();
		m1.put("sheet_name", "销售分析@品类,年度,Universe,W01,W02,W03,W04,W05,当月均周产,上月均周产,当财年均周产");
		lists.add(m1);
		return lists;
	}
	
	public static TreeMap<String, String> createGR(String key,String year){
		TreeMap<String, String> m1 = new TreeMap<String, String>();
		m1.put("品类", key);
		m1.put("年度", year);
		m1.put("Universe",random(2)+"%");
		m1.put("W01", random(2)+"%");
		m1.put("W02", random(2)+"%");
		m1.put("W03", random(2)+"%");
		m1.put("W04", random(2)+"%");
		m1.put("W05", random(2)+"%");
		m1.put("当月均周产", random(2)+"%");
		m1.put("上月均周产", random(2)+"%");
		m1.put("当财年均周产", random(2)+"%");
		return m1;
	}
	
	public static TreeMap<String, String> create(String key,String year){
		TreeMap<String, String> m1 = new TreeMap<String, String>();
		m1.put("品类", key);
		m1.put("年度", year);
		m1.put("Universe",random(4));
		m1.put("W01", random(4));
		m1.put("W02", random(4));
		m1.put("W03", random(4));
		m1.put("W04", random(4));
		m1.put("W05", random(4));
		m1.put("当月均周产", random(4));
		m1.put("上月均周产", random(4));
		m1.put("当财年均周产", random(4));
		return m1;
	}
	
	public static String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(10);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }
}
