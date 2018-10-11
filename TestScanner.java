/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.nio.charset.Charset;
import java.util.Scanner;

/**
 *
 * @author MRB
 */
public class TestScanner {
     public static void main(String[] args){
        //System.out.println(Charset.defaultCharset());
        Scanner scan = new Scanner(System.in,Charset.defaultCharset().name());
		System.out.println(System.getProperty("file.encoding"));
		System.out.println("你好".getBytes().length);
        System.out.println("please input");
        //System.out.println(scan.hasNext());
		
        while (scan.hasNext()) {
            String line = scan.nextLine();
            System.out.println(line);
            System.out.println(line.getBytes().length);
        }
    }
}
