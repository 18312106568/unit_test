/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.util.Scanner;

/**
 *
 * @author MRB
 */
public class TestDebug {
    public static void main(String args[]){
        while(true){
            System.out.println("开始输入:");
            Scanner scan = new Scanner(System.in);
            String str1 = scan.nextLine();
            System.out.println(str1);
        }
    }
}
