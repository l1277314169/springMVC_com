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

public class GenerateDao {

    private String url = "jdbc:mysql://localhost:3306/shiro";
    private String username = "root";
    private String password = "123456";
    private String driverClass = "com.mysql.jdbc.Driver";
    private DatabaseMetaData dbMetaData = null;
    private String packageName = "com.csair.test.mybatisRepository;";
    private String packageNameDto = "com.csair.test.dto";
    private String path = "C:\\Users\\liuhonger\\Desktop\\新建文件夹 (4)\\";

    public GenerateDao() {
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

    private void getService() {
        List<String> tableList = getAllTableList();
        for(String tableName : tableList) {
            String table = getFormatString(tableName, true);
            String className = table + "Dao";
            String dto = table;
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageName + "\n\n");
            sb.append("import " + packageNameDto + "." + dto + ";\n");
            sb.append("import java.util.List;\n");
            sb.append("\npublic interface " + className + " {\n\n");

            sb.append("\tpublic long add"+table+"(" + table + " "+table.substring(0, 1).toLowerCase() + table.substring(1)+");\n\n");

            sb.append("\tpublic boolean delete"+table+"(long id);\n\n");

            sb.append("\tpublic long update"+table+"(" + table + " "+table.substring(0, 1).toLowerCase() + table.substring(1)+");\n\n");

            sb.append("\tpublic "+table+" find"+table+"(" + table + " "+table.substring(0, 1).toLowerCase() + table.substring(1)+");\n\n");

            sb.append("\tpublic List<"+table+"> find"+table+"List(" + table + " "+table.substring(0, 1).toLowerCase() + table.substring(1)+");\n\n");

            sb.append("}\n");
//          System.out.println(sb.toString());
            outputToFile(className+".java", sb.toString());
        }
    }

    public static void main(String[] agrs) {
        GenerateDao aa = new GenerateDao();
        aa.getService();
    }


}
