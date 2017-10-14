package cn.mobilizer.channel.util;

public class VersionHelper {
	public int compare(String oldVersion, String newVersion) {
		if (oldVersion == null && newVersion == null)
			return 0;
		else if (oldVersion == null)
			return -1;
		else if (newVersion == null)
			return 1;

		String[] olds = oldVersion.split("[^a-zA-Z0-9]+"), news = newVersion.split("[^a-zA-Z0-9]+");

		int i1, i2, i3;

		for (int ii = 0, max = Math.min(olds.length, news.length); ii <= max; ii++) {
			if (ii == olds.length)
				return ii == news.length ? 0 : -1;
			else if (ii == news.length)
				return 1;

			try {
				i1 = Integer.parseInt(olds[ii]);
			} catch (Exception x) {
				i1 = Integer.MAX_VALUE;
			}

			try {
				i2 = Integer.parseInt(news[ii]);
			} catch (Exception x) {
				i2 = Integer.MAX_VALUE;
			}

			if (i1 != i2) {
				return i1 - i2;
			}

			i3 = olds[ii].compareTo(news[ii]);

			if (i3 != 0)
				return i3;
		}

		return 0;
	}
}
