/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.model.enums;

import java.util.Date;

/**
 *
 * @author MRB
 */
public enum ClassType {
    
    B(1,Boolean.class),
    S(2,Short.class),
    I(3,Integer.class),
    L(4,Long.class),
    F(5,Float.class),
    D(6,Double.class),
    V(7,Void.class),
    DA(8,Date.class)
    ;
    
    private int key ;
    private Class type;
    private ClassType(int key,Class type){
        this.key = key;
        this.type = type;
        
    }
}
