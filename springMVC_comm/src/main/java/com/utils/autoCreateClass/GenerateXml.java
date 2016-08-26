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

public class GenerateXml {

    private String url = "jdbc:mysql://localhost:3306/shiro";
    private String username = "root";
    private String password = "123456";
    private String driverClass = "com.mysql.jdbc.Driver";
    private DatabaseMetaData dbMetaData = null;
    private String packageNameMybatisDao = "com.csair.test.mybatisRepository";
    //  private String path = "C:\\Users\\ppt\\Desktop\\test\\";
    private String path = "C:\\Users\\liuhonger\\Desktop\\新建文件夹 (4)\\";

    /**
     * @Description: 获取表对应的所有列
     * @author: ppt
     * @date: 2015-3-16 上午10:13:17
     * @param tableName
     * @return: void
     */
    public void getTableColumns(String tableName) {
        try {
            ResultSet resultSetColumn = dbMetaData.getColumns(null, null,
                    tableName, null);
            String className = getFormatString(tableName, true);//表名
            String fileName = className + "Mapper";//文件名
            String thisFileName = className + "MybatisDao";//接口地址
            StringBuffer header = new StringBuffer(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            header.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
            header.append("<mapper namespace=\"");
            header.append(packageNameMybatisDao+"."+thisFileName);
            header.append("\">\n\n");
            StringBuffer footer = new StringBuffer("\n</mapper>");
            StringBuffer add = new StringBuffer();
            //增加数据
            add.append("\t<insert id=\"addEntity\" parameterType=\""+className+"\">\n");
            String insert = "\t\tinsert into " + tableName + "(";
            String column = "";
            String values = "";
            //删除数据
            StringBuffer delete = new StringBuffer();
            delete.append("\t<update id=\"deleteEntity\" parameterType=\"long\">\n");
            delete.append("\t\tupdate " + tableName + " set status = -1 where id = #{id}\n");
            delete.append("\t</update>\n\n");
            //更新数据
            StringBuffer update = new StringBuffer();
            String updateConent = "";
            update.append("\t<update id=\"updateEntity\" parameterType=\""+className+"\">\n");
            update.append("\t\tupdate " + tableName + " set ");
            //查找数据
            StringBuffer select = new StringBuffer();
            select.append("\t<select id=\"findEntity\" parameterType=\""+className+"\" resultType=\""+className+"\">\n");
            String selectContent = "\t\tselect\n";
            //查找数据list
            StringBuffer selectList = new StringBuffer();
            selectList.append("\t<select id=\"findEntityList\" parameterType=\""+className+"\" resultType=\""+className+"\">\n");
            while (resultSetColumn.next()) {
                String columnName = resultSetColumn.getString("COLUMN_NAME");
                String oldColumnName = columnName;
                columnName = this.getFormatString(columnName, false);
                //增加数据
                if("ID".equals(columnName.toUpperCase())) {
                    continue;
                }
                column += columnName +",";
                values += "#{" + columnName + "},";
                //删除数据

                //更新数据
                updateConent += columnName + "=#{" + columnName +"},";
                //查找数据
                selectContent += "\t\t"+oldColumnName + " AS " + columnName + ",\n";
            }
            selectContent = selectContent.substring(0, selectContent.length()-2);
            selectContent += "\n\t\tfrom " + tableName + "\n";
            select.append(selectContent);
            select.append("\t</select>\n\n");
            selectList.append(selectContent);
            selectList.append("\t</select>\n\n");
            updateConent = updateConent.substring(0,updateConent.length()-1);
            update.append(updateConent);
            update.append(" where id = #{id}\n");
            update.append("\t</update>\n\n");
            column = column.substring(0, column.length()-1);
            values = values.substring(0,values.length()-1);
            String statement = insert + column + ") values(" + values + ")\n";
            add.append(statement);
            add.append("\t</insert>\n\n");
            header.append(add);
            header.append(delete);
            header.append(update);
            header.append(select);
            header.append(selectList);
            header.append(footer);
//          System.out.println(header.toString());
            this.outputToFile(fileName+".xml", header.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GenerateXml() {
        try {
            Class.forName(driverClass);
            Connection conn = DriverManager.getConnection(url, username, password);
            dbMetaData = conn.getMetaData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取所以的表
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


    public static void main(String[] agrs) {
        GenerateXml aa = new GenerateXml();
        List<String> tableList = aa.getAllTableList();
        for(String tableName : tableList) {
            aa.getTableColumns(tableName);
        }
//      aa.getTableColumns("loan_info");
    }

}
