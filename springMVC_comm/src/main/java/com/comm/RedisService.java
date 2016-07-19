package com.comm;

/**
 * Created by liuhonger on 2016/7/18.
 */
public interface RedisService<T,E> {

    public T callBack(E e);
}
