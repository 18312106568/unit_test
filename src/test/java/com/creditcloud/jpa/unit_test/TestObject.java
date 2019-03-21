package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.entity.Product;
import com.creditcloud.jpa.unit_test.entity.QQLogin;
import com.creditcloud.jpa.unit_test.entity.TbTeacher;
import com.creditcloud.jpa.unit_test.entity.base.EntityId;
import org.junit.Test;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestObject {

    public static void printTypeSize(Object obj){
        System.out.println(String.format("%s类型占用%dB",obj.getClass(),sizeOf(obj)));
    }



    @Test
    public void testExtendList() throws IllegalAccessException, InterruptedException {
//        List<String> strList = new ArrayList<>();
//        for(int i=0;i<10;i++) {
//            System.out.println(String.format("%d个字符串,占用大小%d", strList.size(), fullSizeOf(strList)));
//            strList.add(Long.toString(System.currentTimeMillis()));
//
//        }
        /**
         * 下段代码结合jconsole,得出结论是此算法能算出对象大小
         */
        List<String> strList = new ArrayList<>();
        for(int i=0;i<10;i++){
            for(int j=0;j<1000000;j++){
                strList.add(Long.toString(System.currentTimeMillis()));
            }
            System.gc();
            System.out.println(String.format("strList大小:%dMB",fullSizeOf(strList)/(1000*1000)));
            Thread.sleep(60*1000L);
        }
    }

    @Test
    public void testInstrumenttation() throws IllegalAccessException, IOException, ClassNotFoundException {
        Class clazz = sun.instrument.InstrumentationImpl.class;

        boolean bool = true;
        byte buf = 123;
        char c = '中';
        short st = 123;
        int i = 123;
        long l = 123l;
        float f = 123.0f;
        double d = 123.000123d;



        //System.out.println(fullSizeOf(arrInt));
        String str = "0123456789";
        List<String> strList = new ArrayList<>();
        for(int j=0;j<10;j++){
            strList.add(Integer.toString(j));
        }

        //基本类型占用多少B
//        System.out.println(String.format("%s类型占用%dB",boolean.class,Boolean.));
//        System.out.println(String.format("%s类型占用%dB",char.class, Character.SIZE/8));
//        System.out.println(String.format("%s类型占用%dB",byte.class,sizeOf(buf)));
//        System.out.println(String.format("%s类型占用%dB",short.class,sizeOf(st)));
//        System.out.println(String.format("%s类型占用%dB",int.class,sizeOf(i)));
//        System.out.println(String.format("%s类型占用%dB",long.class,sizeOf(l)));
//        System.out.println(String.format("%s类型占用%dB",float.class,sizeOf(f)));
//        System.out.println(String.format("%s类型占用%dB",double.class,sizeOf(d)));


        //基本包装类型占用多少B
        printTypeSize(bool);
        printTypeSize(c);
        printTypeSize(buf);
        printTypeSize(st);
        printTypeSize(i);
        printTypeSize(l);
        printTypeSize(f);
        printTypeSize(d);

//        System.out.println(arrInt.getClass().getName().length());
//        System.out.println(sizeOf(strList));
        System.out.println(Integer.class.isPrimitive());
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("E:\\doc\\1.txt")));
//        oos.writeObject(str);
//        oos.flush();
//        oos.close();
//        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(new File("E:\\doc\\1.txt")));
//        System.out.println(oin.readObject().equals(arrInt));
        System.out.println(fullSizeOf(Integer.valueOf(12886)));
//        for(Field field : TbTeacher.class.getDeclaredFields()) {
//            System.out.println(field.getName()+":"+TestSyncAndLock.getUnsafe().objectFieldOffset(field));
//        }
        //System.out.println(instrumentation.getObjectSize(new Integer(123)));
    }

    public static long sizeOf(Object obj) {
        return UnitTestApplication.instrumentation.getObjectSize(obj);
    }

    /**
     * 递归计算当前对象占用空间总大小，包括当前类和超类的实例字段大小以及实例字段引用对象大小
     *
     * @param objP
     * @return
     * @throws IllegalAccessException
     */
    public static long fullSizeOf(Object objP) throws IllegalAccessException {
        Set<Object> visited = new HashSet<Object>();
        Deque<Object> toBeQueue = new ArrayDeque<Object>();
        toBeQueue.add(objP);
        long size = 0L;
        while (toBeQueue.size() > 0) {
            Object obj = toBeQueue.poll();
            //sizeOf的时候已经计基本类型和引用的长度，包括数组
            size += skipObject(visited, obj) ? 0L : sizeOf(obj);
            Class<?> tmpObjClass = obj.getClass();
            if (tmpObjClass.isArray()) {
                //[I , [F 基本类型名字长度是2
                if (tmpObjClass.getName().length() > 2) {
                    for (int i = 0, len = Array.getLength(obj); i < len; i++) {
                        Object tmp = Array.get(obj, i);
                        if (tmp != null) {
                            //非基本类型需要深度遍历其对象
                            toBeQueue.add(Array.get(obj, i));
                        }
                    }
                }
            } else {
                while (tmpObjClass != null) {
                    Field[] fields = tmpObjClass.getDeclaredFields();
                    for (Field field : fields) {
                        if (Modifier.isStatic(field.getModifiers())   //静态不计
                                || field.getType().isPrimitive()) {    //基本类型不重复计
                            continue;
                        }

                        field.setAccessible(true);
                        Object fieldValue = field.get(obj);
                        if (fieldValue == null) {
                            continue;
                        }
                        toBeQueue.add(fieldValue);
                    }
                    tmpObjClass = tmpObjClass.getSuperclass();
                }
            }
        }
        return size;
    }

    /**
     * String.intern的对象不计；计算过的不计，也避免死循环
     *
     * @param visited
     * @param obj
     * @return
     */
    static boolean skipObject(Set<Object> visited, Object obj) {
        if (obj instanceof String && obj == ((String) obj).intern()) {
            return true;
        }
        return visited.contains(obj);
    }
}
