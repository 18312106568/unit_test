package com.creditcloud.jpa.unit_test;


import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestSort {

    @Test
    public void testQuickSort(){
        int LEN = 100000;
        int[] arr = new int[LEN];
        int[] arr2 = new int[LEN];
        for(int i=0;i<LEN;i++){
            arr[i] = Double.valueOf(Math.random()*LEN).intValue();
            System.out.print(arr[i]);
            System.out.print(",");
        }
        System.arraycopy(arr,0,arr2,0,LEN);
        System.out.println("开始排序：");
        long start = System.currentTimeMillis();
        //quickSort(arr,0,LEN-1);
        bubblingSort(arr);
        long end = System.currentTimeMillis();
        System.out.println(String.format("冒泡排序%d个花费时间为：%d ms",LEN,(end-start)));
        start = System.currentTimeMillis();
        quickSort(arr,0,LEN-1);
        end = System.currentTimeMillis();
        System.out.println(String.format("快速排序%d个花费时间为：%d ms",LEN,(end-start)));

        /*for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }*/
    }

    /**
     * 冒泡排序
     * @param arr
     */
    public static void bubblingSort(int[] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                if(i==j){
                    continue;
                }
                if(arr[i]<arr[j]){
                    int temp = arr[j];
                    arr[j]=arr[i];
                    arr[i]=temp;
                }
            }
        }
    }

    /**
     * 快速排序
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right)
            return;
        int base = arr[right];
        int i = left;
        int j = right;
        while (i!=j){

            while (j>i&&arr[i]<=base) {
                i++;
            }
            while (j>i&&arr[j]>=base) {
                j--;
            }

            int tmp = arr[j];
            arr[j]=arr[i];
            arr[i]=tmp;
        }
        arr[right]=arr[i];
        arr[i]=base;
        quickSort(arr,left,i-1);
        quickSort(arr,i+1,right);
    }

    @Test
    public void testStable(){
        Student[] students = new Student[]{
                new Student("小明",175), new Student("小一",176)
                ,new Student("小智",175),new Student("小红",173)
                ,new Student("小天",176),new Student("小胜",172)};
        quickSort(students,0,students.length-1);
        for(Student stu : students){
            System.out.println(stu);
        }
    }


    /**
     * 快速排序
     * @param arr
     * @param left
     * @param right
     */
    public static <T extends Comparable> void quickSort(T[] arr, int left, int right) {
        if (left >= right)
            return;
        T base = arr[right];
        int i = left;
        int j = right;
        while (i!=j){

            while (j>i&&arr[i].compareTo(base)<=0) {
                i++;
            }
            while (j>i&&arr[j].compareTo(base)>=0) {
                j--;
            }

            T tmp = arr[j];
            arr[j]=arr[i];
            arr[i]=tmp;
        }
        arr[right]=arr[i];
        arr[i]=base;
        quickSort(arr,left,i-1);
        quickSort(arr,i+1,right);
    }

    @AllArgsConstructor
    @ToString
    static class Student implements Comparable{
        String name;
        int height;

        @Override
        public int compareTo(Object o) {
            Student stu = (Student) o;
            return this.height==stu.height?0:(this.height>stu.height?1:-1);
        }
    }

    @Test
    public void testListQuickSort(){
        int LEN = 1000000;
        List<Integer> numList = new ArrayList<Integer>();
        for(int i=0;i<LEN;i++){
            numList.add( Double.valueOf(Math.random()*100).intValue());
            System.out.print(numList.get(i));
            System.out.print(",");
        }
        System.out.println("开始排序：");
        long start = System.currentTimeMillis();
        numList = quickSort(numList);

        long end = System.currentTimeMillis();
        System.out.println(String.format("%d个花费时间为：%d ms",LEN,(end-start)));
        /*for(Integer num : numList){
            System.out.println(num);
        }*/
    }



    public static List<Integer> quickSort(List<Integer> numList){
        if(numList.size()<=1)
            return numList;
        Integer base = numList.get(numList.size()/2);
        List<Integer> leftList = new ArrayList<Integer>();
        List<Integer> equalList = new ArrayList<Integer>();
        List<Integer> rightList = new ArrayList<Integer>();
        for(Integer num : numList){
            if(base.equals(num)){
                equalList .add(num);
                continue;
            }
            if(num<base){
                leftList.add(num);
                continue;
            }
            rightList.add(num);
        }
        List<Integer> result = new ArrayList();
        result.addAll(quickSort(leftList));
        result.addAll(equalList);
        result.addAll(quickSort(rightList));
        return result;
    }
}