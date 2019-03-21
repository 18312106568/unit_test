/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import com.mysql.jdbc.Field;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestJDBC {
    
    @Test
    public void testConn() throws SQLException{
        String dbName = "test";
        Connection conn = MysqlUtils.getConn(dbName);
        if(conn==null){
            System.out.println("数据库连接失败！");
        }
        System.out.println(conn.getSchema());
        System.out.println(conn);
        String sql = "select column_name, column_comment from information_schema.columns where table_name = 'tb_user' ;";
        Statement  statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
//        while(rs.next()){
//            System.out.println(rs.getString("FIELD"));
//            System.out.println(rs.getString("TYPE"));
//            System.out.println(rs.getString("NULL"));
//            System.out.println(rs.getString("KEY"));
//            System.out.println(rs.getString("EXTRA"));
//            System.out.println();
//        }
        System.out.println(this.getClass().getClassLoader().getResource(""));

        ResultSetMetaData metaData = rs.getMetaData();
        //System.out.println(metaData);
        //com.mysql.jdbc.ResultSetMetaData
        for(int i=0;i<metaData.getColumnCount();i++){
            System.out.println(
                    metaData.getColumnName(i+1)+"-"+metaData.getColumnTypeName(i+1)+"-"
                            +metaData.getColumnClassName(i+1));
            
        }
        System.out.println(metaData.getClass());
        System.out.println(metaData);
        try {
            TemplateGenerator.generatorJava(MysqlExcutor.createTableEntity(conn,dbName, "tb_user"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "123abc123abc";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    public static class MysqlExcutor{
        
        private final static String TABLE_INFO_SQL = "select table_name, table_comment from information_schema.tables where table_schema = '%s' and table_name = '%s'";
        
        private final static String COLUMN_INFO_SQL = "select * from  %s limit 0,1";
        
        private final static String COMMENT_INFO_SQL = "select column_name, column_comment from information_schema.columns where table_schema = '%s' and table_name = '%s' ;";
        
        private final static String NO_NEED_IMPORT = "java\\.lang.*";
        
        public static MysqlTable createTableEntity(Connection conn,String dbName,String tableName) throws SQLException{
            MysqlTable mysqlTable = new MysqlTable();
            mysqlTable.setClassName(StringUtls.upperCase(StringUtls.changeHungToCame(tableName)));
            mysqlTable.setTableName(tableName);
            Map<String,MysqlColumn> columnMap = new HashMap();
            Map<String,String> importMap = new HashMap();
            
            //获取数据库列信息
            ResultSetMetaData columnMetaData = getColumnInfo(conn, tableName);
            for(int i=0;i<columnMetaData.getColumnCount();i++){
                String columnType = columnMetaData.getColumnClassName(i+1);
                MysqlColumn mysqlColumn = new MysqlColumn();
                mysqlColumn.setName(StringUtls.changeHungToCame(columnMetaData.getColumnName(i+1)));
                mysqlColumn.setType(converToSingleName(columnType));
                mysqlColumn.setColumnName(columnMetaData.getColumnName(i+1));
                columnMap.put(mysqlColumn.getName(),mysqlColumn );
                if(!columnType.matches(NO_NEED_IMPORT)){
                    importMap.put(columnType, columnType);
                }
            }
            
            //获取列注释
            ResultSet commentRs = getCommentInfo(conn,dbName,tableName);
            while(commentRs.next()){
                String key = commentRs.getString("column_name");
                String comment = commentRs.getString("column_comment");
        
                columnMap.get(key).setComment(comment);
            }
            
            //获取表注释
            ResultSet tableRs = getTablefo(conn,dbName, tableName);

            while(tableRs.next()){
                String comment = tableRs.getString("table_comment");
                mysqlTable.setTableComment(comment);
            }
            
            mysqlTable.setImportList(importMap.values());
            mysqlTable.setColumns(columnMap.values());
            return mysqlTable;
        }
        
        public static ResultSetMetaData getColumnInfo(Connection conn,String tableName) throws SQLException{
            return conn.createStatement()
                    .executeQuery(String.format(COLUMN_INFO_SQL, tableName))
                    .getMetaData();
        }
        
        public static ResultSet getCommentInfo(Connection conn,String dbName,String tableName) throws SQLException{
             return conn.createStatement()
                    .executeQuery(String.format(COMMENT_INFO_SQL,dbName, tableName));
        }
        
        public static ResultSet getTablefo(Connection conn,String dbName,String tableName) throws SQLException{
             return conn.createStatement()
                    .executeQuery(String.format(TABLE_INFO_SQL,dbName, tableName));
        }
        
        public static String converToSingleName(String classFullName){
            if(classFullName.lastIndexOf(".")>0){
                return classFullName.substring(classFullName.lastIndexOf(".")+1);
            }
            return classFullName;
        }
        
        
    }
    

    public static class TemplateGenerator{
        public static void generatorJava(MysqlTable table) throws IOException, TemplateException {
            String path = System.getProperty("user.dir");
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("bean",table);
            Configuration cfg = getConfiguration(path);
            Template temp = cfg.getTemplate("");
            temp.process(dataMap,new OutputStreamWriter(System.out));
        }

        public static Configuration getConfiguration(String path) throws IOException {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
            cfg.setDirectoryForTemplateLoading(new File(path));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            return cfg;
        }

    }

    public static class StringUtls{
       /**
        * 首字母大写
        * @param str
        * @return 
        */
       public static String upperCase(String str) {  
           return str.substring(0, 1).toUpperCase() + str.substring(1);  
       }  

       /**
        * 峰驼式转匈牙利命名
        * 
        * @param cameString
        * @return 
        */
       public static String changeCameToHung(String cameString){
           StringBuilder sb = new StringBuilder();
           for(int i=0;i<cameString.length();i++){
               int c = cameString.charAt(i);
               if (c>='A'&&c<='Z') {
                   sb.append("_").append((char)(c+32));
                   continue;
               }
               sb.append((char)c);
           }
           return sb.toString();
       }
       
        /**
        * 匈牙利转峰驼式命名
        *
        * @param hungString
        * @return
        */
       public static String changeHungToCame(String hungString) {
           StringBuilder sb = new StringBuilder();
           for (int i = 0; i < hungString.length(); i++) {
               int c = hungString.charAt(i);
               if (c == '_') {
                   i++;
                   c =  hungString.charAt(i);
                   sb.append((char) (c - 32));
                   continue;
               }
               sb.append((char) c);
           }
           return sb.toString();
       }
    }
    
    public static class MysqlUtils{
        
        final static String DRIVER = "com.mysql.jdbc.Driver";
        final static String URL = "jdbc:mysql://%s:%s/%s";
        final static String LOCAL = "localhost";
        final static String DEFAULT_PORT = "3306";
        
        public static Connection getConn(String dbName) {
            if(dbName==null){
                return null;
            }
            String driver = "com.mysql.jdbc.Driver";
            String url = String.format(URL, LOCAL,DEFAULT_PORT,dbName);
            String username = "root";
            String password = "123abc123abc";
            return getConn(url,username,password);
        }
        
        public static Connection getConn(String url,String userName,String password){
            Connection conn = null;
            try {
                Class.forName(DRIVER); //classLoader,加载对应驱动
                conn = (Connection) DriverManager.getConnection(url, userName, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        } 
        
        
    }
    
    @Data
    public static class MysqlTable{
        private String packageName;
        private String tableName;
        private String className;
        private String tableComment;
        private Collection<String> importList;
        private Collection<MysqlColumn> columns;
    }
    
    @Data
    public static class MysqlColumn{
        private String type;
        private String Name;
        private String comment;
        private String columnName;
    }
    
    public static final class MysqlDefs {
        static final int COM_BINLOG_DUMP = 18;

        static final int COM_CHANGE_USER = 17;

        static final int COM_CLOSE_STATEMENT = 25;

        static final int COM_CONNECT_OUT = 20;

        static final int COM_END = 29;

        static final int COM_EXECUTE = 23;

        static final int COM_FETCH = 28;

        static final int COM_LONG_DATA = 24;

        static final int COM_PREPARE = 22;

        static final int COM_REGISTER_SLAVE = 21;

        static final int COM_RESET_STMT = 26;

        static final int COM_SET_OPTION = 27;

        static final int COM_TABLE_DUMP = 19;

        static final int CONNECT = 11;

        static final int CREATE_DB = 5; // Not used; deprecated?

        static final int DEBUG = 13;

        static final int DELAYED_INSERT = 16;

        static final int DROP_DB = 6; // Not used; deprecated?

        static final int FIELD_LIST = 4; // Not used; deprecated in MySQL 5.7.11 and MySQL 8.0.0.

        static final int FIELD_TYPE_BIT = 16;

        public static final int FIELD_TYPE_BLOB = 252;

        static final int FIELD_TYPE_DATE = 10;

        static final int FIELD_TYPE_DATETIME = 12;

        // Data Types
        static final int FIELD_TYPE_DECIMAL = 0;

        static final int FIELD_TYPE_DOUBLE = 5;

        static final int FIELD_TYPE_ENUM = 247;

        static final int FIELD_TYPE_FLOAT = 4;

        static final int FIELD_TYPE_GEOMETRY = 255;

        static final int FIELD_TYPE_INT24 = 9;

        static final int FIELD_TYPE_LONG = 3;

        static final int FIELD_TYPE_LONG_BLOB = 251;

        static final int FIELD_TYPE_LONGLONG = 8;

        static final int FIELD_TYPE_MEDIUM_BLOB = 250;

        static final int FIELD_TYPE_NEW_DECIMAL = 246;

        static final int FIELD_TYPE_NEWDATE = 14;

        static final int FIELD_TYPE_NULL = 6;

        static final int FIELD_TYPE_SET = 248;

        static final int FIELD_TYPE_SHORT = 2;

        static final int FIELD_TYPE_STRING = 254;

        static final int FIELD_TYPE_TIME = 11;

        static final int FIELD_TYPE_TIMESTAMP = 7;

        static final int FIELD_TYPE_TINY = 1;

        // Older data types
        static final int FIELD_TYPE_TINY_BLOB = 249;

        static final int FIELD_TYPE_VAR_STRING = 253;

        static final int FIELD_TYPE_VARCHAR = 15;

        // Newer data types
        static final int FIELD_TYPE_YEAR = 13;

        static final int FIELD_TYPE_JSON = 245;

        static final int INIT_DB = 2;

        static final long LENGTH_BLOB = 65535;

        static final long LENGTH_LONGBLOB = 4294967295L;

        static final long LENGTH_MEDIUMBLOB = 16777215;

        static final long LENGTH_TINYBLOB = 255;

        // Limitations
        static final int MAX_ROWS = 50000000; // From the MySQL FAQ

        /**
         * Used to indicate that the server sent no field-level character set information, so the driver should use the connection-level character encoding instead.
         */
        public static final int NO_CHARSET_INFO = -1;

        static final byte OPEN_CURSOR_FLAG = 1;

        static final int PING = 14;

        static final int PROCESS_INFO = 10; // Not used; deprecated in MySQL 5.7.11 and MySQL 8.0.0.

        static final int PROCESS_KILL = 12; // Not used; deprecated in MySQL 5.7.11 and MySQL 8.0.0.

        static final int QUERY = 3;

        static final int QUIT = 1;

        static final int RELOAD = 7; // Not used; deprecated in MySQL 5.7.11 and MySQL 8.0.0.

        static final int SHUTDOWN = 8; // Deprecated in MySQL 5.7.9 and MySQL 8.0.0.

        //
        // Constants defined from mysql
        //
        // DB Operations
        static final int SLEEP = 0;

        static final int STATISTICS = 9;

        static final int TIME = 15;
    }
}
