package com.creditcloud.jpa.unit_test;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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


    /**
     * 归并排序
     * @return
     */
    public static int[] mergeSort(int[] arr,int left,int right){
        if(left==right){
            return new int[]{arr[left]};
        }

        //二分
        int mid = (left+right)/2;
        int[] lArr = mergeSort(arr,left,mid);
        int[] rArr = mergeSort(arr,mid+1,right);


        //合并
        int[] nArr =new int[right-left+1];
        int lIndex=0;
        int rIndex=0;
        int nIndex=0;
        while(lIndex<lArr.length && rIndex<rArr.length){
            if(lArr[lIndex]<=rArr[rIndex]){
                nArr[nIndex++] = lArr[lIndex++];
            }else {
                nArr[nIndex] = rArr[rIndex];
            }
        }
        while(lIndex<lArr.length){
            nArr[nIndex++] = lArr[lIndex++];
        }
        while(rIndex<rArr.length){
            nArr[nIndex++] = rArr[rIndex++];
        }

        return nArr;

    }

    @Test
    public void testMergeSort(){
        int arr[] = new int[]{5,3,3,8,9,7,4};
        int arr2[] = mergeSort(arr,0,arr.length-1);
        for(int num : arr2){
            System.out.println(num);
        }
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

    @Test
    public void testClone(){
        Student stu= new Student("小白",170);
        List<Student> stuList = Arrays.asList(stu);
        List<Student> stuList2 =  cloneData(stuList,new TypeToken<List<Student>>(){}.getType());
        stuList2.get(0).name="小绿";
        stuList2.get(0).height=120;
        System.out.println(stuList);
        System.out.println(stuList2);

    }


    public <T>T cloneData(T data, Type type){
        Gson gson = new Gson();
        String dataStr = gson.toJson(data);
        return gson.fromJson(dataStr,type);
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
