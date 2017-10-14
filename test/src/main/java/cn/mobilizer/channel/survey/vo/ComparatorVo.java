package cn.mobilizer.channel.survey.vo;

import java.util.Comparator;

public class ComparatorVo implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		String[] so1 = o1.toString().split("@");
		String[] so2 = o2.toString().split("@");

		int io1 = Integer.parseInt(so1[3]);
		int io2 = Integer.parseInt(so2[3]);
		int c = io1 - io2;
		return c;
	}

}
