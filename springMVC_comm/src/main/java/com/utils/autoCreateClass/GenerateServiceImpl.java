package com.utils.autoCreateClass;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class GenerateServiceImpl {

    private String url = "jdbc:mysql://localhost:3306/shiro";
    private String username = "root";
    private String password = "123456";
    private String driverClass = "com.mysql.jdbc.Driver";
    private DatabaseMetaData dbMetaData = null;
    private String packageName = "com.csair.test.service.impl;";
    private String packageNameService = "com.csair.test.service";
    private String packageNameDto = "com.csair.test.dto";
    private String packageNameMybatis = "com.csair.test.mybatisRepository";
    private String path = "C:\\Users\\liuhonger\\Desktop\\新建文件夹 (4)\\";

    public GenerateServiceImpl() {
        try {
            Class.forName(driverClass);
            Connection conn = DriverManager.getConnection(url, username, password);
            dbMetaData = conn.getMetaData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取所有的表
     * @author: ppt
     * @date: 2015-3-16 上午10:12:57
     * @return: void
     */
    public List<String> getAllTableList() {
        List<String> tableList = new ArrayList<String>();
        try {
            String[] types = { "TABLE" };
            ResultSet rs = dbMetaData.getTables(null, null, "%", types);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");  //表名
                tableList.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    /**
     * 处理字符串，去掉下划线“_”，并且把下划线的下一个字符变大写，flag为true，表示首字母要大写
     * @param name
     * @param flag
     * @return
     */
    private String getFormatString(String name, boolean flag) {
        name = name.toLowerCase();
        String[] nameTemp = name.split("_");
        StringBuffer buffer = new StringBuffer();
        for(String str : nameTemp) {
            String head = str.substring(0, 1).toUpperCase();
            String tail = str.substring(1);
            buffer.append(head+tail);
        }
        StringBuffer result = null;
        if(!flag) {
            result = new StringBuffer();
            String head = buffer.substring(0, 1).toLowerCase();
            String tail = buffer.substring(1);
            result.append(head+tail);
            return result.toString();
        }
        return buffer.toString();
    }
    /**
     * 把String内容写到文件
     * @param fileName
     * @param content
     */
    private void outputToFile(String fileName, String content) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(path+fileName);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        byte[] b = content.getBytes();
        try {
            os.write(b);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getServiceImpl() {
        List<String> tableList = getAllTableList();
        for(String tableName : tableList) {
            String table = getFormatString(tableName, true);
            String mybatis = table + "Dao";
            String dto = table;
            String service = table + "Service";
            String className = service + "Impl";
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageName + "\n\n");
            sb.append("import " + packageNameDto + "." + dto + ";\n");
            sb.append("import " + packageNameMybatis + "." + mybatis + ";\n");
            sb.append("import " + packageNameService + "." + service + ";\n");
            sb.append("import org.apache.log4j.Logger;\n");
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
            sb.append("import org.springframework.stereotype.Service;\n");
            sb.append("import java.util.List;\n");
            //sb.append("@Service(\""+service.substring(0,1).toLowerCase()+service.substring(1)+"\")\n");
            sb.append("@Service()");
            sb.append("public class " + className + " implements "+ service +" {\n\n");

            sb.append("\tprivate static final Log log = LogFactory.getLog("+className+".class);\n");
            String instance = mybatis.substring(0,1).toLowerCase() + mybatis.substring(1);
            sb.append("\t@Autowired\n");
            sb.append("\tprivate "+mybatis+" " + instance +";\n");

            sb.append("\t@Override\n");
            sb.append("\tpublic long addEntity(" + table + " dto) {\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"addEntity\");\n");
            sb.append("\t\t\t"+instance+".addEntity(dto);\n");
            sb.append("\t\t\treturn 0;\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.info(\"addEntity异常\");\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tthrow e;\n");
            sb.append("\t\t}\n");
            sb.append("\t}\n");

            sb.append("\t@Override\n");
            sb.append("\tpublic boolean deleteEntity(long id) {\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"deleteEntity\");\n");
            sb.append("\t\t\t"+instance+".deleteEntity(id);\n");
            sb.append("\t\t\treturn false;\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.info(\"deleteEntity异常\");\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tthrow e;\n");
            sb.append("\t\t}\n");
            sb.append("\t}\n");

            sb.append("\t@Override\n");
            sb.append("\tpublic long updateEntity(" + table + " dto) {\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"updateEntity\");\n");
            sb.append("\t\t\t"+instance+".updateEntity(dto);\n");
            sb.append("\t\t\treturn 0;\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.info(\"updateEntity异常\");\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tthrow e;\n");
            sb.append("\t\t}\n");
            sb.append("\t}\n");

            sb.append("\t@Override\n");
            sb.append("\tpublic "+table+" findEntity(" + table + " dto) {\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"findEntity\");\n");
            sb.append("\t\t\treturn "+instance+".findEntity(dto);\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.info(\"findEntity异常\");\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tthrow e;\n");
            sb.append("\t\t}\n");
            sb.append("\t}\n");

            sb.append("\t@Override\n");
            sb.append("\tpublic List<"+table+"> findEntityList(" + table + " dto) {\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"findEntityList\");\n");
            sb.append("\t\t\treturn "+instance+".findEntityList(dto);\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.info(\"findEntityList异常\");\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t\tthrow e;\n");
            sb.append("\t\t}\n");
            sb.append("\t}\n");

            sb.append("}\n");
//          System.out.println(sb.toString());
            outputToFile(className+".java", sb.toString());
        }
    }

    public static void main(String[] agrs) {
        GenerateServiceImpl aa = new GenerateServiceImpl();
        aa.getServiceImpl();
    }

}
