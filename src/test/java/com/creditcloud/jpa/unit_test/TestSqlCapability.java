package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.entity.TbTeacher;
import com.creditcloud.jpa.unit_test.repository.TbTeacherRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MRB
 */
public class TestSqlCapability extends UnitTestApplicationTests {
    
    @Autowired
    TbTeacherRepository teacherRepository;
    
    @Test
    public void testSqlCap() throws Exception{
         System.out.println("开始保存数据");
         // 开始时间
        Long begin = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            List<TbTeacher> tbTeachers = new ArrayList();
            //以100w个实体保存为一个事务
            for(int j =0;j<100;j++){
                TbTeacher entity = new TbTeacher();
                entity.setDescription("教师");
                entity.setPicUrl("www.bbk.com");
                entity.setRegistDate(new Date());
                entity.setRemark("备注");
                entity.setSchoolName("XX大学");
                entity.setSex("男");
                entity.setTName(i*j+"");
                entity.setTPassword("123456");
                tbTeachers.add(entity);
            }
            teacherRepository.save(tbTeachers);
        }
        
        // 结束时间
        Long end =System.currentTimeMillis();
        // 耗时
        System.out.println("20万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
        
    }
}
