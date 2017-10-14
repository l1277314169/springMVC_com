package com.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhonger on 2016/8/1.
 */
public class Student{
    private int id;

    private String name;

    private Integer age;

    private Date birthday;



    private static final String username = "aa";

    public Student(int id, String name, Integer age, Date birthday) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", age=" + age
                + ", birthday=" + birthday + "]";
    }

    public Student() {
    }

    public static void main(String[] args) {


//        File srcFile = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\aa.txt");
//        File dstFile = new File("C:\\Intel\\bb.txt");
//        BufferedInputStream in = null;
//        BufferedOutputStream out = null;
//        try {
//            in = new BufferedInputStream(new FileInputStream(srcFile));
//            out = new BufferedOutputStream(new FileOutputStream(dstFile));
//            int offset = 0;
//            byte[] b = new byte[10];
//            int byteread = -1;
//            while((byteread=in.read(b)) != -1) {
//                out.write(b,0,byteread);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if(in != null) {
//                try {
//                    in.close();
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if(out != null) {
//                try {
//                    out.close();
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}
