package cn.mobilizer.channel.image.vo;

import java.util.Comparator;

public class ComparatorForImage implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		String so1 = o1.toString().replaceAll("-", "");
		String so2 = o2.toString().replaceAll("-", "");
		
		int io1 = Integer.parseInt(so1);
		int io2 = Integer.parseInt(so2);
		int c = io1 - io2;
		return c;
	}
}