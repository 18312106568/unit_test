/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;


import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.Test;


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
}
