/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 登陆状态类
 * @author MRB
 */
@Data
public class PtuiCheckVK implements Serializable{
    private String type ;
    
    private String verifyCode ;
    
    private String pVerifySession ;
    
    private String lookCode ;
}
