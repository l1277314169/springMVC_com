package cn.mobilizer.channel.report.vo;

public class ArraysUtils {
	
	public static double getMaxValue(Object[] v){
		double max = 0;
		try {
			max = Double.parseDouble(v[0].toString());
			for (int i = 0; i < v.length; i++) {
				double tv = Double.parseDouble(v[i].toString());
				if (tv > max){
					max = tv;
				}
			}
		} catch (NumberFormatException e) {
			//e.printStackTrace();
			return 0;
		}
		return max;
	}
	
	public static double getMinValue(Object[] v){
		double min = 0;
		try {
			min = Double.parseDouble(v[0].toString());
			for (int i = 0; i < v.length; i++) {
				double tv = Double.parseDouble(v[i].toString());
				if (tv < min){
					min = tv;
				}
			}
		} catch (NumberFormatException e) {
			//e.printStackTrace();
			return 0;
		}
		return min;
	}
	
	public static void main(String args[]) {
		
	}
}
