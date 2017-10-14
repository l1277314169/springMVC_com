package cn.mobilizer.channel.report.vo;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberFormatUtils {

	public static String formatNumber(double number) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format(number);
	}
	
	public static String formatNumber(double number,int digits) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(digits);
		return nf.format(number);
	}
	
	public static String formatNumber(BigDecimal number) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format(number);
	}
	
	public static String formatNumber(BigDecimal number,int digits) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(digits);
		return nf.format(number);
	}
	
	public static void main(String[] args) {
		System.out.println(NumberFormatUtils.formatNumber(0.01,2));
	}
}
