/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import org.junit.Test;

/**
 *
 * @author MRB
 */
public class TestSql {
    @Test
    public void testSql(){
     StringBuilder sql = new StringBuilder("SELECT * FROM \n" );
            sql.append("(SELECT DISTINCT(tp.ID) AS id,")
                .append( "tp.`NAME` AS name,")
                .append( "tp.PRODUCTTYPE AS productTypeKey,")
                .append( "tp.FEE_RATE AS feeRate,")
                .append( "tp.OVERDUE_RATE AS overdueRate,")
                .append("tp.LOAN_DURATION_DAYS AS durationDays, ")
                .append("tb.ORG_CODE AS orgCode, ")
                .append("tp.`ENABLE`   ")
                .append( "FROM TB_LOANREQUEST_PRODUCT tp ")
                .append( "left JOIN TB_CORPORATION_BASE tb  ON ( " )
                .append( "(tp.MARKTYPE is NULL or locate(tb.MARK_TYPE,tp.MARKTYPE))")
                .append("AND (tp.PERMISSIONCORNAMES is NULL or locate(tb.`NAME`,tp.PERMISSIONCORNAMES)) ")
                .append( "AND (tp.UNPERMISSIONCORNAMES is NULL or locate(tb.`NAME`,tp.UNPERMISSIONCORNAMES)=0)) " )
                .append(")t ");
            System.out.println(sql.toString());
            int[] arr = new int[10];
            System.out.println(arr.getClass().getName());
    }
}
