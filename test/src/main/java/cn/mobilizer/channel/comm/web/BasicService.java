package cn.mobilizer.channel.comm.web;

import java.util.List;
import java.util.Map;
/*
 * 基础接口类，定义常用的 查询
 */
public interface BasicService<T> {
	public List<T> getObjectList(Map<String, Object> parames);
	public T getObject(Map<String, Object> parames);
}
