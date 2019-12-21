/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;


import com.google.gson.Gson;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.Test;

import java.sql.*;
import java.util.List;


/**
 *
 * @author MRB
 */
public class TestMysql {

    @Test
    public void testJdbc() throws SQLException{
        Gson gson = new Gson();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://185.144.28.216:3306/test", "MRB", "123abc123abc");
            String sql = "select * from tb_score where id=?";//定义一个要执行的SQL语句
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "1' and '1'='1");
            ResultSet row = ps.executeQuery();
            if(row.next()){
                System.out.println(row.getObject(0));
            }else{
                System.out.println("结果为空");
            }
        } catch (SQLException ex) {
            System.out.println("获取连接异常"+ex.toString());
        }finally{
            if(con!=null){
                con.close();
            }
        }
    }

    @Test
    public void testQueryRunner() throws SQLException{
        org.apache.commons.dbutils.QueryRunner qr = new org.apache.commons.dbutils.QueryRunner();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://185.144.28.216:3306/test", "MRB", "123abc123abc");
            String sql = "select * from tb_score";//定义一个要执行的SQL语句
            //调用query方法，结果集处理的参数上，传递实现类ArrayListHandler
            //方法返回值 每行是一个数组
            List<Object[]> result = qr.query(con, sql, new ArrayListHandler());
            //集合的遍历
            for (Object[] objs : result) {
                for (Object obj : objs) {
                        System.out.print(obj+"\t");
                }
                System.out.println();
            }

        } catch (SQLException ex) {
            System.out.println("获取连接异常"+ex.toString());
        }finally{
            if(con!=null){
                con.close();
            }
        }
    }


    @Test
    public void testSeachIn() throws SQLException{
        Gson gson = new Gson();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.11.152)(PORT = 1521)) (CONNECT_DATA = (SID = dbtest) ) )", "gzmpcysjf", "gzmpcysjf");
            String sql = "select ID from JF_FEE_BASE_MAPPING t where (t.RECEIPT_ID,t.LOADING_LIST_ID) in (?)";//定义一个要执行的SQL语句
            PreparedStatement ps = con.prepareStatement(sql);

            Array array = con.createArrayOf("NUMBER",new Object[]{7319525,1535227});
            Array array1 =  con.createArrayOf("ARRAY",new Object[]{array});
            ps.setArray(1, array1);
            ResultSet row = ps.executeQuery();
            if(row.next()){
                System.out.println(row.getObject(0));
            }else{
                System.out.println("结果为空");
            }
        } catch (SQLException ex) {
            System.out.println("获取连接异常"+ex.toString());
        }finally{
            if(con!=null){
                con.close();
            }
        }
    }
}
