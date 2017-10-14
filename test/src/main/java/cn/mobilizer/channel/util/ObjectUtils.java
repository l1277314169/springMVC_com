package cn.mobilizer.channel.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectUtils {

	/**
	 * 计算一个对象所占字节数
	 * @param o对象，该对象必须继承Serializable接口即可序列化
	 * @return
	 * @throws IOException
	 */
	public static int getSize(final Object o){
		if (o == null) { return 0; }
		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream(4096);
			ObjectOutputStream out = new ObjectOutputStream(buf);
			out.writeObject(o);
			out.flush();
			buf.close();
			return buf.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
