/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import org.junit.Test;
import sun.misc.Resource;

/**
 *
 * @author MRB
 */
public class TestReference {
    @Test
    public void testReference() {
        Object counter = new Object();
        ReferenceQueue refQueue = new ReferenceQueue<>();
        WeakReference<Object> phantomReference = new WeakReference<>(counter, refQueue);
        PhantomReference<String> phantomReference2 = new PhantomReference(new String("Phantom"),refQueue);
        //SoftReference softReference = new SoftReference(counter,refQueue);
        counter = null;
        System.gc();
        //System.runFinalization();
        try {
            Reference<Object> ref = refQueue.remove();
            if (ref != null) {
                System.out.println("获取幻像应用" + ref);
            } else {
                System.out.println("引用队列为空1");
            }
             ref = refQueue.remove();
            if (ref != null) {
                System.out.println("获取幻像应用" + ref);
            } else {
                System.out.println("引用队列为空2");
            }
        } catch (Exception ite) {

        }
    }
    
    
    @Test
    public void testReferenceQueue(){
        ReferenceQueue<String> rq = new ReferenceQueue<String>();

        // 软引用
        SoftReference<String> sr = new SoftReference<String>(new String("Soft"),rq);
        // 弱引用
        WeakReference<String> wr = new WeakReference<String>(new String("Weak"),rq);
        // 幽灵引用
        PhantomReference<String> pr = new PhantomReference<String>(new String("Phantom"),rq);

        // 从引用队列中弹出一个对象引用
        Reference<? extends String> ref = null;
        System.gc();
        System.out.println("获取引用队列===》");
        while((ref=rq.poll())!=null){
            System.out.println(ref);
        }
    }
    
}
