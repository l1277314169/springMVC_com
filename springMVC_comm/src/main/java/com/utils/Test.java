package com.utils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;

import java.io.File;

/**
 * Created by Administrator on 2016-12-24.
 */
public class Test {

    public static void main(String[] args) throws   Exception{
        ClassLoader parent = ClassLoader.getSystemClassLoader();

        GroovyClassLoader loader = new GroovyClassLoader(parent);

        Class groovyClass = loader.parseClass(new File("C:\\Users\\Administrator\\Desktop\\Test.groovy"));

        GroovyObject groovyObject = (GroovyObject)groovyClass.newInstance();

        Object[] param = {123,321};

        int res = (int) groovyObject.invokeMethod("add", param);

        System.out.println("res="+res);
    }


}
