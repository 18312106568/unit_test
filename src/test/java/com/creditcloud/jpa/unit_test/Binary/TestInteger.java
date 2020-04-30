/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.Binary;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author MRB
 */
public class TestInteger {

    @Test
    public void testInteger(){
        System.out.println((((3*0.998)*1.0774*0.998)-(2*1.002))*13260-13260);
    }

    @Test
    public void testToBinary(){
        System.out.println(Integer.toHexString(21>>2));
        System.out.println(Integer.toHexString(-21>>2));
        System.out.println(Integer.toHexString(-21>>>2));
        System.out.println(0xd);
    }

    @Test
    public void testCharToInt(){
        char c33 = 3*16+3;
        char c3f = 3*16+11;
        System.out.println(c3f);
    }

    @Test
    public void testFibonacc(){
        int ALL_SIZE = 2019;
        long[] a = new long[ALL_SIZE];
        a[0]=1;
        a[1]=1;
        for(int i=2;i<ALL_SIZE;i++){
            a[i]=a[i-1]+a[i-2];
        }
        for(int i=0;i<ALL_SIZE;i++){
            System.out.println(i+"-"+a[i]);
        }
    }

    @Test
    public void testArraysFill(){
        Integer[] newArr = new Integer[2];
         Arrays.fill(newArr, 50);
        System.out.println(newArr);
        for(Integer integer : newArr){
            System.out.println(integer);
        }
    }

    @Test
    public void testIsPrimitive(){
        System.out.println(int.class.isPrimitive());
    }

    @Test
    public void testNextInt(){
        Random random = new Random();
        for(int i=0;i<100;i++){
            System.out.println(random.nextInt(100));
        }
    }

    @Test
    public void testAtomic(){
        AtomicLong txnSeq = new AtomicLong(0);
        System.out.println(txnSeq);
        txnSeq.incrementAndGet();
        System.out.println(txnSeq);

        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
    }

    @Test
    public void testNullPointer(){
        Integer a = 1;
        Integer b = 2;
        Integer c = null;
        Boolean flag = false;
        Integer result = (flag ?  a * b  :  c);
    }


    public static byte[][] bitmaskArr = {
             {0,0,0,0,0,0,0,1}
            ,{0,0,0,1,1,1,1,0}
            ,{0,1,1,0,0,1,1,0}
            ,{1,0,1,0,1,0,1,0}
    };

    @Test
    public void testBitMask(){
        Integer poison =8;
        Integer gressNum =0;
        Integer MOVENUM = 3;
        for(byte[] arr : bitmaskArr){
            byte bit = arr[poison-1];
            System.out.print(bit);
            gressNum += bit<<MOVENUM;
            MOVENUM--;
        }

        System.out.println();
        System.out.println(gressNum);
    }

    @Test
    public void testRerve(){
        Integer revrOne = ~1;
        System.out.println(Integer.toBinaryString(1));
        System.out.println(revrOne);
        System.out.println(revrOne*1024);
        System.out.println(Integer.toBinaryString(~1));
        System.out.println(Integer.toBinaryString(~1+1));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.MAX_VALUE+1);
        System.out.println(~Integer.MAX_VALUE);
        System.out.println(-Integer.MAX_VALUE-2);
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE+1));
        System.out.println(Integer.toBinaryString(-Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(~~1));
    }

    public static enum Permission{
        ALLOW_SELECT(1<<0),
        ALLOW_CREATE(1<<1),
        ALLOW_UPDATE(1<<2),
        ALLOW_DELETE(1<<3)
        ;
        int code;
        Permission(int code){
            this.code =code;
        }

        public int getCode(){
            return this.code;
        }
    }


    public static class PermissionOp{
        public static int addPermission(int permissionCode,Permission permisson){
            return permissionCode |= permisson.getCode();
        }

        public static int deletePermission(int permissionCode,Permission permission){
            return permissionCode &= ~permission.getCode();
        }

        public static boolean isAllow(int permissionCode,Permission permission){
            return (permissionCode & permission.getCode()) == permission.getCode();
        }
    }

    //高效权限
    @Test
    public void testPermission(){
        int myCode = 0xE ;
        System.out.println("how many permissions I have ?");
        for(Permission permission : Permission.values()){
            System.out.println(String.format("I have permission[%s] :%b"
                    ,permission.name(),PermissionOp.isAllow(myCode,permission)));
        }
        myCode = PermissionOp.deletePermission(myCode,Permission.ALLOW_CREATE);
        System.out.println(String.format(
                "i dont need CREATE permission,now my permission is %d",myCode));

        for(Permission permission : Permission.values()){
            System.out.println(String.format("I have permission[%s] :%b"
                    ,permission.name(),PermissionOp.isAllow(myCode,permission)));
        }

        myCode = PermissionOp.addPermission(myCode,Permission.ALLOW_SELECT);
        System.out.println(String.format(
                "i  need SELECT permission,now my permission is %d",myCode));
        for(Permission permission : Permission.values()){
            System.out.println(String.format("I have permission[%s] :%b"
                    ,permission.name(),PermissionOp.isAllow(myCode,permission)));
        }
    }


    @Test
    public void testPow(){
        System.out.println(1 ^2);
    }
}
