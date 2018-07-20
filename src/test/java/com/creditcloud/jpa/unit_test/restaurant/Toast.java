/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.restaurant;

/**
 *
 * @author MRB
 */
public class Toast{
    //面包的状态
    public enum Status{
        DRY, 
        BUTTERED,
        JAMMED
    }
    
    //面包刚烤出来是干的
    private Status status = Status.DRY;
    
    //面包的编号
    private final int id;
    
    public Toast(int idn) {
        id = idn;
    }
    public void butter(){
        status = Status.BUTTERED;
    }
    public void jam(){
        status = Status.JAMMED;
    }
    public Status getStatus(){
        return status;
    }
    public int getId(){
        return id;
    }
    public String toString(){
        return "Toast " + id + "- " + status;
    }
}